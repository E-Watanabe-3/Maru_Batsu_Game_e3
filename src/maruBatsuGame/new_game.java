package maruBatsuGame;
//実装
//5.勝利判定を実装する
//6.2P側をCPUにして、適当に置くようにする
//7CPUを強くする

import java.util.Scanner; //外部入力

public class new_game{

    public static void main(String[] args) {
        // １．ゲーム開始
        System.out.println("OXゲーム");

        // ２．表示（枠線と数字）3x3盤面初期化
        int[][] masu = new int[3][3]; //二次元配列をint型で3x3定義
        int num = 1; //int型で初期値１～９の値
        for(int i = 0; i < 3; i++){ //盤面カウント123,456,789
            for (int j = 0; j < 3; j++) {
                masu[i][j] = num++;
            }
        }

        printBoard(masu); // 初期盤面表示

        // ４．入力受付
        Scanner scanner = new Scanner(System.in); // 入力機能
        int playerTurn = 1; // 1=O、2=X

        while (true) { // ループ
            System.out.println((playerTurn == 1 ? "O" : "X") + "プレイヤーの番、番号を入力:");
            int key = scanner.nextInt(); // 外部入力受付

            if (key == 0) { // 0 でゲーム終了
                System.out.println("OXゲームを終了しました。");
                break;
            }

            boolean input = false; // 入力の有効性を確認
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (masu[i][j] == key) {
                        masu[i][j] = (playerTurn == 1) ? -1 : -2; // -1=〇、-2=×
                        input = true;
                        break;
                    }
                }
                if (input) break;
            }

            if (!input) { //!異なる入力値
                System.out.println("無効な入力です、他の番号を入力してください：");
                continue;
            }

            printBoard(masu); // 盤面再表示
            playerTurn = (playerTurn == 1) ? 2 : 1;
        }

        scanner.close(); // 入力受付終了
    }

    // 盤面表示
    public static void printBoard(int[][] masu) {
        System.out.println("+---+---+---+");
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (masu[x][y] == -1) {
                    System.out.print("| O ");
                } else if (masu[x][y] == -2) {
                    System.out.print("| X ");
                } else {
                    System.out.print("| " + masu[x][y] + " ");
                }
            }
            System.out.println("|");
            System.out.println("+---+---+---+");
        }
    }
}
//勝敗チェック
/*public static void check(int[][] masu) {
    for(int i = 0; i < 3; i++) {//3行と3列を確認
        if(masu[i][0] == masu[i][1] && masu[i][1] == masu[i][2]) }//横
            return masu[i][0];
        }
}*/
