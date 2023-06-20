
package sudoku;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;

class myFrame extends JFrame implements KeyListener{
	Container c;
	JPanel east,west,north,south,center;
	JTextField[][] grids;
	Boolean[][] filled;
	boolean editable;
	boolean valid;
	int levelSet = 45;
	void east(){
		east = new JPanel();
		east.setBackground(Color.LIGHT_GRAY);
		east.setPreferredSize(new Dimension(450,450));
		c.add(east,BorderLayout.EAST);
	}
//	
//	void west(){
//		west = new JPanel();
//		west.setBackground(Color.BLUE);
//		west.setPreferredSize(new Dimension(150,150));
//		c.add(west,BorderLayout.WEST);	
//	}
	
//	void north(){
//		north = new JPanel();
//		north.setBackground(Color.ORANGE);
//		north.setPreferredSize(new Dimension(70,70));
//		c.add(north,BorderLayout.NORTH);
//	}
//	
//	void south(){
//		south = new JPanel();
//		south.setBackground(Color.YELLOW);
//		south.setPreferredSize(new Dimension(70,70));
//		c.add(south,BorderLayout.SOUTH);
//	}
	
	void center(){
		center = new JPanel();
		center.setBackground(Color.BLACK);
		center.setLayout(new GridLayout(9,9,0,0));
		c.add(center,BorderLayout.CENTER);
		//Call Grids
		grids();
		sudokuStyle();
	}
	//Grids Here
//	void checkAnswer() {
//		for(int i=0;i<9;i++) {
//			for(int j=0;j<9;j++) {
//				if(grids[i][j]==sudo)
//			}
//		}
//	}
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
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		// TODO Auto-generated method stub
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
    			if(n=='1') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else if(n=='2') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else if(n=='3') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else if(n=='4') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else if(n=='5') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else if(n=='6') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else if(n=='7') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else if(n=='8') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else if(n=='9') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else if(n=='\b') {
    				grids[row][col].setBackground(Color.LIGHT_GRAY);
    			}
    			else {
    				e.consume();
//    				grids[row][col].setBackground(Color.RED);
    				grids[row][col].setText("");
    			}
    		}
		}
	}
}
public class sudokuGrid {
    public static void main(String[] args){
    	
    	myFrame f = new myFrame();
    	f.addKeyListener(new myFrame());
    	
        f.setTitle("Sudoku Grid");
        f.setSize(850, 450);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.east();
//        f.west();
//        f.north();
//        f.south();
        f.center();
        
//        f.validation();
        f.setVisible(true);
    }
}

