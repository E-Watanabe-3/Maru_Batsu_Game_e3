package maruBatsuGame;

import java.util.Random;//CPUがランダムな動きをする
import java.util.Scanner;//キーボードの入力を受け付ける

public class GameClass {
   
    static char[][] board = { // 盤面3×3 の文字配列
        {' ', ' ', ' '},//空白で枠を用意
        {' ', ' ', ' '},
        {' ', ' ', ' '}
    };
    
    static char playerMark = 'X';//プレイヤー
    static char cpuMark = 'O';//コンピューター

    public static void main(String[] args) {
    //Scanner=キーボードの入力を受け付ける
    Scanner scanner = new Scanner(System.in);//System.inでコンソールから受け取る
    
    //スタート画面の表示
    
    
    
    
    
    
    
    //現在の盤面の表示
    public static void printBoard() {
        System.out.println("-------------");
        int count = 1; // 空白のマスに表示する1〜9の番号をカウンタ
        
        for (int i = 0; i < 3; i++) {//盤面の行を処理（上、中、下）（０，１，２）
        }
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {//各行にある列（左、中、右）を処理
                if (board[i][j] == ' ') {//そのマスが空いているかどうか
                    System.out.print(count + " | ");//空いているマスに番号を表示
                } else {
                    System.out.print(board[i][j] + " | ");//埋まっているマスにマークを表示
                }
                count++;//次のマスの番号に進むため、番号を一つ増やす
            }
            System.out.println();
            System.out.println("-------------");//盤面の区切り線
        
    }
}