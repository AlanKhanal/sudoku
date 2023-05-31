package sudoku;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;

class myFrame extends JFrame implements KeyListener{
	Container c;
	JPanel east,west,north,south,center;
	JTextField[][] grids;
	Boolean[][] filled;
	boolean editable;
	boolean valid;
	void east(){
		east = new JPanel();
		east.setBackground(Color.RED);
		east.setPreferredSize(new Dimension(150,150));
		c.add(east,BorderLayout.EAST);
	}
	
	void west(){
		west = new JPanel();
		west.setBackground(Color.BLUE);
		west.setPreferredSize(new Dimension(150,150));
		c.add(west,BorderLayout.WEST);	
	}
	
	void north(){
		north = new JPanel();
		north.setBackground(Color.ORANGE);
		north.setPreferredSize(new Dimension(70,70));
		c.add(north,BorderLayout.NORTH);
	}
	
	void south(){
		south = new JPanel();
		south.setBackground(Color.YELLOW);
		south.setPreferredSize(new Dimension(70,70));
		c.add(south,BorderLayout.SOUTH);
	}
	
	void center(){
		center = new JPanel();
		center.setBackground(Color.PINK);
		center.setLayout(new GridLayout(9,9));
		c.add(center,BorderLayout.CENTER);
		//Call Grids
		grids();
	}
	//Grids Here

	
	public void grids(){
		//sudokuGenerator File class
		sudokuGenerator generator = new sudokuGenerator();
		generator.solve();

		grids = new JTextField[9][9];
		int row,col;
 		for(row=0;row<9;row++) {
 			for(col=0;col<9;col++) {
 				grids[row][col] = new JTextField();
		  
 				center.add(grids[row][col]);
		  
 				//Solved
 				grids[row][col].setText("" + generator.grid[row][col]);
 				Font boldFont=new Font(grids[row][col].getFont().getName(), Font.BOLD, grids[row][col].getFont().getSize());
 				grids[row][col].setEditable(false);
 				grids[row][col].setFont(boldFont);
 			}  
 		}  
		  //remove random grids
 		Random randomArr = new Random();
		  
		  //easy=40-45
		  //medium=46-49
		  //hard=50-54
		  //expert=55-60
		  
		 int levelSet = 50;
			for(int i=0;i<=levelSet;i++) {
				int randomRow=randomArr.nextInt(9);
				int randomCol=randomArr.nextInt(9);
				
				//set filled index to true and other to false
				filled = new Boolean[9][9];
				filled[randomRow][randomCol]=false;
				
				if(filled[randomRow][randomCol]==true) {
				//grids[randomRow][randomCol].setBackground(Color.GRAY);
				}
				else {
					//for "To-Fill" indexes
					//grid style
					Font regularFont=new Font(grids[randomRow][randomCol].getFont().getName(), Font.PLAIN, grids[randomRow][randomCol].getFont().getSize());
					grids[randomRow][randomCol].setFont(regularFont);
					grids[randomRow][randomCol].setText("");
					grids[randomRow][randomCol].setBackground(Color.GRAY);
					grids[randomRow][randomCol].setEditable(true);
				}
			}
	}
	
	public void gridInput() {
		int row,col;
		for(row=0;row<=8;row++) {
			for(col=0;col<=8;col++) {
				if(grids[row][col].isEditable()) {
//					System.out.println("Grid"+ row + "," + col +"=" + grids[row][col].isEditable());
					editable=true;
				}else {
					editable=false;
				}
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
		int row,col;
		for(row=0;row<9;row++) {
			for(col=0;col<9;col++) {
				String text = grids[row][col].getText();
				int value = Integer.parseInt(text);
				if(value<=9) {
					valid=true;
					
				}
				else if(value>=1) {
					valid=true;
				}
				else {
					valid = false;
				}
				grids[row][col].setEditable(false);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

public class sudokuGrid {
    public static void main(String[] args) {
    	myFrame f = new myFrame();
    	f.addKeyListener(new myFrame());
        f.setTitle("Sudoku Grid");
        f.setSize(700, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.east();
        f.west();
        f.north();
        f.south();
        f.center();
        
        f.gridInput();
        f.setVisible(true);
    }
}
