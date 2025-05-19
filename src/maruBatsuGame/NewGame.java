package maruBatsuGame;
//実装
//5.勝利判定を実装する
//6.2P側をCPUにして、適当に置くようにする
//7CPUを強くする

import java.util.Scanner; //外部入力

public class NewGame{

    //クラスにfinal修飾子の定数宣言し配列数を3x3指定(マジックナンバー対策)
    private static final int VERTICAL = 3; //盤面縦の行数
    private static final int HORIZONTAL = 3; //盤面横の列数

    public static void main(String[] args) {
        // １．ゲーム開始
        System.out.println("＝〇×ゲームへようこそ＝");
        System.out.println("ルール…〇と×を交互に置き、一列揃えた方の勝ちです。");
        // ２．表示（枠線と数字）3x3盤面初期化
        int[][] masu = new int[VERTICAL][HORIZONTAL]; //2次元配列[][]=定数代入

        int num = 1; //int型で初期値１～９の値
        for(int i = 0; i < masu.length; i++){ //盤面行_変数.length=配列の要素数(ループ回数)を自動反映
            for (int j = 0; j < masu.length; j++) { //盤面列_同様
                masu[i][j] = num++; //num値をmasuへ代入
            }
        }
        printBoard(masu); // masu盤面表示

        // ３．入力受付
        Scanner scanner = new Scanner(System.in); // 入力機能
        //int playerTurn = 1; // 1=O、2=X
        boolean isPlayerTurn = true; //OvsX->2名のみ=boolean型true(O)/false(X)の2種で管理が容易
        //ゲームのループ
        while (true) {
            //三項演算子（変数A = B > C ? true : false）でif文が省略できる
            System.out.println((isPlayerTurn ? "〇" : "×") + "プレイヤーの番です。");
            System.out.println("番号1～9を入力しましょう。（0=強制終了）");
            System.out.print("番号：");
            int inputNum = scanner.nextInt(); // 外部入力受付、プレイヤーが入力する数字
            if (inputNum == 0) { // 0を入力すると強制終了
                System.out.println("＝〇×ゲームを強制終了しました＝");
                break; //whileループ停止
            }
            //入力+入力判定の呼び出しと、値が無効な場合の処理
            if (!tryPlace(masu, inputNum, isPlayerTurn)) { //!異なる入力値の場合
                System.out.println("無効な入力です、他の番号を入力してください：");
                continue; //ループのスタート地点へ
            }
            printBoard(masu); //盤面表示

            if(checkWiner(masu)) {//勝ち判定メソッド呼び出ししたら、
                //if(checkWiner(masu)) {
                System.out.println((isPlayerTurn ? "〇" : "×") + "プレイヤーの勝ちです。");
                System.out.println("＝〇×ゲームを通常終了しました＝");
                break;
            }
            //勝者がいない、かつ空白がない場合
            if(checkDraw(masu)) {
                System.out.println("引き分けです。");
                System.out.println("＝〇×ゲームを通常終了しました＝");
                break;
            }
            isPlayerTurn = !isPlayerTurn; //!=倫理否定演算子で反転しターン交代
        }
        scanner.close(); // 入力受付終了
    }
    //５．勝ちチェックメソッド
    public static boolean checkWiner(int[][] masu) {
        //揃った＝勝ち
        for(int i = 0; i < 3; i++) {
            if(masu[i][0] == masu[i][1] && masu[i][1] == masu[i][2] ){ //横
                return true;
            }
            if(masu[0][i] == masu[1][i] && masu[1][i] == masu[2][i]){ //縦
                return true;
            }
            if(masu[0][0] == masu[1][1] && masu[1][1] == masu[2][2]){ //斜め＼
                return true;
            }
            if(masu[0][2] == masu[1][1] && masu[1][1] == masu[2][0]){ //斜め／
                return true;
            }
        }
        return false;
    }
    //５．引き分けチェックメソッド
    public static boolean checkDraw(int[][] masu) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(masu[i][j] != -1 && masu[i][j] != -2) { //OXではない
                    return false; //1個でも空白があれば引き分けはしない=return
                }
            }
        }
        return true;
    }    
    //３．入力&入力判定メソッド
    private static boolean tryPlace(int[][] masu, int inputNum, boolean isPlayerTurn) {
        for (int i = 0; i < masu.length; i++) { //1行～3行
            for (int j = 0; j < masu[i].length; j++) { //1列～3列
                if (masu[i][j] == inputNum) { //masuへ番号代入
                    masu[i][j] = isPlayerTurn ? -1 : -2; //-1=true(O)/-2=false(X)
                    return true; //入力成功
                }
            }
        }
        return false; //入力失敗
    }
    //２．盤面表示メソッド
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
