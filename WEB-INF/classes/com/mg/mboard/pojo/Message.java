package com.mg.mboard.pojo;
public class Message implements java.io.Serializable
{
private String fromUser;
private String message;
public Message()
{}
public void setFromUser(String fromUser)
{
this.fromUser=fromUser;
}
public String getFromUser()
{
return this.fromUser;
}
public void setMessage(String message)
{
this.message=message;
}
public String getMessage()
{
return this.message;
}
}