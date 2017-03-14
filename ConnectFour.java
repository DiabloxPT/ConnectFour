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
		int nhor=0, nver=0, ndia1=0; 		//LINHA ONDE TERMINAM AS COMBINACOES HORIZONTAIS, VERTICAIS E DIAGONAIS
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
		nhor = line;

		for(int i=0; i<7; i++){
			for(int j=0; j<=2; j++){
				seg[line][0] = board[j][i];
				seg[line][1] = board[j+1][i];
				seg[line][2] = board[j+2][i];
				seg[line][3] = board[j+3][i];
				line++;
			}
		}
		nver = line;

		for(int i=0; i<=2; i++){
			for(int j=0; j<=3; j++){
				seg[line][0] = board[i][j];
				seg[line][1] = board[i+1][j+1];
				seg[line][2] = board[i+2][j+2];
				seg[line][3] = board[i+3][j+3];
				line++;
			}
		}
		ndia1 = line;

		for(int i=0; i<=2; i++){
			for(int j=6; j>=3; j--){
				seg[line][0] = board[i][j];
				seg[line][1] = board[i+1][j-1];
				seg[line][2] = board[i+2][j-2];
				seg[line][3] = board[i+3][j-3];
				line++;
			}
		}
		// EXEMPLO DE PRINT P/ TESTE
		// System.out.println("\nDIAGONAL (DIREITA ESQUERDA)");
		// for(int i=ndia1; i<line; i++){
		// 	System.out.print("[" + seg[i][0]+ seg[i][1]+ seg[i][2]+ seg[i][3]+"];");
		// 	}
		// System.out.println("\n" + line);

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
