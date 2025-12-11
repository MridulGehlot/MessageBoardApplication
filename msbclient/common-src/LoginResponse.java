package com.mg.mboard.pojo.response;
import com.mg.mboard.pojo.*;
public class LoginResponse implements java.io.Serializable
{
private boolean success;
private String [] onlineUsers;
private Message [] lastMessages;
public LoginResponse()
{}
public void setSuccess(boolean success)
{
this.success=success;
}
public boolean getSuccess()
{
return this.success;
}
public void setOnlineUsers(String [] onlineUsers)
{
this.onlineUsers=onlineUsers;
}
public String [] getOnlineUsers()
{
return this.onlineUsers;
}
public void setLastMessages(Message [] lastMessages)
{
this.lastMessages=lastMessages;
}
public Message [] getLastMessages()
{
return this.lastMessages;
}
}