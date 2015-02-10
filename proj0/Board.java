public class Board {
	/** Board class will handle the Board operations of proj0.
	 *  @author David Dominguez Hooper
	 *  [Do not modify this file.]
	 */
    private static boolean[][] pieces;

    private boolean emptyBoard;
    private Piece gamePieces[][];

	public Board(boolean shouldBeEmpty) {
		this.emptyBoard = shouldBeEmpty;
		this.gamePieces = new Piece[8][8];
		if(!this.emptyBoard)
			this.createPieces();
	}

    private void drawBoard(int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i + j) % 2 == 0) 
                	StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                else                  
                	StdDrawPlus.setPenColor(StdDrawPlus.RED);

                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
                StdDrawPlus.setPenColor(StdDrawPlus.WHITE);

                Piece piecePointer = this.gamePieces[i][j];
                if (piecePointer != null) {
                    StdDrawPlus.picture(i + .5, j + .5, this.getIcon(piecePointer), 1, 1);
                }
            }
        }
    }
    private String getIcon(Piece a){
    	String iconLocation = "img/";

    	if (a.isBomb())
    		iconLocation += "bomb";
    	else if (a.isShield()) 
    		iconLocation += "shield";
    	else
    		iconLocation += "pawn";

    	if (a.isFire())
    		iconLocation += "-fire";
    	else
    		iconLocation += "-water";

    	if (a.isKing())
    		iconLocation += "-crowned";
    	iconLocation += ".png";
    	return iconLocation;
    }

    private void startBoardGame() {
        int N = 8;
        StdDrawPlus.setXscale(0, N);
        StdDrawPlus.setYscale(0, N);
        // pieces = new boolean[N][N];
        /** Monitors for mouse presses. Wherever the mouse is pressed,
            a new piece appears. */
        drawBoard(N);     
        StdDrawPlus.show(100);
    }

    private void createPieces() {
    	for(int i = 0; i < 8; i += 2) {
    		// (0, 0) is the bottom left and (7, 7) is top right. [x][y], not [row][column]:
    		
    		gamePieces[i][0] = new Piece(false, this, i, 0, "pawn"); //fire pawns
    		gamePieces[i+1][7] = new Piece(true, this, i+1, 7, "pawn"); //water pawns

    		gamePieces[i+1][1] = new Piece(false, this, i+1, 1, "shield"); //fire shields
    		gamePieces[i][6] = new Piece(true, this, i, 6, "shield"); //water shields

    		gamePieces[i][2] = new Piece(false, this, i, 2, "bomb"); //fire bombs
    		gamePieces[i+1][5] = new Piece(true, this, i+1, 5, "bomb"); //water bombs

    	}
    }

	private boolean outOfBounds(int x, int y) {
		if (x > 7 || y > 7 || x < 0 || y < 0)
			return true;
		else
			return false;
	}

	public Piece pieceAt(int x, int y) {
		if (this.outOfBounds(x, y) || this.gamePieces[x][y] == null)
			return null;
		else
			return this.gamePieces[x][y];
	}

	public boolean canSelect(int x, int y) {
		return false; //might want to use validMove...... >>>>>
	}

	// private boolean validMove(int xi, int yi, int xf, int yf) {
	// 	return false;
	// }
	// Might want to use it  for canSelect

	public void select(int x, int y) {

	}

	public void place(Piece p, int x, int y) {
		if (this.outOfBounds(x, y) || p == null)
			;
		else if (this.gamePieces[x][y] != null) {
			Piece piecePointer = p;
		}
		else
			;

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