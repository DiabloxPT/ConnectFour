import java.util.*;
import java.io.*;
/*--------*/
class Node {
	char[][] board;
	char player;
	int line;
	int utility;

	Node(char[][] board) {
		this.board = board;
		player = 'X';
		line=0;
		utility = 0;
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

	boolean checkPlay(int x) {
		if(board[0][x] != '_') {
			System.out.println("Jogada invalida!");
			return false;
		}
		return true;
	}
	
	int[] checkPlay2() {
			int[] aux = new int[7];
			int help=0;
			for(int x=0; x<7; x++)
				if(board[0][x] == '_'){
				 	aux[help] = x;
					help++;
				}
			int[] check = new int[help];
			for(int x=0; x<help; x++)
				 	check[x] = aux[x];
			return check;
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

	ArrayList<char[]> makeSegs() {
		line =0;
		int nhor=0, nver=0, ndia1=0; 		//LINHA ONDE TERMINAM AS COMBINACOES HORIZONTAIS, VERTICAIS E DIAGONAIS
		char[][] seg = new char[100][4];
		ArrayList<char[]> segs = new ArrayList<char[]>();
		
			//HORIZONTAL
		for(int i=0; i<6; i++){
			for(int j=0; j<=3; j++){
				seg[line][0] = board[i][j];
				seg[line][1] = board[i][j+1];
				seg[line][2] = board[i][j+2];
				seg[line][3] = board[i][j+3];
				segs.add(seg[line]);
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
				segs.add(seg[line]);
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
				segs.add(seg[line]);
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
				segs.add(seg[line]);
				line++;
			}
		}
		
		/*for(int i = 0; i < segs.size(); i++) {
		    System.out.println(segs.get(i));
		}*/
		
		return segs;
		// EXEMPLO DE PRINT P/ TESTE
		// System.out.println("\nDIAGONAL (DIREITA ESQUERDA)");
		// for(int i=ndia1; i<line; i++){
		// 	System.out.print("[" + seg[i][0]+ seg[i][1]+ seg[i][2]+ seg[i][3]+"];");
		// 	}
		// System.out.println("\n" + line);

	}

	int util(ArrayList<char[]> segs) {
		int countX = 0;
		int countO = 0;
		int utilVal = 0;
		
		for(int j = 0; j < segs.size(); j++) {
			for(int i = 0; i < 4; i++) {
				if(segs.get(j)[i] == 'X') countX++;
				if(segs.get(j)[i] == 'O') countO++;
			}
			
			if(countX == 4) utilVal += 512;
			else if(countX == 3 && countO == 0) utilVal += 50;
			else if(countX == 2 && countO == 0) utilVal += 10;
			else if(countX == 1 && countO == 0) utilVal += 1;		
			else if(countO == 4) utilVal -= 512;
			else if(countO == 3 && countX == 0) utilVal -= 50;
			else if(countO == 2 && countX == 0) utilVal -= 10;
			else if(countO == 1 && countX == 0) utilVal -= 1;
		
			countX = 0;
			countO = 0;
		}
		
		
		
		if(player == 'X') return -16 + utilVal;
		else if(player == 'O') return 16 + utilVal;
		
		return 0;
	}
	boolean checkWin(ArrayList<char[]> segs) {
		int countX = 0;
		int countO = 0;
		int utilVal = 0;
		
		for(int j = 0; j < segs.size(); j++) {
			for(int i = 0; i < 4; i++) {
				if(segs.get(j)[i] == 'X') countX++;
				if(segs.get(j)[i] == 'O') countO++;
			}
			
			if(countX == 4 || countO == 4) return true;
			countX = 0;
			countO = 0;
		}
		
		return false;
	}
	
	public static char[][] copyMatrix(char[][] current_table){
    char[][] aux_table = new char[6][7];
    for(int i=0;i<6;i++){
        System.arraycopy(current_table[i],0, aux_table[i], 0, current_table[0].length);
    }
    return aux_table;
  }
	
	ArrayList<Node> makeMove(Node board) {
		ArrayList<Node> newBoards = new ArrayList<Node>();
		int[] possMoves = board.checkPlay2();
		
		for(int i = 0; i < possMoves.length; i++) {
		    char[][] aux = copyMatrix(board.board);
			Node newBoard = new Node(aux);
			newBoard.play(possMoves[i]);
			ArrayList<char[]> segs = newBoard.makeSegs();
			newBoard.utility = newBoard.util(segs);
			
			newBoards.add(newBoard);
		}
		return newBoards;
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
		ArrayList<Node> test = board.makeMove(board);
		
		for(int i = 0; i < test.size(); i++) {
		    test.get(i).printBoard();
		    System.out.println(test.get(i).utility);
		}

		/*board.printBoard();
		while(!board.isFull()) {
			System.out.print("Player " + board.player + " move: ");
			int n = in.nextInt();
			if(n>6 || n<0 || !board.checkPlay(n))System.out.println("Escolha um numero entre 0 e 6");
			else{
				board.play(n);
				board.printBoard();
				ArrayList<char[]> segs = board.makeSegs();
				int score = board.util(segs);
				
				if(board.checkWin(segs)) {
					System.out.println("Winner is: " + board.player);
					return;
				}
				
				board.nextPlayer();
			}
		}*/
	}
}