import java.util.*;
import java.io.*;
/*--------*/
class Node {
	char[][] board;
	char player;

	Node(char[][] board) {
		this.board = board;
		player = 'X';
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
		char[] seg = new char[4];
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
			board.nextPlayer();
		}
	}
}
