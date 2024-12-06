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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class GameBoardPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int GRID_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    private Cell[][] cells;

    private Timer timer;
    private int elapsedSeconds;
    private boolean timerOn = false;

    public GameBoardPanel() {
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        cells = new Cell[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new Cell(row, col);
                add(cells[row][col]);
            }
        }
        CellInputListener listener = new CellInputListener();

        for (int row=0;row<SudokuConstants.GRID_SIZE;row++) {
            for (int col=0;col<SudokuConstants.GRID_SIZE;col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);   // For all editable rows and cols
                }
            }
        }

        setPreferredSize(new Dimension(450, 450));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Menginisialisasi papan baru berdasarkan tingkat kesulitan
     * @param difficulty tingkat kesulitan (easy, medium, hard)
     */
    public void newGame(String difficulty) {
        Puzzle puzzle = new Puzzle();
        int guessAmount = 0;
        if(difficulty.equalsIgnoreCase("easy")) guessAmount = 10;
        else if(difficulty.equalsIgnoreCase("medium")) guessAmount = 20;
        else guessAmount = 30;

        // Generate a new puzzle
        puzzle.newPuzzle(guessAmount);
        System.out.println(guessAmount);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
        resetTimer();
    }
    public void startTimer(JLabel timerLabel) {
        resetTimer();
        timer = new Timer();
        timerOn = true;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timerOn) {
                    elapsedSeconds++;
                    int minutes = elapsedSeconds / 60;
                    int seconds = elapsedSeconds % 60;
                    SwingUtilities.invokeLater(() -> timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)));
                }
            }
        }, 1000, 1000);
    }

    public void pauseTimer() {
        timerOn = false;
    }
    public void resumeTimer() {
        timerOn = true;
    }
    public boolean isTimerOn() {
        return timerOn;
    }

    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        elapsedSeconds = 0;
        timerOn = false;
    }

    public void updateProgress(JProgressBar progressBar) {
        int filledCells = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (!cells[row][col].getText().isEmpty()) {
                    filledCells++;
                }
            }
        }
        int totalCells = GRID_SIZE * GRID_SIZE;
        int progress = (filledCells * 100) / totalCells;
        progressBar.setValue(progress);
    }
    /**
     * Mendapatkan cell tertentu berdasarkan baris dan kolom
     * @param row indeks baris
     * @param col indeks kolom
     * @return Cell yang diminta
     */
    public Cell getCell(int row, int col) {
        if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
            return cells[row][col];
        }
        throw new IndexOutOfBoundsException("Invalid cell coordinates: " + row + ", " + col);
    }

    /**
     * Generate puzzle awal berdasarkan tingkat kesulitan
     * @param difficulty tingkat kesulitan
     * @return Array 2D berisi angka-angka puzzle
     */
    private int[][] generatePuzzle(String difficulty) {
        // Mockup puzzle untuk berbagai tingkat kesulitan
        // Implementasikan generator atau gunakan library jika diperlukan
        if ("easy".equalsIgnoreCase(difficulty)) {
            return new int[][]{
                    {5, 3, 0, 0, 7, 0, 0, 0, 0},
                    {6, 0, 0, 1, 9, 5, 0, 0, 0},
                    {0, 9, 8, 0, 0, 0, 0, 6, 0},
                    {8, 0, 0, 0, 6, 0, 0, 0, 3},
                    {4, 0, 0, 8, 0, 3, 0, 0, 1},
                    {7, 0, 0, 0, 2, 0, 0, 0, 6},
                    {0, 6, 0, 0, 0, 0, 2, 8, 0},
                    {0, 0, 0, 4, 1, 9, 0, 0, 5},
                    {0, 0, 0, 0, 8, 0, 0, 7, 9}
            };
        } else if ("medium".equalsIgnoreCase(difficulty)) {
            return new int[][]{
                    {0, 0, 0, 6, 0, 0, 4, 0, 0},
                    {7, 0, 0, 0, 0, 3, 6, 0, 0},
                    {0, 0, 0, 0, 9, 1, 0, 8, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 5, 0, 1, 8, 0, 0, 0, 3},
                    {0, 0, 0, 3, 0, 6, 0, 4, 5},
                    {0, 4, 0, 2, 0, 0, 0, 6, 0},
                    {9, 0, 3, 0, 0, 0, 0, 0, 0},
                    {0, 2, 0, 0, 0, 0, 1, 0, 0}
            };
        } else if ("hard".equalsIgnoreCase(difficulty)) {
            return new int[][]{
                    {0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 3, 0, 8, 5},
                    {0, 0, 1, 0, 2, 0, 0, 0, 0},
                    {0, 0, 0, 5, 0, 7, 0, 0, 0},
                    {0, 0, 4, 0, 0, 0, 1, 0, 0},
                    {0, 9, 0, 0, 0, 0, 0, 0, 0},
                    {5, 0, 0, 0, 0, 0, 0, 7, 3},
                    {0, 0, 2, 0, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 4, 0, 0, 0, 9}
            };
        }
        throw new IllegalArgumentException("Invalid difficulty level: " + difficulty);
    }

    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell)e.getSource();

            int numberIn = Integer.parseInt(sourceCell.getText());

            for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                    if (cells[row][col].status == CellStatus.WRONG_GUESS) {
                        if(cells[row][col].isEditable()) {
                            cells[row][col].status = CellStatus.TO_GUESS;
                            cells[row][col].paint();
                        } else {
                            cells[row][col].status = CellStatus.GIVEN;
                            cells[row][col].paint();
                        }
                    }
                }
            }

            for (int i = 0; i < SudokuConstants.GRID_SIZE; ++i) {
                if(numberIn == cells[i][sourceCell.col].number) {
                    cells[i][sourceCell.col].status = CellStatus.WRONG_GUESS;
                    cells[i][sourceCell.col].paint();
                }
                else if(numberIn == cells[sourceCell.row][i].number) {
                    cells[sourceCell.row][i].status = CellStatus.WRONG_GUESS;
                    cells[sourceCell.row][i].paint();
                }
            }

            for(int i=(sourceCell.row/3) * 3;i< (sourceCell.row/3) * 3 + 3;i++) {
                for(int j=(sourceCell.col/3) * 3;j<(sourceCell.col/3) * 3 + 3;j++) {
                    if(numberIn == cells[i][j].number) {
                        cells[i][j].status = CellStatus.WRONG_GUESS;
                        cells[i][j].paint();
                    }
                }
            }

            if(numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
                sourceCell.paint();
            }
            sourceCell.paint();   // re-paint this cell based on its status

//            if(isSolved())JOptionPane.showMessageDialog(null, "Congratulation!");
        }
    }
}
