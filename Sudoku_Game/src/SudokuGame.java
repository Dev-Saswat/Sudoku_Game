import java.util.Random;

public class SudokuGame {
    private int[][] board;

    public SudokuGame() {
        board = new int[9][9];
        generateNewPuzzle();
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean solve() {
        return solve(board);
    }

    public void setBoard(int[][] newBoard) {
        board = newBoard;
    }

    private boolean solve(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) {
                                return true;
                            } else {
                                board[row][col] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num ||
                board[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    public void generateNewPuzzle() {
        board = new int[9][9];
        fillDiagonal();
        solve(board);
        removeDigits();
    }

    private void fillDiagonal() {
        for (int i = 0; i < 9; i += 3) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int num;
                do {
                    num = rand.nextInt(9) + 1;
                } while (!isValidInBox(row, col, num));
                board[row + i][col + j] = num;
            }
        }
    }

    private boolean isValidInBox(int row, int col, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[row + i][col + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private void removeDigits() {
        int count = 20;  // Number of cells to remove
        Random rand = new Random();
        while (count != 0) {
            int cellId = rand.nextInt(81);
            int row = cellId / 9;
            int col = cellId % 9;
            if (board[row][col] != 0) {
                board[row][col] = 0;
                count--;
            }
        }
    }

    public void printBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}
