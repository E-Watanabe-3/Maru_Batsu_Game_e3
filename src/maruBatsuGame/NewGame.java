package maruBatsuGame;

import java.util.Scanner; //外部入力

public class NewGame {

    //クラスにfinal修飾子の定数宣言し配列数を3x3指定(マジックナンバー対策)
    private static final int VERTICAL = 3; //盤面縦の行数_定数へ要素数代入
    private static final int HORIZONTAL = 3; //盤面横の列数
    //９．定数としてプレイヤーＯ(-1)と×(-2)を追加
    private static final int PLAYER_O = -1;
    private static final int PLAYER_X = -2;
    
    //メインメソッド
    public static void main(String[] args) {
        // １．ゲーム開始（タイトル）
        System.out.println("＝〇×ゲームへようこそ＝");
        System.out.println("ルール…〇と×を交互に置き、一列揃えた方の勝ちです。");
        // ２．表示（枠線と数字）3x3盤面初期化
        int[][] masu = new int[VERTICAL][HORIZONTAL]; //2次元配列[][]=定数代入

        int num = 1; //整数int型で初期値１をnum(整数)に代入
        for (int i = 0; i < masu.length; i++) { //盤面縦行_変数.length=配列の要素数(ループ回数)を取得
            for (int j = 0; j < masu[i].length; j++) { //盤面横列_同様
                masu[i][j] = num++; //num値整数+1をmasuへ代入
            }
        }
        printBoard(masu); // masu代入処理後の盤面表示

        //３．入力受付
        Scanner scanner = new Scanner(System.in); // 入力受付開始
        //８．先攻と後攻追加
        while(true) {
        System.out.println("先攻(1)か、後攻(2)を番号で選んでください。");
        int turnSelect = 0;//先攻、後攻を決める変数を代入し整数入力受付
        if (scanner.hasNextInt()) {
            turnSelect = scanner.nextInt();
            if(turnSelect == 1 || turnSelect == 2) {
                break;
            }
        } else {
            scanner.nextLine();
        }
        System.out.println("無効な入力です、１か２を入力してください。");
        }
    boolean isPlayerChoice = (turnSelect == 1); //true=先攻=O
        
        
        boolean isPlayerTurn = isPlayerChoice;//最初のターンを固定true/false

        //７.COM追加（選択メニュー）
        System.out.println("COMの難易度を選択してください。");
        System.out.println("弱いCOM：１");
        System.out.println("強いCOM：２");
        int comSelect; //COMの名前を初期値で決める
        while (true) {
            System.out.print("番号：");
            if (scanner.hasNextInt()) {//整数が入力されているかチェック
                comSelect = scanner.nextInt(); //scanner.nextInt(戻り値);=整数Intの外部入力を受け取る
                if (comSelect == 1 || comSelect == 2) { //1もしくは2を入力でループ終了
                    break; //正常な入力でループ終了
                } //1~2以外の入力以外の場合
            } else {
                scanner.nextLine(); //誤入力を読み捨てる（バッファクリア）
            }
            System.out.println("無効な入力です、１か２を入力してください。");
        }
        System.out.println("＝ゲームを開始します＝");
        printBoard(masu);
        
        //４．ゲームのループ
       // boolean isPlayerTurn = true; //OvsX->2つ=boolean型true(O)/false(X)の2種で管理が容易
        while (true) {
            //三項演算子（変数A = B > C ? true : false）でif文が省略できる
            System.out.println((isPlayerTurn ? "〇" : "×") + "プレイヤーの番です。");
            int inputNum = 0;
            if (isPlayerTurn) {
                System.out.println("番号1～9を入力しましょう。（0=強制終了）");
                System.out.print("番号：");
                inputNum = scanner.nextInt(); //外部入力受付、プレイヤーが入力する数字
            //６．COMの処理
            } else {
                if (comSelect == 1) { //COMの難易度を決める
                    inputNum = getWeakCom(masu, isPlayerTurn); //弱いCOM=1選択時
                } else {
                    inputNum = getStrongCom(masu, isPlayerTurn); //強いCOM=2選択時
                }
                System.out.println("COMは、" + inputNum + "を選択しました。");
            }
            if (inputNum == 0) { // 0を入力したら強制終了する
                System.out.println("＝ゲームを強制終了しました＝");
                break; //whileループ停止
            }
            if (inputNum == -99) { //置ける場所がないなら-99を返した場合
                System.out.println("＝置く場所がないため、ゲームを終了しました＝");
                break;
            }
            //入力+入力判定の呼び出しと、値が無効な場合の処理
            if (!tryPlace(masu, inputNum, isPlayerTurn)) { //!異なる入力値の場合
                System.out.println("＝無効な入力です、他の番号を入力してください＝");
                continue; //ループのスタート地点へ
            }
            printBoard(masu); //盤面更新
          //勝ち判定メソッド呼び出す
            if (checkWinner(masu, isPlayerTurn ? -1 : -2)) { //true(-1):false(-2)=O:X
                System.out.println((isPlayerTurn ? "〇" : "×") + "プレイヤーの勝ちです！");
                System.out.println("＝〇×ゲームを終了しました＝");
                break;
            }
            //勝者がいない、かつ空白がない場合
            if (checkDraw(masu)) {
                System.out.println("引き分けです。");
                System.out.println("＝〇×ゲームを終了しました＝");
                break;
            }
            isPlayerTurn = !isPlayerTurn; //!=倫理否定演算子でfalseで返しターン交代
        }
        scanner.close(); // 入力受付終了
    }
    //６．順番に置く弱いCOM
    public static int getWeakCom(int[][] masu, boolean isPlayerTurn) {
        if (checkDraw(masu)) { //COMが置けない場合は引き分け判定へ
            return -99;
        }
        for (int i = 0; i < masu.length; i++) { //盤面チェック
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] != PLAYER_O && masu[i][j] != PLAYER_X) { //O(-1),X(-2)以外ならば
                    return masu[i][j]; //マス番号を戻す
                }
            }
        }
        return -99; //来ないはず
    }

    //７．強いCOMを追加
    public static int getStrongCom(int[][] masu, boolean isPlayerTurn) {
        int comValue = isPlayerTurn ? PLAYER_O : PLAYER_X;//三項演算子でCOMターンにO(-1)orX(-2)代入
        int playerValue = isPlayerTurn ? PLAYER_X : PLAYER_O;//逆の値（プレイヤー側の仮の値）代入
        //COMが勝てる場所を探す
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] != PLAYER_O && masu[i][j] != PLAYER_X) { //OX以外のマスがあれば
                    int kari = masu[i][j]; //空きマスを仮代入し、一時保存
                    masu[i][j] = comValue;//空きマスにCOMの値を仮代入
                    if (checkWinner(masu, comValue)) {//仮代入値を勝利判定にかけて
                        masu[i][j] = kari;//仮代入を戻す
                        return kari; //置けば勝てる場合、そのマスを返す
                    }
                    masu[i][j] = kari;//勝てないなら盤面を戻してマスをチェック
                }
            }
        }
        //相手が勝ちそうな場所を防ぐ
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] != PLAYER_O && masu[i][j] != PLAYER_X) {//OX以外のマスへ
                    int kari = masu[i][j]; //仮にプレイヤーが置いたら
                    masu[i][j] = playerValue; //プレイやー側の値を仮代入し
                    if (checkWinner(masu, playerValue)) {//相手の勝利判定
                        masu[i][j] = kari;//元に戻す
                        return kari; //相手が勝てるなら防ぐようにCOMが置く
                    }
                    masu[i][j] = kari;//相手が勝てないなら元に戻す
                }
            }
        }
        //勝てない、防ぐ必要もない時（弱いCOMと同じ動き＝順番に置く）
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] != PLAYER_O && masu[i][j] != PLAYER_X) {
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
                if (masu[i][j] != PLAYER_O && masu[i][j] != PLAYER_X) { //OX以外
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
                    masu[i][j] = isPlayerTurn ? PLAYER_O : PLAYER_X; //-1=true(O)/-2=false(X)
                    return true; //入力成功
                }
            }
        }
        return false; //入力失敗
    }

    //２．盤面表示メソッド
    public static void printBoard(int[][] masu) {
        System.out.println("+---+---+---+"); //最上部の横線
        for (int i = 0; i < masu.length; i++) {
            for (int j = 0; j < masu[i].length; j++) {
                if (masu[i][j] == PLAYER_O) { //PLAYER_O=(-1)であれば
                    System.out.print("| O "); //"| 〇"を表示
                } else if (masu[i][j] == PLAYER_X) { //
                    System.out.print("| X ");//"| ×"を表示
                } else {
                    System.out.print("| " + masu[i][j] + " ");//-1でも-2でもなければ初期表示
                }
            }
            System.out.println("|"); //マスの右端を"|"でとじる表示
            System.out.println("+---+---+---+");//マスの最下部の横線を表示
        }
    }
}
