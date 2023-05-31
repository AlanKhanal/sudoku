package sudoku;

import java.util.Random;

public class repeatCheck {
    public int[][] grid;
    public Random random;

    public repeatCheck() {
        grid = new int[9][9];
        random = new Random();
    }

    public void initializeGrid(int[][] initialGrid) {
        for (int row = 0; row < initialGrid.length; row++) {
            System.arraycopy(initialGrid[row], 0, grid[row], 0, initialGrid[row].length);
        }
    }

    public void solve() {
        if (solveSudoku(0, 0)) {
            System.out.println("Sudoku Solution:");
            printGrid();
        } else {
            System.out.println("No solution exists for the given Sudoku grid.");
        }
    }

    public boolean solveSudoku(int row, int col) {
        if (row == 9) {
            row = 0;
            if (++col == 9) {
                return true; // All cells are filled, Sudoku is solved
            }
        }

        if (grid[row][col] != 0) {
            return solveSudoku(row + 1, col); // Skip already filled cells
        }

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffleArray(numbers);

        for (int num : numbers) {
            if (isValidMove(row, col, num)) {
                grid[row][col] = num;
                if (solveSudoku(row + 1, col)) {
                    return true;
                }
                grid[row][col] = 0; // Undo the move if it leads to an incorrect solution
            }
        }

        return false; // No valid number found, backtrack to the previous cell
    }

    public boolean isValidMove(int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false; // Check if the number already exists in the same row or column
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (grid[i][j] == num) {
                    return false; // Check if the number already exists in the same 3x3 grid
                }
            }
        }

        return true; // The number can be placed in the cell
    }

    public void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public void printGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] initialGrid = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 5, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        repeatCheck sudokuSolver = new repeatCheck();
        sudokuSolver.initializeGrid(initialGrid);
        sudokuSolver.solve();
    }
}
