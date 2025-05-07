package gameDemo;

import java.util.Random;//CPUがランダムな動きをする
import java.util.Scanner;//キーボードの入力を受け付ける

public class GameDemo {

    // 盤面：3×3 の文字配列
    static char[][] board = {
        {' ', ' ', ' '},
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };

    static char playerMark = 'X';//プレイヤー
    static char cpuMark = 'O';//コンピューター

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);//キーボードの入力を受け付ける

        // スタート画面を表示
        printStartScreen();//CPU強さの選択1or2
        int cpuLevel = scanner.nextInt();//キーボードの数値を受ける1or2

        boolean gameEnded = false;//ゲームが終了したかどうかを判断、falseなら続行

        // メインゲームループ
        while (!gameEnded) {//ゲームが終了したかどうかを判断、falseなら続行
            printBoard(); // 現在の盤面を表示

            // プレイヤーの手番
            playerTurn(scanner);
            if (checkWin(playerMark)) {//指定したマークが３つ並んでいるか調べる
                printBoard();
                System.out.println("🎉 プレイヤーの勝ち！");
                break;//ループを抜ける
            }
            if (isBoardFull()) {//空きマスがない → 引き分け
                printBoard();
                System.out.println("🤝 引き分け！");
                break;
            }

            // CPUの手番（選んだレベルに応じて動作）
            if (cpuLevel == 1) {
                cpuTurnEasy();//ランダム
            } else {
                cpuTurnHard();//ブロック、勝ち狙い
            }

            if (checkWin(cpuMark)) {//指定したマークが３つ並んでいるか調べる
                printBoard();
                System.out.println("🤖 CPUの勝ち！");
                break;
            }
            if (isBoardFull()) {//空きマスがない → 引き分け
                printBoard();
                System.out.println("🤝 引き分け！");
                break;
            }
        }

        scanner.close();//ゲームが終了したら、入力を閉じる
    }

    // 🔰 ゲーム開始時の表示とレベル選択
    public static void printStartScreen() {
        System.out.println("=== 〇×ゲーム（プレイヤー vs CPU） ===");
        System.out.println("CPUの強さを選んでください：");
        System.out.println("1: 弱いCPU（ランダム）");
        System.out.println("2: 強いCPU（ブロック＋勝ち筋）");
        System.out.print("選択：");
    }

    // 🧱 現在の盤面を表示（空きマスには 1〜9 の番号を表示）
    public static void printBoard() {
        System.out.println("-------------");
        int count = 1; // 1〜9の番号をつけるためのカウンタ
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    System.out.print(count + " | ");
                } else {
                    System.out.print(board[i][j] + " | ");
                }
                count++;
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    // 🎮 プレイヤーのターン（番号入力 → マスに変換）
    public static void playerTurn(Scanner scanner) {
        int cell;
        while (true) {
            System.out.println("あなたの番です。1〜9の番号を入力してください。");
            System.out.print("番号：");
            cell = scanner.nextInt();

            if (cell < 1 || cell > 9) {
                System.out.println("⚠ 1〜9の数字を入力してください。");
                continue;
            }

            // ユーザーが１～９を入力、配列の行・列に変換する
            int row = (cell - 1) / 3;
            int col = (cell - 1) % 3;

            if (board[row][col] == ' ') {
                board[row][col] = playerMark;
                break;
            } else {
                System.out.println("⚠ そのマスはすでに埋まっています。");
            }
        }
    }

    // CPU（レベル1）：ランダムに空いているマスに置く
    public static void cpuTurnEasy() {
        Random rand = new Random();
        int row, col;
        System.out.println("CPU（レベル1）の番です。");
        while (true) {
            row = rand.nextInt(3);//0~2のランダムな数を作って、空いている場所におく
            col = rand.nextInt(3);//よわいCPU↑
            if (board[row][col] == ' ') {
                board[row][col] = cpuMark;
                break;
            }
        }
    }

    // CPU（レベル2）：勝てる or 相手をブロックする or ランダム
    public static void cpuTurnHard() {
        System.out.println("CPU（レベル2）の番です。");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    // 勝てる手を試す
                    board[i][j] = cpuMark;
                    if (checkWin(cpuMark)) return;

                    // 相手が勝ちそうなら防ぐ
                    board[i][j] = playerMark;
                    if (checkWin(playerMark)) {
                        board[i][j] = cpuMark;
                        return;
                    }

                    // どちらでもないなら元に戻す
                    board[i][j] = ' ';
                }
            }
        }

        // ランダムに置く（他になければ）
        cpuTurnEasy();
    }

    // 勝利条件を判定（縦・横・斜め）
    public static boolean checkWin(char mark) {
        for (int i = 0; i < 3; i++) {
            // 横 or 縦のチェック
            if ((board[i][0] == mark && board[i][1] == mark && board[i][2] == mark) ||
                (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark)) {
                return true;
            }
        }
        // 斜めのチェック
        if ((board[0][0] == mark && board[1][1] == mark && board[2][2] == mark) ||
            (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark)) {
            return true;
        }

        return false;
    }

    // 盤面が埋まっているかどうか
    public static boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') return false;
        return true;
    }
}
