/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231102 - Ahmed Miftah Ghifari
 * 2 - 5026231103 - Eric Vincentius Jaolis
 * 3 - 5026231156 - Hafiyyuddin Ahmad
 */

/* Conducted to add additonal features for Group wan entreprise sudoku that consist of :
* - Menu Bar (Reset, New Game, About  Game
* - Status Bar, sori yg automatic generator susah banget :( besok dulu */

package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class SudokuExtras {

    private final JFrame mainFrame;  // Referensi ke JFrame
    private final JTextField statusBar;
    private final GameBoardPanel gameBoardPanel;

    private int currentRow = 0;  // Baris yang dipilih
    private int currentCol = 0;  // Kolom yang dipilih

    public SudokuExtras(JFrame frame, GameBoardPanel gameBoardPanel) {
        this.mainFrame = frame;
        this.gameBoardPanel = gameBoardPanel;
        this.statusBar = createStatusBar();
        mainFrame.getContentPane().add(statusBar, BorderLayout.SOUTH);
        mainFrame.setJMenuBar(createMenuBar());
        enableMouselessControl(gameBoardPanel);
    }

    // Membuat Menu Bar
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Game
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newEasy = new JMenuItem("New Easy Puzzle");
        JMenuItem newMedium = new JMenuItem("New Medium Puzzle");
        JMenuItem newHard = new JMenuItem("New Hard Puzzle");

        newEasy.addActionListener(e -> {
            gameBoardPanel.newGame("easy");
            updateStatus("New Easy Puzzle started!");
        });
        newMedium.addActionListener(e -> {
            gameBoardPanel.newGame("medium");
            updateStatus("New Medium Puzzle started!");
        });
        newHard.addActionListener(e -> {
            gameBoardPanel.newGame("hard");
            updateStatus("New Hard Puzzle started!");
        });

        gameMenu.add(newEasy);
        gameMenu.add(newMedium);
        gameMenu.add(newHard);

        // Menu File
        JMenu fileMenu = new JMenu("File");
        JMenuItem resetGameItem = new JMenuItem("Reset Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        resetGameItem.addActionListener(e -> {
            gameBoardPanel.newGame("medium"); // Default difficulty
            updateStatus("Game reset!");
        });
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(resetGameItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Menu Help
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
                mainFrame,
                "Sudoku Game v1.0\nDeveloped by Group wan. entreprise\n conducted by Roger Eric \n Colonel Ahmed \n Commander Hafi.",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        ));

        helpMenu.add(aboutItem);

        // Menambahkan menu ke menu bar
        menuBar.add(gameMenu);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    // Membuat Status Bar
    private JTextField createStatusBar() {
        JTextField statusBar = new JTextField("Okaeri nasai!");
        statusBar.setEditable(false);
        statusBar.setBackground(Color.LIGHT_GRAY);
        statusBar.setHorizontalAlignment(SwingConstants.LEFT);
        return statusBar;
    }

    // Memperbarui pesan pada Status Bar
    public void updateStatus(String message) {
        statusBar.setText(message);
    }

    // Navigasi tanpa mouse
    public void enableMouselessControl(GameBoardPanel board) {
        board.setFocusable(true);

        board.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (currentRow > 0) currentRow--;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (currentRow < SudokuConstants.GRID_SIZE - 1) currentRow++;
                        break;
                    case KeyEvent.VK_LEFT:
                        if (currentCol > 0) currentCol--;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (currentCol < SudokuConstants.GRID_SIZE - 1) currentCol++;
                        break;
                    default:
                        break;
                }
                highlightCurrentCell(board);
            }

            @Override
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isDigit(keyChar)) {
                    int number = Character.getNumericValue(keyChar);
                    Cell currentCell = board.getCell(currentRow, currentCol);
                    if (currentCell.isEditable()) {
                        currentCell.setText(String.valueOf(number));
                        currentCell.repaint();
                    }
                }
            }
        });
    }

    private void highlightCurrentCell(GameBoardPanel board) {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                Cell cell = board.getCell(row, col);
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }

        Cell currentCell = board.getCell(currentRow, currentCol);
        currentCell.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        currentCell.requestFocus();
    }
}
