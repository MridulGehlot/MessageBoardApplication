package com.mg.mboard.pojo.request;
public class RemoveUserRequest implements java.io.Serializable
{
private String username;
public RemoveUserRequest()
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