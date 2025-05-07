package demo_seka;

//設計図クラス

	import java.util.Random;

	// ゲームのルールや処理をまとめたクラス
	public class demo_plan {
	    private char[][] board;     // 盤面（3×3のマス）
	    private char playerMark;    // プレイヤーのマーク（'X'など）
	    private char cpuMark;       // CPUのマーク（'O'など）

	    // コンストラクタ（初期化）
	    public demo_plan(char playerMark, char cpuMark) {
	        this.playerMark = playerMark;
	        this.cpuMark = cpuMark;

	        // 空白で初期化された3×3の盤面を作成
	        board = new char[3][3];
	        for (int i = 0; i < 3; i++)
	            for (int j = 0; j < 3; j++)
	                board[i][j] = ' ';
	    }

	    // 盤面を表示する
	    public void printBoard() {
	        System.out.println("-------------");
	        int count = 1;  // マス番号（1〜9）
	        for (int i = 0; i < 3; i++) {
	            System.out.print("| ");
	            for (int j = 0; j < 3; j++) {
	                if (board[i][j] == ' ') {
	                    // 空きマス → 番号で表示
	                    System.out.print(count + " | ");
	                } else {
	                    // 埋まっているマス → マークを表示
	                    System.out.print(board[i][j] + " | ");
	                }
	                count++;
	            }
	            System.out.println();
	            System.out.println("-------------");
	        }
	    }

	    // 指定のマスにマークを置く（成功すればtrue）
	    public boolean placeMark(int cell, char mark) {
	        if (cell < 1 || cell > 9) return false;

	        int row = (cell - 1) / 3;
	        int col = (cell - 1) % 3;

	        if (board[row][col] == ' ') {
	            board[row][col] = mark;
	            return true;
	        }
	        return false;
	    }

	    // CPUの簡単な手（ランダムに置く）
	    public void cpuTurnEasy() {
	        Random rand = new Random();
	        int row, col;
	        while (true) {
	            row = rand.nextInt(3);
	            col = rand.nextInt(3);
	            if (board[row][col] == ' ') {
	                board[row][col] = cpuMark;
	                break;
	            }
	        }
	    }

	    // CPUの強い手（勝ちやブロックを狙う）
	    public void cpuTurnHard() {
	        for (int i = 0; i < 3; i++) {
	            for (int j = 0; j < 3; j++) {
	                if (board[i][j] == ' ') {
	                    // 自分が勝てるならそこに置く
	                    board[i][j] = cpuMark;
	                    if (checkWin(cpuMark)) return;

	                    // 相手が勝ちそうならブロック
	                    board[i][j] = playerMark;
	                    if (checkWin(playerMark)) {
	                        board[i][j] = cpuMark;
	                        return;
	                    }

	                    // どちらもなければ空白に戻す
	                    board[i][j] = ' ';
	                }
	            }
	        }
	        // 勝ちもブロックもできない場合はランダムに置く
	        cpuTurnEasy();
	    }

	    // 指定のマークが勝っているかチェック
	    public boolean checkWin(char mark) {
	        for (int i = 0; i < 3; i++) {
	            // 横一列 または 縦一列が同じマーク
	            if ((board[i][0] == mark && board[i][1] == mark && board[i][2] == mark) ||
	                (board[0][i] == mark && board[1][i] == mark && board[2][i] == mark))
	                return true;
	        }

	        // 斜め2方向が同じマーク
	        if ((board[0][0] == mark && board[1][1] == mark && board[2][2] == mark) ||
	            (board[0][2] == mark && board[1][1] == mark && board[2][0] == mark))
	            return true;

	        return false;
	    }

	    // 盤面がすべて埋まっているかチェック（引き分け用）
	    public boolean isBoardFull() {
	        for (int i = 0; i < 3; i++)
	            for (int j = 0; j < 3; j++)
	                if (board[i][j] == ' ') return false;
	        return true;
	    }
	}
