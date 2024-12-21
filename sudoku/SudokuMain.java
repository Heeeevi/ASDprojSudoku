/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #1
 * 1 - 5026231102 - Ahmed Miftah Ghifari
 * 2 - 5026231103 - Eric Vincentius Jaolis
 * 3 - 5026231156 - Hafiyyuddin Ahmad
 */

// job to be done button reset, yg bolong banyakin aka random, difficulty, timer, status bar (number off cells remaining), Sound Effect Oiiiaoiia waktu menang
package sudoku;

import javax.swing.*;
import java.awt.*;

public class SudokuMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sudoku Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Main panel dengan BorderLayout
            JPanel mainPanel = new JPanel(new BorderLayout());

            // Panel untuk papan permainan
            GameBoardPanel gameBoardPanel = new GameBoardPanel();
            mainPanel.add(gameBoardPanel, BorderLayout.CENTER);

            // Panel untuk pause
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(600, 600));
            gameBoardPanel.setBounds(0, 0, 600, 600);
            layeredPane.add(gameBoardPanel, JLayeredPane.DEFAULT_LAYER);
            JPanel overlayPanel = new JPanel();
            overlayPanel.setBackground(new Color(100, 100, 100, 128));
            overlayPanel.setBounds(0, 0, 600, 600);
            overlayPanel.setVisible(false); // Initially hidden //NEW
            layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);
            mainPanel.add(layeredPane, BorderLayout.CENTER);

            // Panel untuk elemen tambahan (bisa tombol atau label)
            JPanel extrasPanel = new JPanel();
            extrasPanel.setLayout(new FlowLayout());

            // Tombol tambahan untuk restart atau bantuan
            JButton newGameButton = new JButton("New Game");
            JButton solveButton = new JButton("Solve");
            JComboBox<String> difficultyComboBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});

            // Timer dan progressbar
            JLabel timerLabel = new JLabel("Time: 00:00");
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true);

            //Tombol pause
            JButton pauseButton = new JButton("Pause");
            extrasPanel.add(pauseButton);

            extrasPanel.add(new JLabel("Difficulty:"));
            extrasPanel.add(difficultyComboBox);
            extrasPanel.add(newGameButton);
            extrasPanel.add(solveButton);
            extrasPanel.add(timerLabel);
            extrasPanel.add(progressBar);


            // Menambahkan extrasPanel di bagian bawah (SOUTH)
            mainPanel.add(extrasPanel, BorderLayout.SOUTH);

            // Menambahkan listener untuk tombol new game
            newGameButton.addActionListener(e -> {
                String selectedDifficulty = (String) difficultyComboBox.getSelectedItem();
                gameBoardPanel.newGame(selectedDifficulty);
                gameBoardPanel.startTimer(timerLabel);
                gameBoardPanel.updateProgress(progressBar);
                overlayPanel.setVisible(false);
                Audio.MUSIC.stop();
                Audio.MUSIC.music();
            });

            // Menambahkan listener untuk tombol solve (placeholder)
            solveButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Solve functionality not implemented yet."));

            // Menambahkan listener untuk tombol pause
            pauseButton.addActionListener(e -> {
                if (gameBoardPanel.isTimerOn()) {
                    gameBoardPanel.pauseTimer();
                    pauseButton.setText("Resume");
                    overlayPanel.setVisible(true);

                    Audio.CLICK.play();
                } else {
                    gameBoardPanel.resumeTimer();
                    pauseButton.setText("Pause");
                    overlayPanel.setVisible(false);
                    Audio.CLICK.play();
                }
            });

            // Menambahkan main panel ke frame
            frame.add(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Inisialisasi dengan permainan baru
            gameBoardPanel.newGame("Easy");
            gameBoardPanel.startTimer(timerLabel);
            gameBoardPanel.updateProgress(progressBar);
            Audio.MUSIC.music();

        });
    }
}
