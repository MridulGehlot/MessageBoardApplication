package com.mg.mboard.pojo.request;
public class PostMessageRequest implements java.io.Serializable
{
private String fromUser;
private String message;
public PostMessageRequest()
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