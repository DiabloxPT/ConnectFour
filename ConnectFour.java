import java.util.*;
import java.io.*;
/*--------*/
class Node {
	char[][] board;
	char player;
	int line;
	int utility;
	int value;
	int depth;
	ArrayList<Node> sucessors;

	Node() {
		this.player = 'X';
		this.line = 0;
		this.utility = 0;
		this.value = 0;
		this.depth = 0;
		sucessors = new ArrayList<Node>();
	}

	Node(char[][] board) {
		this.board = board;
		this.player = 'X';
		this.line=0;
		this.utility = 0;
		this.value = 0;
		this.depth = 0;
		sucessors = new ArrayList<Node>();
	}

	Node(char[][] board, int utility) {
		this.board = board;
		this.player = 'X';
		this.line=0;
		this.utility = utility;
		this.depth = 0;
		sucessors = new ArrayList<Node>();
	}

	Node(char[][] board, int utility, int depth) {
		this.board = board;
		this.player = 'X';
		this.line=0;
		this.utility = utility;
		this.depth = depth;
		sucessors = new ArrayList<Node>();
	}

	int getDepth() {
		return this.depth;
	}

	char getPlayer() {
		return this.player;
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

	void play(int x, char p) {
		for(int i = 5; i >= 0; i--) {
			if(board[i][x] == '_') {
				board[i][x] = p;
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
		System.arraycopy(aux, 0, check, 0, help);
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
		char[][] seg = new char[69][4];
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

	public int util(ArrayList<char[]> segs) {
		int countX = 0;
		int countO = 0;
		int utilVal = 0;

		for(int j = 0; j < segs.size(); j++) {
			for(int i = 0; i < 4; i++) {
				if(segs.get(j)[i] == 'X') countX++;
				if(segs.get(j)[i] == 'O') countO++;
			}

			if(countX == 4) utilVal += 512;
			else if(countX == 3 && countO == 0) utilVal += 100;
			else if(countX == 2 && countO == 0) utilVal += 20;
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

	ArrayList<Node> makeMove(Node board, char p) {
		ArrayList<Node> newBoards = new ArrayList<Node>();
		int[] possMoves = board.checkPlay2();


			for (int i = 0; i < possMoves.length; i++) {
				char[][] aux = copyMatrix(board.board);
				Node newBoard = new Node(aux, util(board.makeSegs()), board.getDepth() + 1);

				newBoard.play(possMoves[i], p);
				ArrayList<char[]> segs = newBoard.makeSegs();
				newBoard.utility = newBoard.util(segs);

				newBoards.add(newBoard);
			}
			return newBoards;

	}



	Node miniMax(Node node) {
		System.out.println("MINIMAX SEARCH");
		int bestUtil = 999999;

		int v = maxM(node);
		Node best = new Node();

		for(Node suc : node.sucessors){
			if(suc.value == v && suc.utility < bestUtil) {
				bestUtil = suc.utility;
				best = suc;
				/*else if (suc.utility == bestUtil){
					best = suc;
				}*/
			}
		}

		/*if(best.board == null) {
			bestUtil = 999999;
			v = 99999;
			for(Node suc : node.sucessors) {
				if(suc.value == v) {
					if (suc.utility > 0 && suc.utility < bestUtil) {
						bestUtil = suc.utility;
						best = suc;
					}
				}
			}
		}*/

		if(best.board == null) {
			for(Node suc : node.sucessors) {
				if (suc.value == 0 && suc.utility < bestUtil) {
					bestUtil = suc.utility;
					best = suc;
				}
			}
		}

		return best;
	}

	public int maxM(Node node) {
		//System.out.println(node.getDepth());
		ArrayList<Node> aux = new ArrayList<Node>();

		if(checkWin(node.makeSegs()) || node.isFull()) {
			return util(node.makeSegs());
		}

		int v = -99999;

		if(node.getDepth() < 5) {
		aux = node.makeMove(node, 'O');
		node.sucessors = aux;
		}

		for(Node suc : node.sucessors) {
			int minV = minM(suc);
			node.value = minV;
			v = Math.max(v, minV);
		}
		return v;
	}

	public int minM(Node node) {
		//System.out.println(node.getDepth());
		ArrayList<Node> aux = new ArrayList<Node>();

		if(checkWin(node.makeSegs()) || node.isFull()) {
			return util(node.makeSegs());
		}

		int v = 99999;

		if(node.getDepth() < 5) {
			aux = node.makeMove(node, 'X');
			node.sucessors = aux;
		}

		for(Node suc : node.sucessors) {
			int maxV = maxM(suc);
			node.value = maxV;
			v = Math.min(v, maxV);
		}
		return v;
	}

	Node AlphaBeta(Node node) {
		System.out.println("ALPHA-BETA SEARCH");
		int bestUtil = 999999;

		int v = ABmax(node, -99999, 99999);
		Node best = new Node();

		for(Node suc : node.sucessors) {
			if(suc.value == v && suc.utility < bestUtil) {
				bestUtil = suc.utility;
				best = suc;
				/*else if (suc.utility == bestUtil){
					best = suc;
				}*/
			}
		}

		/*if(best.board == null) {
			bestUtil = 999999;
			v = 99999;
			for(Node suc : node.sucessors) {
				if(suc.value == v) {
					if (suc.utility > 0 && suc.utility < bestUtil) {
						bestUtil = suc.utility;
						best = suc;
					}
				}
			}
		}*/

		if(best.board == null) {
			for(Node suc : node.sucessors) {
				if (suc.value == 0 && suc.utility < bestUtil) {
					bestUtil = suc.utility;
					best = suc;
				}
			}
		}

		return best;
	}

	public int ABmax(Node node, int alpha1, int beta1) {
		int alpha = alpha1;
		//System.out.println(node.getDepth());
		ArrayList<Node> aux = new ArrayList<Node>();

		if(checkWin(node.makeSegs()) || node.isFull()) {
			return util(node.makeSegs());
		}

		int v = -99999;

		if(node.getDepth() < 5) {
			aux = node.makeMove(node, 'O');
			node.sucessors = aux;
		}

		for(Node suc : node.sucessors) {
			int minV = ABmin(suc, alpha, beta1);
			node.value = minV;
			v = Math.max(v, minV);
			if(v > beta1) return v;
			alpha = Math.max(v, alpha);
		}
		return v;
	}

	public int ABmin(Node node, int alpha1, int beta1) {
		int beta = beta1;
		//System.out.println(node.getDepth());
		ArrayList<Node> aux = new ArrayList<Node>();

		if(checkWin(node.makeSegs()) || node.isFull()) {
			return util(node.makeSegs());
		}

		int v = 99999;

		if(node.getDepth() < 5) {
		aux = node.makeMove(node, 'X');
		node.sucessors = aux;
		}

		for(Node suc : node.sucessors) {
			int maxV = ABmax(suc, alpha1, beta);
			node.value = maxV;
			v = Math.min(v, maxV);
			if(v < alpha1) return v;
			beta = Math.min(v, beta);
		}
		return v;
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
		/*ArrayList<Node> test = board.makeMove(board, board.player);

		for(int i = 0; i < test.size(); i++) {
		    test.get(i).printBoard();
		    System.out.println(test.get(i).utility);
		}*/

		//board.printBoard();
		System.out.print("Escolha quem joga 1º(C para computador, P para utilizador):");
		String turn = in.next();
		if(turn.equals("P")) {
			board.player = 'X';
		}
		else if(turn.equals("C")) board.player = 'O';

		System.out.print("Escolha o algoritmo(M para minimax, A para alphabeta):");
		String algor = in.next();

		while(true) {
			board.printBoard();
			if(board.checkWin(board.makeSegs())) {
				board.nextPlayer();
				System.out.println("Winner: " + board.getPlayer());
				System.exit(0);
			}

			if(board.isFull()) {
				System.out.println("Draw");
			}

			if(board.player == 'X') {
				System.out.print("Player " + board.player + " move: ");
				int n = in.nextInt();
				while(n>6 || n<0 || !board.checkPlay(n)) {
					System.out.println("Escolha um numero entre 0 e 6");
					n = in.nextInt();
					board.printBoard();
				}
					board.play(n, board.player);
				/*board.printBoard();
				ArrayList<char[]> segs = board.makeSegs();
				int score = board.util(segs);

				if(board.checkWin(segs)) {
					System.out.println("Winner is: " + board.player);
					return;
				}*/
				board = new Node(board.board, board.util(board.makeSegs()));
				board.nextPlayer();
			}
			else if(board.player == 'O'){
				long startTime = System.currentTimeMillis();
				if(algor.equals("M")) {
					board = board.miniMax(board);
				}
				else if(algor.equals("A")) {
					board = board.AlphaBeta(board);
				}
				long endTime = System.currentTimeMillis();
				System.out.println("Tempo de execução: "+((endTime-startTime)/1000)+"s ("+(endTime-startTime)+"ms)");
				board.player = 'X';
			}
		}
	}
}
