import java.util.*;
import java.io.*;
/*--------*/
class Node {
	char[][] board;
	char player;

	int line;

	Node(char[][] board) {
		this.board = board;
		player = 'X';
		line=0;
	}

	int findPos(int x) {
		for(int i = 0; i < 6; i++) {
			if(board[i][x] != '_') return i--;
		}

		return -1;
	}

	void printBoard() {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("\n");
		}
	}

	void play(int x) {
		for(int i = 5; i >= 0; i--) {
			if(board[i][x] == '_') {
				board[i][x] = player;
				return;
			}
		}
	}

	void checkPlay(int x) {
		if(board[0][x] != '_') {
			System.out.println("Jogada invalida!");
			nextPlayer();
		}
	}

	void nextPlayer() {
		if(player == 'X') player = 'O';
		else if(player == 'O') player = 'X';
	}

	boolean isFull(){
		for(int i=0; i<6; i++){
			for(int j=0; j<7; j++){
				if (board[i][j] == '_') {
					return false;
				}
			}
		}
		return true;
	}

	void makeSegs() {
		line =0;
		char[][] seg = new char[100][4];
			//HORIZONTAL
		for(int i=0; i<6; i++){
			for(int j=0; j<=3; j++){
				seg[line][0] = board[i][j];
				seg[line][1] = board[i][j+1];
				seg[line][2] = board[i][j+2];
				seg[line][3] = board[i][j+3];
				line++;
			}
		}

		//for(int i=0; i<)

		//TESTAR O PRINT
		/*for(int i=0; i< line; i++){
			System.out.print("[" + seg[i][0]+ seg[i][1]+ seg[i][2]+ seg[i][3]+"];");
		}
		System.out.println();
		*/
	}


}

class ConnectFour {
	public static void main(String[] arg) {
		Scanner in = new Scanner(System.in);
		char[][] game = new char[6][7];

		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				game[i][j] = '_';
			}
		}

		Node board = new Node(game);

		board.printBoard();
		while(!board.isFull()) {
			System.out.print("Player " + board.player + " move: ");
			int n = in.nextInt();

			board.checkPlay(n);
			board.play(n);
			board.printBoard();
			board.makeSegs();
			board.nextPlayer();
		}
	}
}
