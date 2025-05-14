package maruBatsuGame;

//ゲームのルール（トグル
//要件
//1.盤面は3 × 3ok

//2.先に一列揃えたプレイヤーの勝利
//3.プレイヤー vs CPU（どっちが○か×かは問わない）
//4.CPUの強さを2段階で選択可能

//実装手順(目安)
//1.環境作成
//2.ゲーム開始画面と終了処理を作るok
//3.３×３の画面表示を作るok

//4.1Pと2Pで交互にマスを埋めるようにする
//5.勝利判定を実装する
//6.2P側をCPUにして、適当に置くようにする
//7CPUを強くする

import java.util.Scanner;//入力を受付

public class game_plan {
	private static char[][] array = new char[3][3];//3x3盤面をchar型配列で宣言。2次元配列=[][]である

	
	private static char player = '○'; //Playerに"〇"を代入

	
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in); //キーボード入力を受け付けるように
		initializeBoard();

		//１．ゲーム開始画面を表示
		System.out.println("〇×ゲームにようこそ！");
		System.out.println("プレイヤーの番、番号を入力してください：");

		//４．交互に入力
		while(true) {  //入力継続ループ
			
			System.out.println("プレイヤー " + (player == '○' ? "1P（○）" : "2P（×）") + " の番。（番号1～9）：");
			int move; //
			while(true) { //プレイヤーのターン
				try {
					move = Integer.parseInt(scanner.nextLine()) - 1; //文字列を整数に変換。キーボード入力を取得、0~8
					int row =move / 3;
					int col = move % 3;
					if (move < 0 || move >= 9 || array[row][col] == '○' || array[row][col] == '×') {//1~9以外は再入力
						System.out.println("無効な入力です。もう一度選んでください：");
					} else {
						array[row][col] = player; //現在の〇×
						break;
					}
				} catch(NumberFormatException e) {
					System.out.println("数字を入力してください：");
				}
			}
			printBoard(array);//盤面呼び出し
			
			
			
			
			player = (player == '○') ? '×' : '○'; //〇×交代

			scanner.close();
			//２．ゲーム終了画面を表示
			System.out.println("〇×ゲームを終了しました。");
		}
	}

	// 盤面初期化（マスを空白に）
	private static void initializeBoard() {
		int count = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				array[i][j] = (char)('0' + count); //1~9の番号を表示
				count++;
			}
		}
		//３．盤面を画面表示する呼び出し
		printBoard(array);

	}

	//3x3盤面を配列で表示
	public static void printBoard(char[][] array) {
		System.out.println("-------------");
		for (int i = 0; i < 3; i++) {
			System.out.print("| ");
			for (int j = 0; j < 3; j++) {
				System.out.print(array[i][j] + " | ");
			}
			System.out.println();
			System.out.println("-------------");	}
		}
	}

