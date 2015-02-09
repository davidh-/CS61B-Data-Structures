public class Board {
	/** Board class will handle the Board operations of proj0.
	 *  @author David Dominguez Hooper
	 *  [Do not modify this file.]
	 */
    private static boolean[][] pieces;

    private boolean emptyBoard;
    private Piece gamePieces[][];


    private static void drawBoard(int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i + j) % 2 == 0) StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                else                  StdDrawPlus.setPenColor(StdDrawPlus.RED);
                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
                StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                if (pieces[i][j]) {
                    StdDrawPlus.picture(i + .5, j + .5, "img/bomb-fire-crowned.png", 1, 1);
                }
            }
        }
    }

    private void startBoardGame() {
        int N = 8;
        StdDrawPlus.setXscale(0, N);
        StdDrawPlus.setYscale(0, N);
        pieces = new boolean[N][N];

        /** Monitors for mouse presses. Wherever the mouse is pressed,
            a new piece appears. */
        while(true) {
            drawBoard(N);
            if (StdDrawPlus.mousePressed()) {
                double x = StdDrawPlus.mouseX();
                double y = StdDrawPlus.mouseY();
                pieces[(int) x][(int) y] = true;
            }            
            StdDrawPlus.show(100);
        }
    }



	public Board(boolean shouldBeEmpty) {
		this.emptyBoard = shouldBeEmpty;
		this.gamePieces = new Piece[8][8];
		// if(shouldBeEmpty == false)



	}

	public Piece pieceAt(int x, int y) {
		return null;
	}

	public boolean canSelect(int x, int y) {
		return false;
	}

	public boolean validMove(int xi, int yi, int xf, int yf) {
		return false;
	}

	public void select(int x, int y) {

	}

	public void place(Piece p, int x, int y) {

	}

	public Piece remove(int x, int y) {
		return null;
	}

	public boolean canEndTurn() {
		return false;
	}

	public void endturn() {

	}

	public String winner() {
		return null;
	}


	public static void main(String[] args) {
		Board gameBoard = new Board(false);
		gameBoard.startBoardGame();
	}
}