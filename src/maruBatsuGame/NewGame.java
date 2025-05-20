package maruBatsuGame;

import java.util.Scanner; //外部入力

public class NewGame {

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
        for (int i = 0; i < masu.length; i++) { //盤面縦行_変数.length=配列の要素数(ループ回数)を取得
            for (int j = 0; j < masu[i].length; j++) { //盤面横列_同様
                masu[i][j] = num++; //num値をmasuへ代入
            }
        }
        printBoard(masu); // masu盤面表示

        // ３．入力受付
        Scanner scanner = new Scanner(System.in); // 入力機能
        //int playerTurn = 1; // 1=O、2=X
        //７.COM選択メニュー
        System.out.println("COMの難易度を選択してください。");
        System.out.println("弱いCOM：１");
        System.out.println("強いCOM：２");
        int comSelect; //初期値
        while (true) {
            System.out.print("番号：");
            comSelect = scanner.nextInt();
            if (comSelect == 1 || comSelect == 2) {//1もしくは2を入力でループ終了
                break;
            } //1~2以外の入力
            System.out.println("無効な入力です、１か２を入力してください。");
        }
        //４．ゲームのループ
        boolean isPlayerTurn = true; //OvsX->2名のみ=boolean型true(O)/false(X)の2種で管理が容易
        while (true) {
            //三項演算子（変数A = B > C ? true : false）でif文が省略できる
            System.out.println((isPlayerTurn ? "〇" : "×") + "プレイヤーの番です。");
            int inputNum = 0;
            if (isPlayerTurn) {
                System.out.println("番号1～9を入力しましょう。（0=強制終了）");
                System.out.print("番号：");
                inputNum = scanner.nextInt(); // 外部入力受付、プレイヤーが入力する数字
                //６．COMの操作
            } else {
                if (comSelect == 1) {
                    inputNum = getWeakCom(masu, isPlayerTurn); //弱いCOM
                } else {
                    inputNum = getStrongCom(masu, isPlayerTurn); //強いCOM
                }
                System.out.println("COMは、" + inputNum + "を選択しました。");
            }
            if (inputNum == 0) { // 0を入力すると強制終了
                System.out.println("＝ゲームを強制終了しました＝");
                break; //whileループ停止
            }
            if (inputNum == -99) { //-99が入力された場合
                System.out.println("＝COMは置く場所がないため、ゲームを終了しました＝");
                break;
            }
            //入力+入力判定の呼び出しと、値が無効な場合の処理
            if (!tryPlace(masu, inputNum, isPlayerTurn)) { //!異なる入力値の場合
                System.out.println("無効な入力です、他の番号を入力してください。");
                continue; //ループのスタート地点へ
            }
            printBoard(masu); //盤面表示

            if (checkWinner(masu, isPlayerTurn ? -1 : -2)) {//勝ち判定メソッド呼び出ししたら、
                //if(checkWiner(masu)) {
                System.out.println((isPlayerTurn ? "〇" : "×") + "プレイヤーの勝ちです。");
                System.out.println("＝〇×ゲームを通常終了しました＝");
                break;
            }
            //勝者がいない、かつ空白がない場合
            if (checkDraw(masu)) {
                System.out.println("引き分けです。");
                System.out.println("＝〇×ゲームを通常終了しました＝");
                break;
            }
            isPlayerTurn = !isPlayerTurn; //!=倫理否定演算子でfalseで返す=ターン交代
        }
        scanner.close(); // 入力受付終了
    }

    //６．順番に置く弱いCOM
    public static int getWeakCom(int[][] masu, boolean isPlayerTurn) {
        if (checkDraw(masu)) { //COMが置けない場合は引き分け判定へ
            return -99;
        }
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] != -1 && masu[i][j] != -2) { //O(-1),X(-2)以外ならば
                    return masu[i][j]; //マス番号を戻す
                }
            }
        }
        return -99; //来ないはず
    }

    //７．強いCOMを追加
    public static int getStrongCom(int[][] masu, boolean isPlayerTurn) {
        int comValue = isPlayerTurn ? -1 : -2;//COMターンにO(-1)orX(-2)代入
        int playerValue = isPlayerTurn ? -2 : -1;//逆の値代入

        //COMが勝てる場所を探す
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] != -1 && masu[i][j] != -2) { //OXがなければ、
                    int kari = masu[i][j]; //空きマスを仮代入
                    masu[i][j] = comValue;//COMの値を仮代入
                    if (checkWinner(masu, comValue)) {//仮代入が勝利ならば
                        masu[i][j] = kari;//仮代入を戻す
                        return kari;
                    }
                    masu[i][j] = kari;//勝てないならマスを元に戻す
                }
            }
        }
        //相手が勝ちそうな場所を防ぐ
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] != -1 && masu[i][j] != -2) {
                    int kari = masu[i][j];
                    masu[i][j] = playerValue; //仮代入
                    if (checkWinner(masu, playerValue)) {//相手の勝利判定
                        masu[i][j] = kari;//相手が勝てるならＣＯＭが先に置いて戻す
                        return kari;
                    }
                    masu[i][j] = kari;//相手が勝てないなら元に戻す
                }
            }
        }
        //それ以外（弱いCOMと同じ動きへ）
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] != -1 && masu[i][j] != -2) {
                    return masu[i][j];
                }
            }
        }
        return -99; // 置ける場所がない場合
    }
    //５．勝ちチェックメソッド
    public static boolean checkWinner(int[][] masu, int playerValue) {
        //揃った＝勝ち
        for (int i = 0; i < masu.length; i++) {
            if (masu[i][0] == playerValue && masu[i][1] == playerValue && masu[i][2] == playerValue) { //横
                return true;
            }
            if (masu[0][i] == playerValue && masu[1][i] == playerValue && masu[2][i] == playerValue) { //縦
                return true;
            }
        }
        if (masu[0][0] == playerValue && masu[1][1] == playerValue && masu[2][2] == playerValue) { //斜め＼
            return true;
        }
        if (masu[0][2] == playerValue && masu[1][1] == playerValue && masu[2][0] == playerValue) { //斜め／
            return true;
        }
        return false;
    }

    //５．引き分けチェックメソッド
    public static boolean checkDraw(int[][] masu) {
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] != -1 && masu[i][j] != -2) { //OXではない
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
