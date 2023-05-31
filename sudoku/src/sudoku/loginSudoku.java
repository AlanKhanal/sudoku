//package sudoku;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//
//
//class myFrame extends JFrame implements ActionListener{
//	Container c;
//	JLabel userLbl, passLbl,rUser,rPass;
//	JTextField user;
//	JPasswordField pass;
//	JButton login;
//	
//	
//	myFrame(){
//		setTitle("Login || Sudoku");
//		setSize(400,300);
//		setLocation(100,100);
//		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		
//		c=getContentPane();
//		c.setLayout(null);
//		
//		userLbl = new JLabel("Username");
//		passLbl = new JLabel("Password");
//		
//		userLbl.setBounds(10,50,100,20);
//		passLbl.setBounds(10,100,100,20);
//		
//		c.add(userLbl);
//		c.add(passLbl);
//		
//		
//		user = new JTextField();
//		user.setBounds(120,50,120,20);
//		c.add(user);
//		
//		pass = new JPasswordField();
//		pass.setBounds(120,100,120,20);
//		c.add(pass);
//	
//		login = new JButton("Login");
//		login.setBounds(120,150,80,20);
//		c.add(login);
//		
//		login.addActionListener(this);
//
//		
//	}
//	
//	public void actionPerformed(ActionEvent e) {
//		
//		rUser = new JLabel("NICE");
//		rUser.setBounds(120,170,80,20);
//		c.add(rUser);
//	}
//}
//
//public class loginSudoku {
//	public static void main(String[] args) {
//		myFrame frame = new myFrame();
//		frame.setVisible(true);
//	}
//}
