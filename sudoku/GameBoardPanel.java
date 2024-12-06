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

public class GameBoardPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int GRID_SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    private Cell[][] cells;

    public GameBoardPanel() {
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        cells = new Cell[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col] = new Cell(row, col);
                add(cells[row][col]);
            }
        }

        setPreferredSize(new Dimension(450, 450));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Menginisialisasi papan baru berdasarkan tingkat kesulitan
     * @param difficulty tingkat kesulitan (easy, medium, hard)
     */
    public void newGame(String difficulty) {
        int[][] puzzle = generatePuzzle(difficulty);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                cells[row][col].setValue(puzzle[row][col]);
                cells[row][col].setEditable(puzzle[row][col] == 0);
            }
        }
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
}
