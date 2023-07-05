
package sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;


class myFrame extends JFrame implements KeyListener{
	public String gameID="";
	JLabel timeLabel;
	Timer timer ;
	long startTime;
	long elapsedTime,minutes,seconds,milliseconds;
	String time;
	int easyZero=0,easyOnes=0,medZero=0,medOnes=0,hardZero=0,hardOnes=0;
	int point=0;
	int totHint=0;
	int levelSet=0;
	JButton hint, surrender;
	String hintDialog="";
	String level="";
	void levelSelection() {
			String[] options = {"Easy", "Medium", "Hard"};
            int result = JOptionPane.showOptionDialog(this, "Select the difficulty level", "Level Selection",
            		JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            // Handle the selected option
            if (result >= 0 && result < options.length) {
                String selectedOption = options[result];
                System.out.println("Selected Level: " + selectedOption);
                // Add logic to set the difficulty level based on the selection
               if(selectedOption=="Easy") {
            	   levelSet=15;
            	   level="easy";
               }
               if(selectedOption=="Medium") {
            	   levelSet=35;
            	   level="medium";
               }
               if(selectedOption=="Hard") {
            	   levelSet=55;
            	   level="hard";
               }
            } 

            else {
            	closeFrame();
            }
		}
		String gameIdGenerator() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		int idLength=10;
		Random randomId = new Random();
		StringBuilder sb = new StringBuilder(idLength);
		for(int i= 0; i< idLength;i++) {
			int index = randomId.nextInt(characters.length());
			char randomChar = characters.charAt(index);
			sb.append(randomChar);
		}
		return sb.toString();
	}
	
	
	
	void closeFrame() {
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
		login login = new login();
		login.setVisible(true);
//		dispose();
	}
	public buttonCreator buttonCreator;
	Container c;
	JPanel east,west,north,south,center;
	JTextField[][] grids;
	Boolean[][] filled;
	boolean editable;
	boolean valid;
	void east(){
		east = new JPanel();
		east.setBackground(Color.LIGHT_GRAY);
		east.setPreferredSize(new Dimension(450,450));
		c.add(east,BorderLayout.EAST);
		
		GridLayout gridLayout = new GridLayout(4, 1); // 3 rows, 1 column
	    east.setLayout(gridLayout);
		
	    timer();
		newgame();
		hint();
		surrender();
		//exit();
		//about();
	}
	void timer() {
		timeLabel = new JLabel("00:00:000");
		timeLabel.setFont(new Font("Arial",Font.BOLD,24));
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startTime = System.currentTimeMillis();
		
		timer = new Timer(10,e->{
			
			elapsedTime=System.currentTimeMillis()-startTime;
			minutes = (elapsedTime/60000)%60;
			seconds = (elapsedTime/1000)%60;
			milliseconds=elapsedTime%1000;
			time = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
			timeLabel.setText(time);
		});
		
		timer.start();
		east.add(timeLabel);
		setVisible(true);
	}
	void stopTimer() {
		timer.stop();
		elapsedTime = System.currentTimeMillis()-startTime;
		minutes = (elapsedTime / 60000) % 60;
        seconds = (elapsedTime / 1000) % 60;
        milliseconds = elapsedTime % 1000;
        time = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
        timeLabel.setText(time);
	}
	void newgame() {
		JButton newgame = new JButton("New Game");
		newgame.addActionListener(e ->{
			JDialog newConfirm = new JDialog(this,"New Game", true);
			int choice = JOptionPane.showOptionDialog(this, "Start new game? This will be counted as a loss.", "New Game?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (choice == JOptionPane.YES_OPTION) {
				sudokuGrid newGame = new sudokuGrid();
				newGame.sudokuGrid();
				dispose();
				newConfirm.dispose();
            } else if (choice == JOptionPane.NO_OPTION) {
                newConfirm.dispose();
            } else {
            	newConfirm.dispose();
            }
		});
		east.add(newgame);
		setVisible(true);
	}
	void surrender() {
		surrender = new JButton("Surrender");
		surrender.addActionListener(e->{
			JDialog surrenderConfirm = new JDialog(this, "Surrender",true);
			int choice = JOptionPane.showOptionDialog(this, "Do you want to surrender? This will be counted as a loss.", "Surrender?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			
			if (choice == JOptionPane.YES_OPTION) {

				stopTimer();
				sudokuGenerator generator = new sudokuGenerator();
				int[][] solvedSudoku = generator.data;
				for(int row=0;row<9;row++) {
					for(int col=0;col<9;col++) {
						System.out.println(solvedSudoku[row][col]);
						
						if(grids[row][col].isEditable() && grids[row][col].getText().isEmpty()) {
							grids[row][col].setBackground(Color.red);
						}
						grids[row][col].setText(solvedSudoku[row][col]+"");
						grids[row][col].setEditable(false);
					}
				}
				point=0;
				hint.setEnabled(false);
				surrender.setEnabled(false);
				unsolved();
            } else if (choice == JOptionPane.NO_OPTION) {
                surrenderConfirm.dispose();
            } else {
            	//close
            }
		});
		east.add(surrender);
		setVisible(true);
	}
	
	
	void hint() {
		totHint=3;
		hint = new JButton("Hint");
		hintDialog= "You have 3 hints. Get hint?";
		hint.addActionListener(e->{
			if(totHint==0) {
				JOptionPane.showMessageDialog(this, "You have 0 hints left.");
			}
			else {
				JDialog getHint = new JDialog(this, "Hint",true);
				int choice = JOptionPane.showOptionDialog(this, hintDialog, "Hint?",
	                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				
				if (choice == JOptionPane.YES_OPTION) {
					totHint = totHint-1;
					hintDialog = "You have "+totHint+" hints. Get hint?";
					sudokuGenerator generator = new sudokuGenerator();
					int[][] solvedSudoku = generator.data;
					outerloop:
					for(int row=0;row<9;row++) {
						for(int col=0;col<9;col++) {
//							System.out.println(solvedSudoku[row][col]);
//							
							if(grids[row][col].isEditable()==true && grids[row][col].getText().isEmpty()) {
//								System.out.println(grids[row][col].getText());
								grids[row][col].setText(solvedSudoku[row][col]+"");
								grids[row][col].setEditable(false);
								grids[row][col].setBackground(Color.green);
								checkComplete();
								break outerloop;
							}
						}
					}
	            } else if (choice == JOptionPane.NO_OPTION) {
	                getHint.dispose();
	            } else {
	            	//close
	            }
			}
		});
		east.add(hint);
		setVisible(true);
	}
	void center(){
		center = new JPanel();
		center.setBackground(Color.BLACK);
		center.setLayout(new GridLayout(9,9,0,0));
		c.add(center,BorderLayout.CENTER);
		//Call Grids
		grids();
		sudokuStyle();
		setVisible(true);
	}
	void sudokuStyle() {
		int row,col;
		for(row=0;row<9;row++) {
			for(col=0;col<9;col++) {
				if(col==0 || col ==3 || col==6) {
					Border oldBorder = grids[row][col].getBorder();
					Border blackBorder = BorderFactory.createMatteBorder(0, 3, 0, 0, Color.BLACK);
					Border newBorder = BorderFactory.createCompoundBorder(blackBorder, oldBorder);
					grids[row][col].setBorder(newBorder);
				}
				if(row==0 || row ==3 || row==6) {
					Border oldBorder = grids[row][col].getBorder();
					Border blackBorder = BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK);
					Border newBorder = BorderFactory.createCompoundBorder(blackBorder, oldBorder);
					grids[row][col].setBorder(newBorder);
				}
				if(col==8) {
				Border oldBorder = grids[row][col].getBorder();
				Border blackBorder = BorderFactory.createMatteBorder(0, 0, 0, 3, Color.BLACK);
				Border newBorder = BorderFactory.createCompoundBorder(blackBorder, oldBorder);
				grids[row][col].setBorder(newBorder);
				}
				if(row==8) {
					Border oldBorder = grids[row][col].getBorder();
					Border blackBorder = BorderFactory.createMatteBorder(0, 0, 3, 0, Color.BLACK);
					Border newBorder = BorderFactory.createCompoundBorder(blackBorder, oldBorder);
					grids[row][col].setBorder(newBorder);
				}			
			}
		}
	}
	public void grids(){
		//sudokuGenerator File class
		sudokuGenerator generator = new sudokuGenerator();
		generator.solve();

		grids = new JTextField[9][9];
		int row,col;
 		for(row=0;row<9;row++) {
 			for(col=0;col<9;col++) {
 				grids[row][col] = new JTextField(1);
		  
 				center.add(grids[row][col]);
 				
 				Font currentFont=grids[row][col].getFont();
 				Font italicFont = currentFont.deriveFont(Font.LAYOUT_NO_LIMIT_CONTEXT, currentFont.getSize() + 15);
 				
 				grids[row][col].setFont(italicFont);
 				grids[row][col].setText("" + generator.grid[row][col]);
 				grids[row][col].setEditable(false);
 				grids[row][col].setHorizontalAlignment(JTextField.CENTER);
 			}  
 		}  
		//remove random grids
 		Random randomArr = new Random();
		  //easy=40-45
		  //medium=46-49
		  //hard=50-54
		  //expert=55-60
		for(int i=0;i<=levelSet;i++) {
			int randomRow=randomArr.nextInt(9);
			int randomCol=randomArr.nextInt(9);
			
			//set filled index to true and other to false
			filled = new Boolean[9][9];
			filled[randomRow][randomCol]=false;
			
			if(filled[randomRow][randomCol]==true) {
				//grids[randomRow][randomCol].setBackground(Color.LIGHT_GRAY);
			}
			else {
				//for "To-Fill" indexes
				//grid style
				Font currentFont=grids[randomRow][randomCol].getFont();
 				Font boldFont = currentFont.deriveFont(Font.BOLD, 26);
 				grids[randomRow][randomCol].setFont(boldFont);
 				
				grids[randomRow][randomCol].setText("");
				grids[randomRow][randomCol].setBackground(Color.LIGHT_GRAY);
				grids[randomRow][randomCol].setEditable(true);
				grids[randomRow][randomCol].addKeyListener(this);

			}
		}
	}
	//grid Insertion
	void insertValue(int row, int col, String value) {
		grids[row][col].setText(value);
	}
	myFrame(){
		c=getContentPane();
		c.setLayout(new BorderLayout());
	}
	@Override
	public void keyReleased(KeyEvent e) {
		JTextField source = (JTextField) e.getSource();
		int row=-1;
		int col=-1;
		
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(source == grids[i][j]) {
					row=i;
					col=j;
					break;
				}
			}
		}
		if(row!=-1 && col!= -1) {
			String value=grids[row][col].getText();
			int length = value.length();
            if(length>1) {
				valid=false;
			}
			else {
				//allows 1 number to stay
				valid=true;
			}
            if(valid==false) {
            	grids[row][col].setText("");
    		}
    		else {
    			grids[row][col].setBackground(Color.LIGHT_GRAY);
    			char n =e.getKeyChar();
    			if(n=='1' || n=='2' || n=='3' || n=='4' || n=='5' || n=='6' || n=='7' || n=='8' || n=='9') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    				point=1;
    				checkComplete();
    			}
    			else if(n=='\b') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else {
    				e.consume();
    				grids[row][col].setText("");
    			}
    		}
		}
	}
	//unused event
	@Override public void keyTyped(KeyEvent e) {} @Override public void keyPressed(KeyEvent e) {}
	
	void gameRecord() {
		gameID=gameIdGenerator();
		System.out.println("gameRecord"+gameID);
		
		Connection connection = null;
		try {connection = connect.getConnection();}
		catch(SQLException e) {e.printStackTrace();}
		
		try {
			Statement statement = connection.createStatement();
			String username = fetchSession.getSessionData();
			String gameStart = "INSERT INTO performance(`game_id`,`username`,`solve`,`level`) VALUES('"+gameID+"','"+username+"',0,'"+level+"')";
			statement.executeUpdate(gameStart);
			statement.close();
		}
		catch(SQLException e){e.printStackTrace();}
	}
	void updateRecord() {
		System.out.println("updateRecord"+gameID);
		Connection connection = null;
		try {connection = connect.getConnection();}
		catch(SQLException e) {e.printStackTrace();}
		try {
			Statement statement = connection.createStatement();
			String username = fetchSession.getSessionData();
			String gameStart = "UPDATE performance SET `solve` = 1 WHERE `username` = '"+username+"' and `game_id`='"+gameID+"'";
			statement.executeUpdate(gameStart);
			statement.close();
		}
		catch(SQLException e){e.printStackTrace();}
	}
	void resultData() {
		Connection connection = null;
		try {connection = connect.getConnection();}
		catch(SQLException e) {e.printStackTrace();}
		try {
			Statement statement = connection.createStatement();
			String username = fetchSession.getSessionData();
			String getEasy = "SELECT SUM(solve=0) AS zeros, SUM(solve=1) AS ones FROM performance WHERE username='"+username+"' and level = 'easy'";
			String getMedium = "SELECT SUM(solve=0) AS zeros, SUM(solve=1) AS ones FROM performance WHERE username='"+username+"' and level = 'medium'";
			String getHard = "SELECT SUM(solve=0) AS zeros, SUM(solve=1) AS ones FROM performance WHERE username='"+username+"' and level = 'hard'";
			
			ResultSet resultEasy = statement.executeQuery(getEasy);
			if(resultEasy.next()) {
				easyZero = resultEasy.getInt("zeros");
				easyOnes = resultEasy.getInt("ones");
			}

			ResultSet resultMedium = statement.executeQuery(getMedium);
			if(resultMedium.next()) {
				medZero = resultMedium.getInt("zeros");
				medOnes = resultMedium.getInt("ones");
			}
			
			ResultSet resultHard = statement.executeQuery(getHard);
			if(resultHard.next()) {
				hardZero = resultHard.getInt("zeros");
				hardOnes = resultHard.getInt("ones");
			}
			statement.close();
		}
		catch(SQLException e){e.printStackTrace();}
		
	}
	void checkComplete(){
		boolean solved = true;
		for(int row=0;row<9;row++) {
			for(int col=0;col<9;col++) {
				sudokuGenerator generator = new sudokuGenerator();
				int[][] solvedSudoku = generator.data;
				String input = grids[row][col].getText();
				String solution = solvedSudoku[row][col]+"";
				if(!input.equals(solution)) {
					solved=false;
					break;
				}
			}
			if(!solved) {
				break;
			}
		}
		if(solved) {
			
			stopTimer();
			updateRecord();
			hint.setEnabled(false);
			surrender.setEnabled(false);			
			//execution
			for(int row=0;row<9;row++) {
				for(int col=0;col<9;col++) {
					grids[row][col].setEditable(false);
				}
			}
			resultData();
			JOptionPane.showMessageDialog(this, "Congratulation! You have completed the puzzle!\n"
					+"Lost     Wins      Level\n"
					+easyZero +"            "+ easyOnes +"        Easy\n"
					+medZero +"            "+medOnes +"        Medium\n"
					+hardZero +"            "+hardOnes +"        Hard");
			
			setVisible(true);
		}
	}
	void unsolved() {
		resultData();
		JOptionPane.showMessageDialog(this, "You Lost!\n"
				+"Lost     Wins      Level\n"
				+easyZero +"            "+ easyOnes +"        Easy\n"
				+medZero +"            "+medOnes +"        Medium\n"
				+hardZero +"            "+hardOnes +"        Hard");
		
		setVisible(true);
	}
}
class fetchSession{
	public static String sessionData;
	public static String getSessionData() {
		return sessionData;
	}
	public static void setSessionData(String username) {
		sessionData=username;
	}
	boolean sessionCheck() {
		String sessionData = fetchSession.getSessionData();
		if(sessionData==null) {
			
			return false;
		}
		else {
			return true;
		}
	}
}
public class sudokuGrid {
	static void sudokuGrid() {
		fetchSession session = new fetchSession();
		String username = fetchSession.getSessionData();
		
		boolean sessionFound = session.sessionCheck();
		if(!sessionFound) {
			//how to close the file/frame
			myFrame f = new myFrame();
			f.closeFrame();
			return;
		}
		myFrame f = new myFrame();
		f.levelSelection();
    	f.addKeyListener(new myFrame());
        f.setTitle("Sudoku | " + username);
        f.setSize(850, 450);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        if(f.levelSet!=0) {
        	f.east();
        	f.center();
        	f.setVisible(true);
        	f.gameRecord();
        }
        
        //close
        f.addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		int option = JOptionPane.showConfirmDialog(f,"Exit game?");
            	if(option == JOptionPane.YES_OPTION) {
            		System.exit(0);
            	}
            	else if(option==JOptionPane.NO_OPTION) {
            		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            	}
            	else {
            		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            	}
        	}
    	});
        
	}
    public static void main(String[] args){
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			
		}
    	sudokuGrid();
    }
}

