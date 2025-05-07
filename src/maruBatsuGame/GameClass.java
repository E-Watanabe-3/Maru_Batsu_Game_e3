package maruBatsuGame;

public class GameClass {
	
	// 3x3盤面を定義
	    static char[][] board = new char[3][3];

	    public static void main(String[] args) {
	        // ゲームタイトル表示
	        System.out.println("〇×ゲームへようこそ！");

	        // 盤面初期化
	        initializeBoard();

	        // 盤面表示
	        printBoard();
	    }

	    // 盤面を初期化
	    public static void initializeBoard() {
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                board[i][j] = ' '; // 空白で初期化
	            }
	        }
	    }

	    // 盤面を表示するメソッド
	    public static void printBoard() {
	        System.out.println(" ------------- "); // 上部の横線表示
	        for(int i = 0; i < 3; i++) { // 行のループ

	            System.out.print(" | "); // 行の先頭に縦線を表示
	            for(int j = 0; j < 3; j++) { // 列のループ
	                System.out.print(board[i][j] + " | "); // 各セルを区切る
	            }
	            System.out.println(); // 行の最後で改行
	            System.out.println(" ------------- "); // 横線を追加
	        }
	    }
	}