package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class login extends JFrame {
    public JLabel titleLabel,titleLabel2;
    public JLabel usernameLabel;
    public JLabel emailLabel;
    public JLabel gotoSignup;
    public JLabel passwordLabel;
    public JLabel validErr;
    public JTextField usernameField;
    public JTextField emailField;
    public JPasswordField passwordField;
    public JButton signupButton;

    public login() {
        setTitle("SUDOKU");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10,1, 60, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 100,60, 100));
        panel.setBackground(Color.LIGHT_GRAY);

        titleLabel = new JLabel("SUDOKU");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        titleLabel2 = new JLabel("Login");
        titleLabel2.setFont(new Font("Arial", Font.ITALIC, 24));
        titleLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel2);
        
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial",Font.PLAIN,16));
        
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial",Font.PLAIN,16));
        panel.add(usernameField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial",Font.PLAIN,16));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial",Font.PLAIN,16));
        panel.add(passwordField);

        gotoSignup = new JLabel("Create account? Signup!");
        gotoSignup.setFont(new Font("Arial", Font.ITALIC, 16));
        gotoSignup.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(gotoSignup);
        
        signupButton = new JButton("LOGIN");
        signupButton.setFont(new Font("Arial", Font.BOLD, 20));
        signupButton.setBackground(Color.BLUE);
        signupButton.setBorderPainted(false);
        signupButton.setForeground(Color.WHITE);
        
        signupButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(signupButton);
        
        validErr = new JLabel("");
        validErr.setForeground(Color.RED);
        validErr.setFont(new Font("Arial", Font.BOLD, 16));
        validErr.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(validErr);

        getContentPane().add(panel);
//        pack();
        setLocationRelativeTo(null); // Centers the frame on the screen
        
        // Set preferred size for text fields based on the panel's width
        Dimension fieldSize = new Dimension(panel.getWidth() - 40, 30);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        signUpClick();
        gotoSignup();
    }
    void signUpClick() {
    	signupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password=new String(passwordField.getPassword());
				String regex="^[a-zA-Z0-9]+$";
				//Validation
				if(username.isEmpty() || password.isEmpty()) {
					validErr.setText("Fill all the requirements.");
				}
				else if(!username.matches(regex)){
					validErr.setText("Username doesn't support any symbolic character.");
				}
				else if(username.length()<8 || password.length()<8) {
					validErr.setText("Character must be atleast 8 characters.");
				}
				else {
					loginUser(username,password);
					
				}
			}
    		
    	});
    }
    void gotoSignup() {
    	gotoSignup.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				signup login = new signup();
		    	login.setVisible(true);
		    	dispose();
			}
    	});
    	
    }
    public void loginUser(String username,String password) {
    	try {
        	Connection connection = connect.getConnection();
        	Statement statement = connection.createStatement();
        	String query = "SELECT * FROM user WHERE username='"+username+"' and password='"+password+"'";
        	ResultSet resultSet = statement.executeQuery(query);
        		if(resultSet.next()) {
        			validErr.setText("Found");
        		}	
        		else {
//            				statement.executeUpdate("INSERT INTO user(username,password) VALUES('"+username+"','"+password+"')");
//            		    	login login = new login();
//            		    	login.setVisible(true);
//            		    	dispose();
        			validErr.setText("Not Found");
        			}
        		
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    }

    public static void main(String[] args) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            login signupForm = new login();
            signupForm.setVisible(true);
    }
}
