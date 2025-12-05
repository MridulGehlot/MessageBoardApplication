package com.mg.mboard.pojo.request;
public class LogoutRequest implements java.io.Serializable
{
private String username;
public LogoutRequest()
{}
public void setUsername(String username)
{
this.username=username;
}
public String getUsername()
{
return this.username;
}
}