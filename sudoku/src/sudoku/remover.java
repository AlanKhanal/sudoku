package sudoku;

import java.util.Random;

import javax.swing.JTextField;

public class remover {
	public void remover() {
		sudokuGenerator sud = new sudokuGenerator();
		sud.solve();
		
		int[][] randomArray = new int[9][9];
		Random randomArr = new Random();
		
		int[][] grids = new int[9][9];
		for(int i=0;i<20;i++) {
				int randomRow=randomArr.nextInt(9);
				int randomCol=randomArr.nextInt(9);
				
				System.out.println(randomRow + " "+ randomCol);
			}
		
		
	}
	public static void main(String[] args) {
		remover rem = new remover();
		rem.remover();
	}
}
