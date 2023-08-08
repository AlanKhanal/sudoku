package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class signup extends JFrame {
	public boolean isValid;
    public JLabel titleLabel,titleLabel2;
    public JLabel usernameLabel;
//    public JLabel emailLabel;
    public JLabel gotoLogin;
    public JLabel passwordLabel;
    public JLabel confirmPasswordLabel;
    public JLabel validErr;
    public JTextField usernameField;
//    public JTextField emailField;
    public JPasswordField passwordField;
    public JPasswordField confirmPasswordField;
    public JButton signupButton;

    public signup() {
        setTitle("SUDOKU");
        setSize(650, 550);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\ASUS\\eclipse-workspace\\sudoku\\src\\sudoku\\icon\\newgame.png");    
        setIconImage(icon);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(12,1, 60, 4));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 100,60, 100));
        panel.setBackground(Color.LIGHT_GRAY);

        titleLabel = new JLabel("SUDOKU");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        titleLabel2 = new JLabel("SignUp");
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

        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(new Font("Arial",Font.PLAIN,16));
        panel.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial",Font.PLAIN,16));
        panel.add(confirmPasswordField);
        

        gotoLogin = new JLabel("Have an account? Login!");
        gotoLogin.setFont(new Font("Arial", Font.ITALIC, 16));
        gotoLogin.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(gotoLogin);
        
        signupButton = new JButton("REGISTER");
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
        confirmPasswordField.setPreferredSize(fieldSize);
        signUpClick();
        gotoLogin();
    }
    void signUpClick() {
    	signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password=new String(passwordField.getPassword());
				String confirmPassword=new String(confirmPasswordField.getPassword());
				String regex="^[a-zA-Z0-9]+$";
				//Validation
				if(username.isEmpty() || password.isEmpty() ||confirmPassword.isEmpty()) {
					validErr.setText("Fill all the requirements.");
					isValid=false;
				}
				else if(!username.matches(regex)){
					validErr.setText("Username doesn't support any symbolic character.");
					isValid=false;
				}
				else if(username.length()<8 || password.length()<8 ||confirmPassword.length()<8) {
					validErr.setText("Character must be atleast 8 characters.");
					isValid=false;
				}
				else if(!password.equals(confirmPassword)) {
					validErr.setText("Confirmed password not matched.");
					isValid=false;
				}
				else {
					MessageDigest messageDigest;
					try {
						messageDigest = MessageDigest.getInstance("MD5");
						messageDigest.update(password.getBytes());
						byte[] resultByteArray = messageDigest.digest();
						StringBuilder sb = new StringBuilder();
						for(byte b: resultByteArray) {
							sb.append(String.format("%02x",b));
						}
						password=sb.toString();
						registerUser(username,password);
					} catch (NoSuchAlgorithmException e1) {e1.printStackTrace();}
				}
			}
    		
    	});
    }
    void gotoLogin() {
    	gotoLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				login login = new login();
		    	login.setVisible(true);
		    	dispose();
			}
    	});
    	
    }
    public void registerUser(String username,String password) {
    	try {
        	Connection connection = connect.getConnection();
        	Statement statement = connection.createStatement();
        	String query = "SELECT * FROM users WHERE username='"+username+"'";
        	ResultSet resultSet = statement.executeQuery(query);
    		if(resultSet.next()) {
    			validErr.setText("Username already exists!");
				isValid=false;
    		}	
    		else {
				statement.executeUpdate("INSERT INTO users(username,password) VALUES('"+username+"','"+password+"')");
		    	login login = new login();
		    	login.setVisible(true);
		    	dispose();
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

        signup signupForm = new signup();
        signupForm.setVisible(true);
    }
}
