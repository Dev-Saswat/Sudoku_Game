import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI extends JFrame {
    private SudokuGame game;
    private JTextField[][] cells;

    public SudokuGUI() {
        game = new SudokuGame();
        cells = new JTextField[9][9];

        setTitle("Sudoku Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = createGridPanel();

        JPanel controlPanel = createControlPanel();

        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        initialize();

        setVisible(true);
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(9, 9));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        Font cellFont = new Font("SansSerif", Font.BOLD, 20);
        Border cellBorder = new LineBorder(Color.BLACK, 1);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(cellFont);
                cells[row][col].setBorder(cellBorder);
                cells[row][col].setBackground(getSubMatrixColor(row, col));
                gridPanel.add(cells[row][col]);
            }
        }
        return gridPanel;
    }

    private Color getSubMatrixColor(int row, int col) {
        if ((row / 3 + col / 3) % 2 == 0) {
            return new Color(240, 240, 240); // Light gray color
        } else {
            return Color.WHITE;
        }
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton solveButton = new JButton("Solve");
        JButton generateButton = new JButton("Generate New Puzzle");
        JButton validateButton = new JButton("Validate");

        solveButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        generateButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        validateButton.setFont(new Font("SansSerif", Font.BOLD, 16));

        solveButton.setBackground(new Color(70, 130, 180));
        solveButton.setForeground(Color.WHITE);
        generateButton.setBackground(new Color(34, 139, 34));
        generateButton.setForeground(Color.WHITE);
        validateButton.setBackground(new Color(255, 140, 0));
        validateButton.setForeground(Color.WHITE);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[][] board = new int[9][9];
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        String text = cells[row][col].getText();
                        board[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
                    }
                }
                game.setBoard(board);
                if (game.solve()) {
                    updateBoard(game.getBoard());
                } else {
                    JOptionPane.showMessageDialog(null, "No solution exists!");
                }
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.generateNewPuzzle();
                updateBoard(game.getBoard());
            }
        });

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateBoard()) {
                    JOptionPane.showMessageDialog(null, "The board is valid!");
                } else {
                    JOptionPane.showMessageDialog(null, "The board is invalid!");
                }
            }
        });

        controlPanel.add(solveButton);
        controlPanel.add(generateButton);
        controlPanel.add(validateButton);

        return controlPanel;
    }

    private void initialize() {
        String[] options = {"Solve a given matrix", "Generate a new matrix"};
        int choice = JOptionPane.showOptionDialog(null, "Choose an option",
                "Sudoku Game", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            int[][] emptyBoard = new int[9][9];
            updateBoard(emptyBoard);
        } else {
            game.generateNewPuzzle();
            updateBoard(game.getBoard());
        }
    }

    private void updateBoard(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(new Color(220, 220, 220));
                } else {
                    cells[row][col].setText("");
                    cells[row][col].setEditable(true);
                    cells[row][col].setBackground(getSubMatrixColor(row, col));
                }
            }
        }
    }

    private boolean validateBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String text = cells[row][col].getText();
                if (!text.isEmpty()) {
                    try {
                        int num = Integer.parseInt(text);
                        if (num < 1 || num > 9) {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }
}
