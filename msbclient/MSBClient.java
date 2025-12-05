import com.mg.mboard.pojo.*;
import com.mg.mboard.pojo.request.*;
import com.mg.mboard.pojo.response.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.websocket.*;
import com.google.gson.*;
@ClientEndpoint
public class MSBClient
{
private Session session;
private WebSocketContainer webSocketContainer;
@OnMessage
public void onMessage(String message)
{
System.out.println("\n Message Arrived : "+message+"\n");
}
private void connect()
{
try
{
webSocketContainer=ContainerProvider.getWebSocketContainer();
session=webSocketContainer.connectToServer(this,URI.create("ws://localhost:8080/wsmboard/messageBoardServer"));
}catch(Exception e)
{
System.out.println(e);
}
}
private void createTUI()
{
try
{
InputStreamReader isr=new InputStreamReader(System.in);
BufferedReader br=new BufferedReader(isr);
String input,command,username,password;
String prompt="::msgBoard>";
String splits[];
Gson gson=new Gson();
Card c;
while(true)
{
System.out.print(prompt);
input=br.readLine();
if(input.equals("quit")) break;
splits=input.split(" ");
command=splits[0];
if(command.equals("LOGIN"))
{
username=splits[1];
password=splits[2];
LoginRequest lr=new LoginRequest();
lr.setUsername(username);
lr.setPassword(password);
c=new Card();
c.setType(CardType.REQUEST);
c.setAction(Action.LOGIN);
c.setId(UUID.randomUUID().toString());
c.setObject(lr);
session.getBasicRemote().sendText(gson.toJson(c));
}
else if(command.equals("LOGOUT"))
{
username=splits[1];
LogoutRequest lr=new LogoutRequest();
lr.setUsername(username);
c=new Card();
c.setType(CardType.REQUEST);
c.setAction(Action.LOGOUT);
c.setId(UUID.randomUUID().toString());
c.setObject(lr);
session.getBasicRemote().sendText(gson.toJson(c));
}
}
}catch(Throwable t)
{
System.out.println(t);
System.exit(0);
}
}
public static void main(String gg[])
{
MSBClient c=new MSBClient();
c.connect();
c.createTUI();
}
}