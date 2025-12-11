import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
interface MessageBoardFrameEventHandler
{
public void logoutAndCloseApplication();
public void postMessage(String message);
}
class MessageBoardFrame extends JFrame
{
private JLabel usersLabel;
private JTextArea messageBoardTextArea;
private JTextArea messageToSendTextArea;
private JButton sendButton;
private JList onlineUsersList;

private String username;
private int frameWidth;
private int frameHeight;
private Container container;
private Vector<String> users;

private MessageBoardFrameEventHandler messageBoardFrameEventHandler;
MessageBoardFrame(String username,MessageBoardFrameEventHandler messageBoardFrameEventHandler)
{
this.username=username;
this.setTitle("Message Board ["+username+"]");
this.messageBoardFrameEventHandler=messageBoardFrameEventHandler;
users=new Vector<>();

initComponents();
addListeners();

//setImageIcon(new ImageIcon("mb_icon.png").getImage());
setResizable(false);
Dimension desktopDimension=Toolkit.getDefaultToolkit().getScreenSize();
frameWidth=desktopDimension.width-50;
frameHeight=desktopDimension.height-50;
int x,y;
x=desktopDimension.width/2-frameWidth/2;
y=desktopDimension.height/2-frameHeight/2;
setSize(frameWidth,frameHeight);
setLocation(x,y);
setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
}
private void initComponents()
{
container=getContentPane();
messageBoardTextArea=new JTextArea();
messageBoardTextArea.setEditable(false);
container.setLayout(new BorderLayout());
container.add(messageBoardTextArea,BorderLayout.CENTER);

messageToSendTextArea=new JTextArea();
sendButton=new JButton("SEND");
JPanel panel1=new JPanel();
panel1.setLayout(new BorderLayout());
panel1.add(messageToSendTextArea,BorderLayout.CENTER);
panel1.add(sendButton,BorderLayout.EAST);
container.add(panel1,BorderLayout.SOUTH);

usersLabel=new JLabel("U S E R S");
onlineUsersList=new JList(users);
JPanel panel2=new JPanel();
panel2.setLayout(new BorderLayout());
panel2.add(usersLabel,BorderLayout.NORTH);
panel2.add(onlineUsersList,BorderLayout.CENTER);
container.add(panel2,BorderLayout.EAST);

Font font=new Font("Veradana",Font.PLAIN,22);
messageBoardTextArea.setFont(font);
messageToSendTextArea.setFont(font);
usersLabel.setFont(font);
onlineUsersList.setFont(font);
sendButton.setFont(font);

panel2.setPreferredSize(new Dimension(300,100));
panel1.setPreferredSize(new Dimension(100,200));

messageToSendTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
messageBoardTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
panel2.setBorder(BorderFactory.createLineBorder(Color.black));

}
private void addListeners()
{
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent ev)
{
//Keep This Method Above
int selectedOption=JOptionPane.showConfirmDialog(MessageBoardFrame.this,"Do You Want to Logout?","LOGOUT",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.YES_OPTION)
{
MessageBoardFrame.this.dispose();
messageBoardFrameEventHandler.logoutAndCloseApplication();
}
}
});
sendButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
String message=messageToSendTextArea.getText().trim();
String complete="you=> "+message+"\n";
messageBoardTextArea.append(complete);
messageBoardFrameEventHandler.postMessage(message);
messageToSendTextArea.setText("");
}
});
}
public void addToMessageBoard(String username,String message)
{
String complete=username+"=> "+message+"\n";
this.messageBoardTextArea.append(complete);
}
public void addErrorToMessageBoard(String error)
{
this.messageBoardTextArea.append(error);
}
public void setOnlineUsers(String [] onlineUsers)
{
Arrays.sort(onlineUsers);
this.users=new Vector();
for(int i=0;i<onlineUsers.length;i++) this.users.add(onlineUsers[i]);
this.onlineUsersList.setListData(this.users);
}
public void addOnlineUser(String username)
{
Vector<String> x=new Vector();
for(String s:this.users) x.add(s);
x.add(username);
this.users=x;
Collections.sort(x);
this.onlineUsersList.setListData(x);
}
public void removeOnlineUser(String username)
{
Vector<String> x=new Vector();
for(String s:this.users) 
{
if(s.equals(username)) continue;
x.add(s);
}
this.users=x;
Collections.sort(x);
this.onlineUsersList.setListData(x);
}
}