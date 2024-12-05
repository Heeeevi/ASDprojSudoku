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
* - Status Bar*/

package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuExtras {
    private final SudokuMain mainFrame;
    private final JTextField statusBar;

    public SudokuExtras(SudokuMain frame) {
        this.mainFrame = frame;
        this.statusBar = createStatusBar();
        mainFrame.getContentPane().add(statusBar, BorderLayout.SOUTH);
        mainFrame.setJMenuBar(createMenuBar());
    }

    // Create Menu Bar
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem resetGameItem = new JMenuItem("Reset Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        // Action Listeners for File Menu
        newGameItem.addActionListener(e -> {
            mainFrame.board.newGame();
            updateStatus("Game Start!");
        });
        resetGameItem.addActionListener(e -> {
            mainFrame.board.newGame();
            updateStatus("Game reset!");
        });
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newGameItem);
        fileMenu.add(resetGameItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Options Menu (Placeholder for future expansion)
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem placeholderOption = new JMenuItem("Settings...");
        optionsMenu.add(placeholderOption);

        // Help Menu (Placeholder for future expansion)
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
                mainFrame,
                "Sudoku Game v1.0\nDeveloped by Group wan. entreprise\n conducted by Roger Eric \n Colonel Ahmed \n Commander Hafi.",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        ));
        helpMenu.add(aboutItem);

        // Add Menus to Menu Bar
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);

        return menuBar;
    }

    // Create Status Bar
    private JTextField createStatusBar() {
        JTextField statusBar = new JTextField("Okaeri nasai!");
        statusBar.setEditable(false);
        statusBar.setBackground(Color.LIGHT_GRAY);
        statusBar.setHorizontalAlignment(SwingConstants.LEFT);
        return statusBar;
    }

    // Update Status Bar Message
    public void updateStatus(String message) {
        statusBar.setText(message);
    }
}
