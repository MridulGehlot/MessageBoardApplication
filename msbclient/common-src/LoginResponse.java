package com.mg.mboard.pojo.response;
public class LoginResponse implements java.io.Serializable
{
private boolean success;
private String [] onlineUsers;
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
}