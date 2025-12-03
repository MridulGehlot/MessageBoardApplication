package com.mg.mboard.pojo;
public class Card implements java.io.Serializable
{
public String type; //REQUEST / RESPONSE
public String action; //LOGIN
public String id;
public Object object;
public Card()
{}
public void setType(String type)
{
this.type=type;
}
public String getType()
{
return this.type;
}
public void setAction(String action)
{
this.action=action;
}
public String getAction()
{
return this.action;
}
public void setId(String id)
{
this.id=id;
}
public String getId()
{
return this.id;
}
public void setObject(Object object)
{
this.object=object;
}
public Object getObject()
{
return this.object;
}
}