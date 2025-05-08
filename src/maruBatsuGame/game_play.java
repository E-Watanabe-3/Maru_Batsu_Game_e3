package maruBatsuGame;
//実行クラス
//環境作成
import java.util.Scanner;

public class game_play {
	public static void main(String[] args) {
		System.out.println("〇×ゲームへようこそ");
		char[] board = {'1','2','3','4','5','6','7','8','9'};
		char Player1 = '〇';
		
		Scanner scanner = new Scanner(System.in);
		while(true) {
			printBoard(board);
			System.out.println("プレイヤーの" + Player1 + "番です。１～９の番号を入力して下さい：");
			int move = scanner.nextInt();
			if(move < 1 || move > 9 || board[move - 1] == '〇' || board[move - 1] == '×') {
				System.out.println("その番号は無効です。もう一度入力してください：");
				continue;
			}
		}

		
		
		
		
		System.out.println("ゲームを終了します");
	}

	private static void printBoard(char[] board) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}


