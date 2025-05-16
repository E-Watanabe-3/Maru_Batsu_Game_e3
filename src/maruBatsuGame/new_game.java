package maruBatsuGame;
//実装
//5.勝利判定を実装する
//6.2P側をCPUにして、適当に置くようにする
//7CPUを強くする

import java.util.Scanner; //外部入力

public class new_game{

    public static void main(String[] args) {
        // １．ゲーム開始
        System.out.println("＝〇×ゲームへようこそ＝");
        System.out.println("ルール…〇と×を交互に置き、一列揃えた方の勝ちです。");
        // ２．表示（枠線と数字）3x3盤面初期化
        int[][] masu = new int[3][3]; //二次元配列をint型で3x3定義
        int num = 1; //int型で初期値１～９の値
        for(int i = 0; i < 3; i++){ //盤面_行_3カウント(123,456,789)
            for (int j = 0; j < 3; j++) { //盤面_列_3カウント
                masu[i][j] = num++; //num値をmasuへ代入
            }
        }
        printBoard(masu); // masu盤面表示

        // ４．入力受付
        Scanner scanner = new Scanner(System.in); // 入力機能
        int playerTurn = 1; // 1=O、2=X

        while (true) { //ゲームループ
            //三項演算子（A = B > C ? true : false）でif文が省略できる
            System.out.println((playerTurn == 1 ? "〇" : "×") + "プレイヤーの番です。");
            System.out.println("番号1～9を入力しましょう。（0=強制終了）");
            System.out.print("番号：");
            int key = scanner.nextInt(); // 外部入力受付

            if (key == 0) { // 0 で強制終了
                System.out.println("＝〇×ゲームを強制終了しました＝");
                break;
            }
            //入力判定 boolean=true/falseの値のみ
            boolean input = false; //入力失敗したらwhileループ
            for (int i = 0; i < 3; i++) { //1行～3行
                for (int j = 0; j < 3; j++) { //1列～3列
                    if (masu[i][j] == key) { //masuへ番号代入
                        masu[i][j] = (playerTurn == 1) ? -1 : -2; // -1=〇、-2=×とする
                        input = true; //入力成功した場合、
                        break; //for文を抜ける
                    }
                }
                if (input) break; //入力成功のためfor抜ける
            }

            if (!input) { //!異なる入力値の場合
                System.out.println("無効な入力です、他の番号を入力してください：");
                continue; //ループのスタート地点へ
            }
            printBoard(masu); //盤面表示
            if(check(masu)) {//勝敗判定メソッド呼び出ししたら、
                System.out.println("＝〇×ゲームを通常終了しました＝");
                break;
            }
            playerTurn = (playerTurn == 1) ? 2 : 1;//ターン交代
        }
        scanner.close(); // 入力受付終了
     

    }

    //勝敗チェックメソッド
    public static boolean check(int[][] masu) {
        //揃った＝勝ち
        for(int i = 0; i < 3; i++) {
            if(masu[i][0] == masu[i][1] && masu[i][1] == masu[i][2] ){ //横
                System.out.println("あなたの勝ちです。");
                return true;
            }
                if(masu[0][i] == masu[1][i] && masu[1][i] == masu[2][i]){ //縦
                    System.out.println("あなたの勝ちです。");
                    return true;
                }
                }
                    if(masu[0][0] == masu[1][1] && masu[1][1] == masu[2][2]){ //斜め＼
                        System.out.println("あなたの勝ちです。");
                        return true;
                    }
                    if(masu[0][2] == masu[1][1] && masu[1][1] == masu[2][0]){ //斜め／
                            System.out.println("あなたの勝ちです。");
                            return true;
                    }
        //揃わない＝引き分け
        boolean draw = true;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(masu[i][j] != -1 && masu[i][j] != -2) { //OXではない
                    draw = false; //引き分けはしない
                }
            }
        }
        if(draw) {
        System.out.println("引き分けです。");
        return true;
        }
        return false; //引き分けしなければ判定終了
    }
    // 盤面表示メソッド
    public static void printBoard(int[][] masu) {
        System.out.println("+---+---+---+");
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (masu[x][y] == -1) { //-1であれば| 〇にする
                    System.out.print("| O ");
                } else if (masu[x][y] == -2) { //-2であれば| ×にする
                    System.out.print("| X ");
                } else {
                    System.out.print("| " + masu[x][y] + " ");//何も代入なければ初期表示
                }
            }
            System.out.println("|"); //マスの右端を|でとじる
            System.out.println("+---+---+---+");//マスの最下部をとじる
        }
    }
 }
