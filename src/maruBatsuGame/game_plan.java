package maruBatsuGame;
//設計図クラス
public class game_plan {
//ゲームのルール
//要件
//1.盤面は3 × 3

//2.先に一列揃えたプレイヤーの勝利
//3.プレイヤー vs CPU（どっちが○か×かは問わない）
//4.CPUの強さを2段階で選択可能
//実装手順(目安)
//1.環境作成

//2.ゲーム開始画面と終了処理を作る
//3.３×３の画面表示を作る
//4.1Pと2Pで交互にマスを埋めるようにする
//5.勝利判定を実装する
//6.2P側をCPUにして、適当に置くようにする
//7CPUを強くする


	//①3x3盤面を表示
	public class GameBoard {
		public static void main(String[] args) {
			char[][] board = new char[3][3];

			// 初期化（すべてのマスを空白に）
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					board[i][j] = ' ';
				}
			}

			// 盤面を表示するメソッド
			printBoard(board);
		}

		public static void printBoard(char[][] board) {
			System.out.println("-------------");
			for (int i = 0; i < 3; i++) {
				System.out.print("| ");
				for (int j = 0; j < 3; j++) {
					System.out.print(board[i][j] + " | ");
				}
				System.out.println();
				System.out.println("-------------");
			}
		}
	}
}