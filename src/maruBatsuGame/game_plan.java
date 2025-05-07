package maruBatsuGame;
//設計図クラス
//ゲームのルール
public class game_plan {
     char[][] board; //盤面3×3の文字配列
     char player1; //プレイヤーネーム
     char player2;
     
     //コンストラクタ（初期化）
     public game_plan(char player1,char player2) {
    	 this.player1 = player1;
    	 this.player2 = player2;
     //3x3盤面作成
     board = new char[3][3];
     for(int i = 0; i < 3; i++) {
    	 for(int j = 0; i < 3; j++)
    		 board[i][j] = ' ';
     }
     }
     //盤面を表示する
     public void printBoard() {
    	 System.out.println("---------------");
    	 int count = 1;
    	for(int i = 0; i < 3; i++) {
    		System.out.println("| ");
    		for(int j = 0; j < 3; j++) {
    			if(board[i][j] ==' ') {
    				System.out.println(count + " | ");
    			} else {
    				System.out.println(board[i][j] + " | ");
    			}
    		}
    	}
    	
     
     
     } 
}