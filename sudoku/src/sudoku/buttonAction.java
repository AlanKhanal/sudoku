package sudoku;

public class buttonAction{
	sudokuGenerator generator = new sudokuGenerator();
	myFrame frame = new myFrame();
	public void processData() {
		int[][] solvedSudoku = generator.data;
		
//		IMPORTANT
//		for(int i=0;i<9;i++) {
//			for(int j=0;j<9;j++) {
//				System.out.print(solvedSudoku[i][j]);
//			}
//			System.out.println();
//		}
	}
}
