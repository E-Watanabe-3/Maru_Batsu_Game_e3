package gameDemo;

import java.util.Random;
import java.util.Scanner;

public class GameDemo {
    private static final char EMPTY = ' '; // 空のマス
    private static final char PLAYER = 'X'; // プレイヤーの記号
    private static final char CPU = 'O'; // CPUの記号
    private static char[][] board = new char[3][3]; // 盤面
    private static boolean hardMode = false; // CPUの強さ（trueなら強い）

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ゲーム開始時の設定
        System.out.println("〇×ゲームへようこそ！");
        System.out.println("CPUの強さを選択してください (1: 普通, 2: 強い): ");
        int difficulty = scanner.nextInt();
        hardMode = (difficulty == 2); // 2なら強いモード

        initializeBoard(); // 盤面を空にする
        playGame(scanner); // ゲーム開始
    }

    // 盤面を初期化（すべて空にする）
    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    // ゲームの流れ（プレイヤーとCPUのターン）
    private static void playGame(Scanner scanner) {
        boolean gameRunning = true; // ゲームが続いているか
        boolean playerTurn = true; // プレイヤーの番かどうか

        while (gameRunning) {
            printBoard(); // 盤面を表示
            
            if (playerTurn) {
                // プレイヤーの操作
                System.out.println("あなたの番です。1〜9の番号を入力してください:");
                int choice = scanner.nextInt();
                int row = (choice - 1) / 3;
                int col = (choice - 1) % 3;

                if (isValidMove(row, col)) {
                    board[row][col] = PLAYER;
                    playerTurn = false; // 次はCPUの番
                } else {
                    System.out.println("無効な手です。もう一度入力してください。");
                    continue;
                }
            } else {
                // CPUの操作
                cpuMove();
                playerTurn = true; // 次はプレイヤーの番
            }

            // 勝敗判定
            if (checkWin(PLAYER)) {
                printBoard();
                System.out.println("あなたの勝利！");
                gameRunning = false;
            } else if (checkWin(CPU)) {
                printBoard();
                System.out.println("CPUの勝利！");
                gameRunning = false;
            } else if (isDraw()) {
                printBoard();
                System.out.println("引き分け！");
                gameRunning = false;
            }
        }
        scanner.close();
    }

    // CPUの動き（普通モードはランダム、強いモードは最適な手）
    private static void cpuMove() {
        Random random = new Random();

        if (!hardMode) { // 普通モード（ランダムに選ぶ）
            int row, col;
            do {
                row = random.nextInt(3);
                col = random.nextInt(3);
            } while (!isValidMove(row, col));
            board[row][col] = CPU;
        } else { // 強いモード（勝てる手を探す）
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = CPU;
                        if (checkWin(CPU)) return;
                        board[i][j] = EMPTY;
                    }
                }
            }
            int row, col;
            do {
                row = random.nextInt(3);
                col = random.nextInt(3);
            } while (!isValidMove(row, col));
            board[row][col] = CPU;
        }
    }

    // 盤面を表示（〇×のみ）
    private static void printBoard() {
        System.out.println(" ------------- ");
        for (int i = 0; i < 3; i++) {
            System.out.print(" | ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println(" ------------- ");
        }
    }

    // 有効な手かチェック（空いているマスかどうか）
    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == EMPTY;
    }

    // 勝敗の判定（縦・横・斜めのどこか揃ったら勝ち）
    private static boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    // 引き分けの判定（すべて埋まっていたら引き分け）
    private static boolean isDraw() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
