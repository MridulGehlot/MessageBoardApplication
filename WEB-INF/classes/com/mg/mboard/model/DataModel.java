package com.mg.mboard.model;
import com.mg.mboard.pojo.*;
import java.util.*;
import javax.websocket.*;
public class DataModel
{
private static Map<String,User> registeredUsers=new HashMap<>();
private static Map<String,Session> loggedInUsers=new HashMap<>();
private static Map<String,Card> unProcessedRequests=new HashMap<>();
static
{
initDataModel();
}
public static void addUnProcessedRequest(Card card)
{
synchronized(unProcessedRequests)
{
unProcessedRequests.put(card.getId(),card);
}
}
public static void removeUnProcessedRequest(String id)
{
synchronized(unProcessedRequests)
{
unProcessedRequests.remove(id);
}
}
public static Card getUnProcessedRequest(String id)
{
synchronized(unProcessedRequests)
{
return unProcessedRequests.get(id);
}
}

public static void addUser(String username,Session session)
{
synchronized(loggedInUsers)
{
loggedInUsers.put(username,session);
}
}
public static void removeUser(String username)
{
synchronized(loggedInUsers)
{
loggedInUsers.remove(username);
}
}
public static String [] getLoggedInUsers()
{
SortedSet<String> tree=new TreeSet<>();
synchronized(loggedInUsers)
{
for(String username: loggedInUsers.keySet())
{
tree.add(username);
}
}
String users [] = new String[tree.size()];
tree.toArray(users);
return users;
}

private static void initDataModel()
{
//Ideally Should Fetch Data from DataBase
//But Here i am Hardcoding
User u1=new User();
u1.setUsername("Amit");
u1.setPassword("amit");

User u2=new User();
u2.setUsername("Bobby");
u2.setPassword("bobby");

User u3=new User();
u3.setUsername("Chetan");
u3.setPassword("chetan");

User u4=new User();
u4.setUsername("Dinesh");
u4.setPassword("dinesh");

User u5=new User();
u5.setUsername("Eshan");
u5.setPassword("eshan");

registeredUsers.put(u1.getUsername(),u1);
registeredUsers.put(u2.getUsername(),u2);
registeredUsers.put(u3.getUsername(),u3);
registeredUsers.put(u4.getUsername(),u4);
registeredUsers.put(u5.getUsername(),u5);
}
}