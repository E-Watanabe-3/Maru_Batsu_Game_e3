package maruBatsuGame;

import java.util.Scanner; //外部入力

public class NewGame {

    //クラスにfinal修飾子の定数宣言し配列数を3x3指定(マジックナンバー対策)
    private static final int VERTICAL = 3; //盤面縦の行数_定数へ要素数代入
    private static final int HORIZONTAL = 3; //盤面横の列数
    //９．定数としてプレイヤーＯ(-1)と×(-2)を追加
    private static final int PLAYER_O = -1;
    private static final int PLAYER_X = -2;
    private static final int PLAYER_V = 999;

    //mainメソッド
    public static void main(String[] args) {
        //３．入力受付
        Scanner scanner = new Scanner(System.in);
        do { //do内でゲーム進行
            gameBox(scanner);
        } while (playAgain(scanner)); //trueで再戦
        scanner.close();
    }

    //１０．再戦用にゲームループを別メソッドへ
    public static void gameBox(Scanner scanner) {
        // １．ゲーム開始（タイトル）
        System.out.println("＝＝〇×ゲームへようこそ＝＝");
        System.out.println("〇と×を交互に置き、一列揃えた方の勝ちです！");
        // ２．表示（枠線と数字）3x3盤面初期化
        int[][] masu = new int[VERTICAL][HORIZONTAL]; //2次元配列[][]=定数代入

        int num = 1; //整数int型で初期値１をnum(整数)に代入
        for (int i = 0; i < masu.length; i++) { //盤面縦行_変数.length=配列の要素数(ループ回数)を取得
            for (int j = 0; j < masu[i].length; j++) { //盤面横列_同様
                masu[i][j] = num++; //num値整数+1をmasuへ代入
            }
        }
        printBoard(masu); // masu代入処理後の盤面表示

        //９．先攻1or後攻2の選択
        int selectTurn = selectOption(scanner, "先攻/後攻を選択して下さい。\n先攻：１\n後攻：２", 1, 2);
        boolean isPlayerChoice = (selectTurn == 1); //true先攻
        boolean isPlayerTurn = isPlayerChoice; //最初のターンを代入true/false
        //８．COM難易度1or2の選択
        int selectCom = selectOption(scanner, "\nCOMの難易度を選択して下さい。\n簡単：１\n難しい：２", 1, 2);
        System.out.println("\n＝ゲームを開始します＝");
        printBoard(masu);

        //４．ゲームのループ
        // boolean isPlayerTurn = true; //OvsX->2つ=boolean型true(O)/false(X)の2種で管理が容易
        while (true) {
            //三項演算子（変数A = B > C ? true : false）でif文が省略できる
            System.out.println((isPlayerTurn ? "〇" : "×") + "プレイヤーの番です。");
            int inputNum = 0;
            if (isPlayerTurn) {
                System.out.println("番号1～9を入力して下さい（0=強制終了）。");
                System.out.print("番号：");
                inputNum = scanner.nextInt(); //外部入力受付、プレイヤーが入力する数字
                scanner.nextLine();
                //６．COMの処理
            } else {
                if (selectCom == 1) { //COMの難易度を決める
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
                System.out.println("＝置けるマスがないため、ゲームを終了しました＝");
                break;
            }
            //入力+入力判定の呼び出しと、値が無効な場合の処理
            if (!tryPlace(masu, inputNum, isPlayerTurn)) { //!異なる入力値の場合
                System.out.println("＝無効な入力です、他の番号を入力してください＝");
                continue; //ループのスタート地点へ
            }
            printBoard(masu); //盤面更新
            //勝利判定の呼び出し
            if (checkWinner(masu, isPlayerTurn ? PLAYER_O : PLAYER_X)) { //true(-1):false(-2)=O:X
                System.out.println((isPlayerTurn ? "〇" : "×") + "プレイヤーの勝ちです！");
                break;
            } //引き分け判定の呼び出し
            if (checkDraw(masu)) {
                System.out.println("引き分けです。");
                break;
            }
            isPlayerTurn = !isPlayerTurn; //!=倫理否定演算子でfalseで返しターン交代
        }
    }

    //１０．再戦メソッド
    private static boolean playAgain(Scanner scanner) {
        while (true) {
            System.out.print("\n再戦しますか？\n(Y/N)：");
            String retry = scanner.nextLine();
            if ("Y".equalsIgnoreCase(retry)) {//.equalsIgnoreCase()大文字小文字区別しない(YyNn)
                System.out.println("\n＝再戦が選択されました＝\n");
                return true;
            } else if ("N".equalsIgnoreCase(retry)) {
                System.out.println("＝ゲームを終了しました＝");
                return false;
            } else {
                System.out.println("YまたはNを入力して下さい。");
            }
        }
    }

    //８+９．セレクトメニュー（先攻1と後攻2、COM難易度1+2）
    private static int selectOption(Scanner scanner, String menu, int min, int max) {
        int select = -1;
        while (true) {
            System.out.println(menu);
            System.out.print("番号：");
            if (scanner.hasNextInt()) { //int整数のみ入力可能
                select = scanner.nextInt();
                if (min <= select && select <= max) { //min~max数値ならば
                    break; //受け付けてループ修了
                }
            } else { //それ以外の値ならば
                System.out.println("\n＝その値は無効です、" + min + "～" + max + "のみです＝\n");
                scanner.nextLine();//無効な誤入力を読み捨てる（バッファクリア）
            }

        }
        return select; //入力数値を戻す
    }

    //７．強いCOMメソッド
    //７．強いCOMメソッド
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
        } //相手が勝ちそうな場所を防ぐ
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

    //６．弱いCOMメソッド
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

    //５．勝利判定メソッド

    //５．勝利判定＋メソッド
    public static boolean checkWinner(int[][] masu, int playerValue) {
        //揃った＝勝ち
        for (int i = 0; i < masu.length; i++) {
            if (masu[i][0] == playerValue && masu[i][1] == playerValue && masu[i][2] == playerValue) { //横x3
                return true;
            }
            if (masu[0][i] == playerValue && masu[1][i] == playerValue && masu[2][i] == playerValue) { //縦x3
                return true;
            }
        }
        if (masu[0][0] == playerValue && masu[1][1] == playerValue && masu[2][2] == playerValue) { //斜め＼
            return true;
        }
        if (masu[0][2] == playerValue && masu[1][1] == playerValue && masu[2][0] == playerValue) { //斜め／
            return true;
        }
        return false; //揃わなければ戻る
    }

    //５．引き分け判定メソッド
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

    //３．入力判定メソッド
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