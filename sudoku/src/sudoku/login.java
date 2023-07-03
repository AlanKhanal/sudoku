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
import sudoku.sudokuGrid;

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
    public JButton loginButton;
    private static String username;

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
        
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setBackground(Color.BLUE);
        loginButton.setBorderPainted(false);
        loginButton.setForeground(Color.WHITE);
        
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(loginButton);
        
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
        loginClick();
        gotoSignup();
    }
    void loginClick() {
    	loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password=new String(passwordField.getPassword());
				//Validation
				if(isValidLogin(username,password)) {
//					login.username=username;
					loginUser(username,password);
				}
			}
    		
    	});
    }
    public boolean isValidLogin(String username,String password) {

		String user = usernameField.getText();
		String pass=new String(passwordField.getPassword());
		String regex="^[a-zA-Z0-9]+$";
    	//Validation
		if(username.isEmpty() || password.isEmpty()) {
			validErr.setText("Fill all the requirements.");
			return false;
		}
		else if(!username.matches(regex)){
			validErr.setText("Username doesn't support any symbolic character.");
			return false;
		}
		else if(username.length()<8 || password.length()<8) {
			validErr.setText("Character must be atleast 8 characters.");
			return false;
		}
		else {
			
			return true;
		}
    }
    public void openSudokuFrame() {
    	sudokuGrid game = new sudokuGrid();
    	game.sudokuGrid();
    	dispose();
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
        	String query = "SELECT * FROM users WHERE username='"+username+"' and password='"+password+"'";
        	ResultSet resultSet = statement.executeQuery(query);
        		if(resultSet.next()) {
        			login.username=username;
        			fetchSession.setSessionData(username);
        			openSudokuFrame();
        		}	
        		else {
//            				
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

            login loginForm = new login();
            loginForm.setVisible(true);
    }
}
