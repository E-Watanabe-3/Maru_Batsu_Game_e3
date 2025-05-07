package demo_seka;

import java.util.Scanner;
//実行クラス
public class demo_play {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // 入力を受け取る

        // ゲームの開始メッセージ
        System.out.println("=== 〇×ゲーム（プレイヤー vs CPU） ===");
        System.out.println("CPUの強さを選んでください：");
        System.out.println("1: 弱いCPU（ランダム）");
        System.out.println("2: 強いCPU（ブロック＋勝ち筋）");
        System.out.print("選択：");
        int cpuLevel = scanner.nextInt(); // ユーザーが1か2を入力

        // ゲームの本体クラスを作成（プレイヤーがX、CPUがO）
        demo_plan game = new demo_plan('X', 'O');

        // ゲームループ（勝敗がつくまで繰り返す）
        while (true) {
            game.printBoard(); // 現在の盤面を表示

            // ▶ プレイヤーの手番
            int cell;
            while (true) {
                System.out.print("あなたの番です（1〜9）：");
                cell = scanner.nextInt();
                if (game.placeMark(cell, 'X')) break;
                System.out.println("そのマスはすでに埋まっています。");
            }

            // 勝ち or 引き分け チェック
            if (game.checkWin('X')) {
                game.printBoard();
                System.out.println("🎉 プレイヤーの勝ち！");
                break;
            }
            if (game.isBoardFull()) {
                game.printBoard();
                System.out.println("🤝 引き分け！");
                break;
            }

            // ▶ CPUの手番
            System.out.println("CPUの番です...");
            if (cpuLevel == 1) {
                game.cpuTurnEasy(); // ランダム
            } else {
                game.cpuTurnHard(); // ブロックなどを含む戦略
            }

            // 勝ち or 引き分け チェック（CPU）
            if (game.checkWin('O')) {
                game.printBoard();
                System.out.println("🤖 CPUの勝ち！");
                break;
            }
            if (game.isBoardFull()) {
                game.printBoard();
                System.out.println("🤝 引き分け！");
                break;
            }
        }

        scanner.close(); // 入力ストリームを閉じる
    }
}