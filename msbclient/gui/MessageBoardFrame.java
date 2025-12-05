import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
class MessageBoardFrame extends JFrame
{
private JLabel usersLabel;
private JTextArea messageBoardTextArea;
private JTextArea messageToSendTextArea;
private JButton sendButton;
private JList onlineUsersList;

private String username;
private JDialog loginDialog;
private int frameWidth;
private int frameHeight;
private Container container;
private Vector<String> users;

MessageBoardFrame(String username,JDialog loginDialog)
{
this.username=username;
this.loginDialog=loginDialog;
this.setTitle("Message Board ["+username+"]");

users=new Vector<>();
users.add("Amit");
users.add("Sunena");
users.add("Raju");

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
MessageBoardFrame.this.dispose();
loginDialog.setVisible(true);
}
});
}
public static void main(String gg[])
{
MessageBoardFrame mbf=new MessageBoardFrame("Mridul",null);
mbf.setVisible(true);
}
/*
Next Task
1. Remove Main
2. in LoginDialog class
loginButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
MessageBoardFrame mbf=new MessageBoardFrame("Mridul",LoginDialog.this);
LoginDialog.this.setVisible(false);
mbf.setVisible(true);
}
});
*/
}