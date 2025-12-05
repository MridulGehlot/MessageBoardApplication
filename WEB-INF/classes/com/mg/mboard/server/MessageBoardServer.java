package com.mg.mboard.server;
import com.mg.mboard.pojo.*;
import com.mg.mboard.pojo.request.*;
import com.mg.mboard.pojo.response.*;
import com.mg.mboard.model.*;
import com.google.gson.*;
import com.google.gson.internal.*;
import java.io.*;
import java.util.*;
import javax.websocket.*;
import javax.websocket.server.*;
@ServerEndpoint("/messageBoardServer")
public class MessageBoardServer
{

private void logout(Session session,String id,LogoutRequest logoutRequest)
{
DataModel.removeUser(logoutRequest.getUsername());
Gson gson=new Gson();
String u;
int x=0;
String [] onlineUsers=DataModel.getLoggedInUsers();
while(x<onlineUsers.length)
{
u=onlineUsers[x];
Session s=DataModel.getUserSession(u);
if(s!=null)
{
RemoveUserRequest removeUserRequest=new RemoveUserRequest();
removeUserRequest.setUsername(logoutRequest.getUsername());
Card c=new Card();
c.setType(CardType.REQUEST);
c.setId(UUID.randomUUID().toString());
c.setAction(Action.REMOVE_USER);
c.setObject(removeUserRequest);
try
{
synchronized(s)
{
s.getBasicRemote().sendText(gson.toJson(c));
}
}catch(Throwable e)
{
System.out.println(e);
}
}
x++;
}
}

private void login(Session session,String id,LoginRequest loginRequest)
{
Gson gson=new Gson();
User user=DataModel.getUser(loginRequest.getUsername());
LoginResponse loginResponse=new LoginResponse();
if(user==null || user.getUsername().equals(loginRequest.getUsername())==false)
{
System.out.println("Login Request Rejected");
loginResponse.setSuccess(false);
loginResponse.setOnlineUsers(null);
}
else
{
System.out.println("Login Request Accepted");
Map<String,Object> m=session.getUserProperties();
m.put("username",loginRequest.getUsername());
loginResponse.setSuccess(true);
DataModel.addUser(loginRequest.getUsername(),session);
loginResponse.setOnlineUsers(DataModel.getLoggedInUsers());
}
Card card=new Card();
card.setType(CardType.RESPONSE);
card.setId(id);
card.setObject(loginResponse);
try
{
session.getBasicRemote().sendText(gson.toJson(card));
}catch(Throwable t)
{
System.out.println(t);
}
if(loginResponse.getSuccess())
{
String u;
int x=0;
String [] onlineUsers=loginResponse.getOnlineUsers();
while(x<onlineUsers.length)
{
u=onlineUsers[x];
if(u.equals(loginRequest.getUsername())==false)
{
Session s=DataModel.getUserSession(u);
if(s!=null)
{
AddUserRequest addUserRequest=new AddUserRequest();
addUserRequest.setUsername(loginRequest.getUsername());
Card c=new Card();
c.setType(CardType.REQUEST);
c.setId(UUID.randomUUID().toString());
c.setAction(Action.ADD_USER);
c.setObject(addUserRequest);
try
{
synchronized(s)
{
s.getBasicRemote().sendText(gson.toJson(c));
}
}catch(Throwable e)
{
System.out.println(e);
}
}
}
x++;
}
} //if success
} //login ends here

private void postMessage(Session session,String id,PostMessageRequest postMessageRequest)
{}

private void processRequest(Session session,Card card)
{
LinkedTreeMap map=(LinkedTreeMap)card.getObject();
if(card.action.equals(Action.LOGIN))
{
LoginRequest loginRequest=new LoginRequest();
loginRequest.setUsername((String)map.get("username"));
loginRequest.setPassword((String)map.get("password"));
login(session,card.getId(),loginRequest);
}
else if(card.action.equals(Action.LOGOUT))
{
LogoutRequest logoutRequest=new LogoutRequest();
logoutRequest.setUsername((String)map.get("username"));
logout(session,card.getId(),logoutRequest);
}
else if(card.action.equals(Action.POST_MESSAGE))
{
PostMessageRequest postMessageRequest=new PostMessageRequest();
postMessageRequest.setFromUser((String)map.get("fromUser"));
postMessageRequest.setMessage((String)map.get("message"));
postMessage(session,card.getId(),postMessageRequest);
}
}

private void processResponse(Session session,Card card)
{
//do nothing
}

@OnMessage
public void onMessage(Session session,String message)
{
System.out.println("Message Arrived : "+message);
try
{
Gson gson=new Gson();
Card card=(Card)gson.fromJson(message,Card.class);
if(card.getType().equals(CardType.REQUEST))
{
processRequest(session,card);
}
if(card.getType().equals(CardType.RESPONSE))
{
processResponse(session,card);
}
}catch(Throwable t)
{
System.out.print(t); //add it to log file later on
}
}

@OnError
public void onError(Session session,Throwable t)
{
System.out.println("Some Issue : "+t.toString());
}
@OnOpen
public void onOpen()
{
System.out.println("Connection opened, but doing nothing");
}
@OnClose
public void onClose()
{
System.out.println("Connection closed will handle later on");
}

}