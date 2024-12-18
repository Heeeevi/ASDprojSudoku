/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231102 - Ahmed Miftah Ghifari
 * 2 - 5026231103 - Eric Vincentius Jaolis
 * 3 - 5026231156 - Hafiyyuddin Ahmad
 */

package sudoku;
/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
    int[][] board;
    boolean[][] isGiven;
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    //boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];


    // Constructor
    public Puzzle() {
        super();
        board = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
        isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int cellsToGuess) {
        // I hardcode a puzzle here for illustration and testing.
        int[][] hardcodedNumbers =
                {{5, 3, 4, 6, 7, 8, 9, 1, 2},
                        {6, 7, 2, 1, 9, 5, 3, 4, 8},
                        {1, 9, 8, 3, 4, 2, 5, 6, 7},
                        {8, 5, 9, 7, 6, 1, 4, 2, 3},
                        {4, 2, 6, 8, 5, 3, 7, 9, 1},
                        {7, 1, 3, 9, 2, 4, 8, 5, 6},
                        {9, 6, 1, 5, 3, 7, 2, 8, 4},
                        {2, 8, 7, 4, 1, 9, 6, 3, 5},
                        {3, 4, 5, 2, 8, 6, 1, 7, 9}};

        // Copy from hardcodedNumbers into the array "numbers"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = hardcodedNumbers[row][col];
            }
        }

        // Need to use input parameter cellsToGuess!
        // Hardcoded for testing, only 2 cells of "8" is NOT GIVEN
        boolean[][] hardcodedIsGiven =
                {{true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true},
                        {true, true, true, true, true, true, true, true, true}};

        for(int i=0;i<cellsToGuess;i++) {
            int row = (int) (Math.random() * hardcodedIsGiven.length);
            int col = (int) (Math.random() * hardcodedIsGiven.length);
            hardcodedIsGiven[row][col] = false;
        }

        // Copy from hardcodedIsGiven into array "isGiven"
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                isGiven[row][col] = hardcodedIsGiven[row][col];
            }
        }
    }
    public void generatePuzzle(String difficulty) {
        // Step 1: Generate a full Sudoku board
        generateFullBoard();

        // Step 2: Determine the number of clues based on difficulty
        int clues;
        switch (difficulty.toLowerCase()) {
            case "easy":
                clues = 40;
                break;
            case "medium":
                clues = 30;
                break;
            case "hard":
                clues = 20;
                break;
            default:
                clues = 30; // Default to medium
        }

        // Step 3: Remove numbers to create the puzzle
        removeNumbers(clues);
    }
    private void generateFullBoard() {
        // Backtracking algorithm to generate a full valid Sudoku board
        fillBoard(0, 0);
    }

    private boolean fillBoard(int row, int col) {
        if (row == SudokuConstants.GRID_SIZE) return true;
        if (col == SudokuConstants.GRID_SIZE) return fillBoard(row + 1, 0);

        for (int num = 1; num <= SudokuConstants.GRID_SIZE; num++) {
            if (isSafeToPlace(row, col, num)) {
                board[row][col] = num;
                if (fillBoard(row, col + 1)) return true;
                board[row][col] = 0; // Backtrack
            }
        }
        return false;
    }
    private boolean isSafeToPlace(int row, int col, int num) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
            if (board[row / SudokuConstants.SUBGRID_SIZE * SudokuConstants.SUBGRID_SIZE + i / SudokuConstants.SUBGRID_SIZE]
                    [col / SudokuConstants.SUBGRID_SIZE * SudokuConstants.SUBGRID_SIZE + i % SudokuConstants.SUBGRID_SIZE] == num)
                return false;
        }
        return true;
    }private void removeNumbers(int clues) {
        int totalCells = SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE;
        int cellsToRemove = totalCells - clues;

        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * SudokuConstants.GRID_SIZE);
            int col = (int) (Math.random() * SudokuConstants.GRID_SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                isGiven[row][col] = false;
                cellsToRemove--;
            }
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean[][] getIsGiven() {
        return isGiven;
    }
}

//(For advanced students) use singleton design pattern for this class