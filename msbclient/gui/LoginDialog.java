import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class LoginDialog extends JDialog
{
private Image image;
private JLabel titleLabel;
private JLabel usernameLabel;
private JLabel passwordLabel;
private JTextField usernameTextField;
private JPasswordField passwordField;
private JButton loginButton;
private Container container;
private int dialogWidth=500;
private int dialogHeight=300;
LoginDialog()
{
super(new JFrame(),"Message Board",true);
//image=new ImageIcon("mb_icon.png").getImage();
//((JFrame)getOwner()).setIconImage(image);
initComponents();
addListeners();
Dimension desktopDimension=Toolkit.getDefaultToolkit().getScreenSize();
int x,y;
x=desktopDimension.width/2-dialogWidth/2;
y=desktopDimension.height/2-dialogHeight/2;
setSize(dialogWidth,dialogHeight);
setResizable(false);
setLocation(x,y);
}
private void initComponents()
{
titleLabel=new JLabel("Authentication");
usernameLabel=new JLabel("Username");
passwordLabel=new JLabel("Password");
usernameTextField=new JTextField();
passwordField=new JPasswordField();
loginButton=new JButton("Login");
container=getContentPane();

container.setLayout(null); //very very very important

Font titleFont=new Font("Verdana",Font.BOLD,24);
titleLabel.setFont(titleFont);
titleLabel.setBounds(130,15,250,50);
container.add(titleLabel);

Font font=new Font("Verdana",Font.PLAIN,20);

usernameLabel.setFont(font);
usernameLabel.setBounds(20,80,120,30);
container.add(usernameLabel);
usernameTextField.setFont(font);
usernameTextField.setBounds(150,80,250,30);
container.add(usernameTextField);

passwordLabel.setFont(font);
passwordLabel.setBounds(20,120,120,30);
container.add(passwordLabel);
passwordField.setFont(font);
passwordField.setBounds(150,120,250,30);
container.add(passwordField);

loginButton.setFont(font);
loginButton.setBounds(160,170,150,50);
container.add(loginButton);

}
private void addListeners()
{
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent ev)
{
System.exit(0);
}
});
}
}
class Main
{
public static void main(String gg[])
{
LoginDialog lg=new LoginDialog();
lg.setVisible(true);
}
}
