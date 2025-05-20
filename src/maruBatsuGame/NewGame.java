package maruBatsuGame;

import java.util.Scanner; //外部入力

public class NewGame{

    //クラスにfinal修飾子の定数宣言し配列数を3x3指定(マジックナンバー対策)
    private static final int VERTICAL = 3; //盤面縦の行数_定数へ要素数代入
    private static final int HORIZONTAL = 3; //盤面横の列数

    public static void main(String[] args) {
        // １．ゲーム開始
        System.out.println("＝〇×ゲームへようこそ＝");
        System.out.println("ルール…〇と×を交互に置き、一列揃えた方の勝ちです。");
        // ２．表示（枠線と数字）3x3盤面初期化
        int[][] masu = new int[VERTICAL][HORIZONTAL]; //2次元配列[][]=定数代入

        int num = 1; //int型で初期値１
        for(int i = 0; i < masu.length; i++){ //盤面縦行_変数.length=配列の要素数(ループ回数)を取得
            for (int j = 0; j < masu[i].length; j++) { //盤面横列_同様
                masu[i][j] = num++; //num値をmasuへ代入
            }
        }
        printBoard(masu); // masu盤面表示

        // ３．入力受付
        Scanner scanner = new Scanner(System.in); // 入力機能
        //int playerTurn = 1; // 1=O、2=X
        boolean isPlayerTurn = true; //OvsX->2名のみ=boolean型true(O)/false(X)の2種で管理が容易
        //４．ゲームのループ
        while (true) {
            //三項演算子（変数A = B > C ? true : false）でif文が省略できる
            System.out.println((isPlayerTurn ? "〇" : "×") + "プレイヤーの番です。");
            int inputNum = 0;
            if(isPlayerTurn){
                System.out.println("番号1～9を入力しましょう。（0=強制終了）");
                System.out.print("番号：");
                inputNum = scanner.nextInt(); // 外部入力受付、プレイヤーが入力する数字

/*
                try {
                    if (inputNum >= 0 && inputNum <= 9) {
                        break;
                    } else {
                    }
                }catch(Exception e) {
                    System.out.println("無効な入力です、数字1～9を入力してください。");
                    scanner.next(); // 入力をクリアして再試行
                }
*/                

                //６．COMの操作
            } else { //プレイヤーが入力しないターンの時
                inputNum = getWeakCom(masu, isPlayerTurn); //COMが値を代入
                System.out.println("COMは、"+ inputNum + "を選択しました。") ;
            }
            if(inputNum == 0) { // 0を入力すると強制終了
                System.out.println("＝ゲームを強制終了しました＝");
                break; //whileループ停止
            }
            if(inputNum == -99) { //-99が入力された場合
                System.out.println("＝COMは置く場所がないため、ゲームを終了しました＝");
                break;
            }
            //入力+入力判定の呼び出しと、値が無効な場合の処理
            if(!tryPlace(masu, inputNum, isPlayerTurn)) { //!異なる入力値の場合
                System.out.println("無効な入力です、他の番号を入力してください。");
                continue; //ループのスタート地点へ
            }
            printBoard(masu); //盤面表示

            if(checkWinner(masu)) {//勝ち判定メソッド呼び出ししたら、
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
            isPlayerTurn = !isPlayerTurn; //!=倫理否定演算子でfalseで返す=ターン交代
        }
        scanner.close(); // 入力受付終了
    }
    //６．順番に置く弱いCOMを追加
    public static int getWeakCom(int[][] masu, boolean isPlayerTurn) {
        if(checkDraw(masu)) { //COMが置けない場合は引き分け判定へ
            return -99;
        }
        for(int i = 0; i < masu.length; i++) {
            for(int j = 0; j < masu[i].length; j++) {
                if(masu[i][j] != -1 && masu[i][j] != -2) { //O(-1),X(-2)以外ならば
                    return masu[i][j]; //マス番号を戻す
                }
            }
        }
        return -99; //来ないはず
    }
    //５．勝ちチェックメソッド
    public static boolean checkWinner(int[][] masu) {
        //揃った＝勝ち
        for(int i = 0; i < masu.length; i++) {
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
        for(int i = 0; i < masu.length; i++) {
            for(int j = 0; j < masu[i].length; j++) {
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
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] == -1) { //-1であれば| 〇にする
                    System.out.print("| O ");
                } else if (masu[i][j] == -2) { //-2であれば| ×にする
                    System.out.print("| X ");
                } else {
                    System.out.print("| " + masu[i][j] + " ");//何も代入なければ初期表示
                }
            }
            System.out.println("|"); //マスの右端を|でとじる
            System.out.println("+---+---+---+");//マスの最下部をとじる
        }
    }
}

