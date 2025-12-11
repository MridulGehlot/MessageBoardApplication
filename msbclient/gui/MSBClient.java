import com.mg.mboard.pojo.*;
import com.mg.mboard.pojo.request.*;
import com.mg.mboard.pojo.response.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.websocket.*;
import com.google.gson.*;
import com.google.gson.internal.*;
@ClientEndpoint
public class MSBClient implements LoginDialogEventHandler,MessageBoardFrameEventHandler
{
private String username;
private LoginDialog loginDialog;
private MessageBoardFrame messageBoardFrame;
private Session session;
private WebSocketContainer webSocketContainer;
@OnMessage
public void onMessage(String message)
{
System.out.println("\n Message Arrived : "+message+"\n");
Gson gson=new Gson();
Card card=null;
try
{
card=gson.fromJson(message,Card.class);
}catch(Exception e)
{
System.out.println(e);
}
if(card.getType().equals(CardType.REQUEST))
{
processRequest(card);
}
if(card.getType().equals(CardType.RESPONSE))
{
processResponse(card);
}
}
private void processRequest(Card card)
{
LinkedTreeMap map=(LinkedTreeMap)card.getObject();
if(card.getAction().equals(Action.ADD_USER))
{
AddUserRequest addUserRequest=new AddUserRequest();
addUserRequest.setUsername((String)map.get("username"));
handleAddUserRequest(addUserRequest);
}
else if(card.getAction().equals(Action.REMOVE_USER))
{
RemoveUserRequest removeUserRequest=new RemoveUserRequest();
removeUserRequest.setUsername((String)map.get("username"));
handleRemoveUserRequest(removeUserRequest);
}
else if(card.getAction().equals(Action.ADD_MESSAGE))
{
AddMessageRequest addMessageRequest=new AddMessageRequest();
addMessageRequest.setFromUser((String)map.get("fromUser"));
addMessageRequest.setMessage((String)map.get("message"));
handleAddMessageRequest(addMessageRequest);
}
}
private void processResponse(Card card)
{
LinkedTreeMap map=(LinkedTreeMap)card.getObject();
if(card.getAction().equals(Action.LOGIN))
{
LoginResponse loginResponse=new LoginResponse();
loginResponse.setSuccess((boolean)map.get("success"));
ArrayList<String> list=(ArrayList<String>) map.get("onlineUsers");
ArrayList<LinkedTreeMap> messages=(ArrayList<LinkedTreeMap>) map.get("lastMessages");
if(list!=null)
{
String [] onlineUsers=new String[list.size()];
list.toArray(onlineUsers);
loginResponse.setOnlineUsers(onlineUsers); 
}
System.out.println("Hurray");
if(messages!=null)
{
System.out.println("Cartoon");
System.out.println(messages.size()>0);
System.out.println("size - "+messages.size());

Message [] lastMessages=new Message[messages.size()];
Message msg;
for(int i=0;i<messages.size();i++)
{
LinkedTreeMap m=messages.get(i);
msg=new Message();
msg.setFromUser((String)m.get("fromUser"));
msg.setMessage((String)m.get("message"));
lastMessages[i]=msg;
}
loginResponse.setLastMessages(lastMessages);
}
handleLoginResponse(loginResponse,card.getId());
}
else if(card.getAction().equals(Action.LOGOUT))
{
}
else if(card.getAction().equals(Action.POST_MESSAGE))
{}
}
private void handleLoginResponse(LoginResponse loginResponse,String id)
{
if(loginResponse.getSuccess())
{
this.loginDialog.dispose();
if(this.messageBoardFrame==null)
{
this.messageBoardFrame=new MessageBoardFrame(this.username,this);
}
this.messageBoardFrame.setOnlineUsers(loginResponse.getOnlineUsers());
this.messageBoardFrame.setVisible(true);
Message [] lastMessages=loginResponse.getLastMessages();
for(int i=0;i<lastMessages.length;i++)
{
Message m=lastMessages[i];
this.messageBoardFrame.addToMessageBoard(m.getFromUser(),m.getMessage());
}
}
else
{
this.loginDialog.displayError("Invalid Credentials");
this.loginDialog.clear();
this.loginDialog.enableAll();
}
}
private void handleAddUserRequest(AddUserRequest addUserRequest)
{
this.messageBoardFrame.addOnlineUser(addUserRequest.getUsername());
}
private void handleRemoveUserRequest(RemoveUserRequest removeUserRequest)
{
this.messageBoardFrame.removeOnlineUser(removeUserRequest.getUsername());
}
private void handleAddMessageRequest(AddMessageRequest addMessageRequest)
{
this.messageBoardFrame.addToMessageBoard(addMessageRequest.getFromUser(),addMessageRequest.getMessage());
}
private void connect()
{
try
{
webSocketContainer=ContainerProvider.getWebSocketContainer();
session=webSocketContainer.connectToServer(this,URI.create("ws://localhost:8080/wsmboard/messageBoardServer"));
}catch(Exception e)
{
javax.swing.JOptionPane.showMessageDialog(null,"Unable to Connect to server try after some time");
System.out.println(e);
System.exit(0);
}
}

public void postMessage(String message)
{
PostMessageRequest postMessageRequest=new PostMessageRequest();
postMessageRequest.setFromUser(this.username);
postMessageRequest.setMessage(message);
Card card=new Card();
card.setType(CardType.REQUEST);
card.setAction(Action.POST_MESSAGE);
card.setId(UUID.randomUUID().toString());
card.setObject(postMessageRequest);
Gson gson=new Gson();
try
{
session.getBasicRemote().sendText(gson.toJson(card));
}catch(Throwable t)
{
this.messageBoardFrame.addErrorToMessageBoard("\n --ERROR-- => Unable to send this message try again later \n");
System.out.println(t);
}
}

public void onLoginButtonClicked(String username,String password)
{
System.out.println("Login Data Arrived");
System.out.println(username+","+password);
this.username=username;
//send login request to server
login(username,password);
}
private void login(String username,String password)
{
LoginRequest loginRequest=new LoginRequest();
loginRequest.setUsername(username);
loginRequest.setPassword(password);
Card card=new Card();
card.setType(CardType.REQUEST);
card.setAction(Action.LOGIN);
card.setId(UUID.randomUUID().toString());
card.setObject(loginRequest);
Gson gson=new Gson();
try
{
this.session.getBasicRemote().sendText(gson.toJson(card));
}catch(Throwable t)
{
System.out.println("Login Failed");
System.out.println(t);
}
}
public void onLoginDialogClosed()
{
if(session!=null)
{
try
{
session.close();
}catch(Throwable t)
{
//do nothing
}
}
System.exit(0);
}
private void start()
{
if(this.loginDialog==null)
{
this.loginDialog=new LoginDialog();
this.loginDialog.setLoginDialogEventHandler(this);
}
this.loginDialog.setVisible(true);
}

public void logoutAndCloseApplication()
{
Gson gson=new Gson();
Card card=new Card();
LogoutRequest logoutRequest=new LogoutRequest();
logoutRequest.setUsername(this.username);
card.setAction(Action.LOGOUT);
card.setId(UUID.randomUUID().toString());
card.setObject(logoutRequest);
card.setType(CardType.REQUEST);
try
{
session.getBasicRemote().sendText(gson.toJson(card));
session.close();
System.exit(0);
}catch(Exception e)
{
System.out.println(e);
}
}

public static void main(String gg[])
{
MSBClient c=new MSBClient();
c.connect();
c.start();
//c.createTUI();
}

}