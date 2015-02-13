public class Board {
	/** Board class will handle the Board operations of proj0.
	 *  @author David Dominguez Hooper
	 *  [Do not modify this file.]
	 */

    private Piece gamePieces[][];
    private int currentPlayer;
    private Piece currentPiece;
    private boolean currentPieceSelected;
    private int selectedPieceX;
    private int selectedPieceY; 
    private boolean turnFinished;

	public Board(boolean shouldBeEmpty) {
		this.turnFinished = false;
		this.gamePieces = new Piece[8][8];
		this.currentPlayer = 0;
		if(!shouldBeEmpty)
			this.createPieces();
	}


    private void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) 
                	StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                else                  
                	StdDrawPlus.setPenColor(StdDrawPlus.RED);
                //draw new stuff right here
                if (currentPiece != null && i == selectedPieceX && j == selectedPieceY)
                	StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                
                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
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
        StdDrawPlus.setXscale(0, 8);
        StdDrawPlus.setYscale(0, 8);
        /** Monitors for mouse presses. Wherever the mouse is pressed,
            a new piece appears. */
        while(true) {

	        drawBoard();
            if (StdDrawPlus.mousePressed()) {

                int x = (int)StdDrawPlus.mouseX();
                int y = (int)StdDrawPlus.mouseY();

                if (this.canSelect(x, y))
                	this.select(x, y);
            }  
            if (StdDrawPlus.isSpacePressed()) {
	            if (this.canEndTurn() && this.turnFinished)
	            	this.endTurn();
			}
	        StdDrawPlus.show(25);
	    }
    }

    private void createPieces() {
    	for(int i = 0; i < 8; i += 2) {
    		// (0, 0) is the bottom left and (7, 7) is top right. [x][y], not [row][column]:
    		
    		gamePieces[i][0] = new Piece(true, this, i, 0, "pawn"); //fire pawns
    		gamePieces[i+1][7] = new Piece(false, this, i+1, 7, "pawn"); //water pawns

    		gamePieces[i+1][1] = new Piece(true, this, i+1, 1, "shield"); //fire shields
    		gamePieces[i][6] = new Piece(false, this, i, 6, "shield"); //water shields

    		gamePieces[i][2] = new Piece(true, this, i, 2, "bomb"); //fire bombs
    		gamePieces[i+1][5] = new Piece(false, this, i+1, 5, "bomb"); //water bombs

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
		if (this.outOfBounds(x, y))
			return false;
		else if (this.gamePieces[x][y] != null) {
			if ((this.gamePieces[x][y].side() == this.currentPlayer) && ( !this.currentPieceSelected || (this.currentPieceSelected && !this.turnFinished)))
				return true;
			else
				return false;
		}
		else {
			if (this.currentPieceSelected && !this.turnFinished && this.validMove(this.selectedPieceX, this.selectedPieceY, x, y))
				return true;
			else if (this.currentPieceSelected && this.gamePieces[selectedPieceX][selectedPieceY].hasCaptured() && this.validMove(this.selectedPieceX, this.selectedPieceY, x, y)) 
				return true;
			else
				return false;
		}
	}

	private boolean correctDirectionMove(int verticalMove) {
		if (this.currentPiece.isFire() && verticalMove > 0)
			return true;
		else if (!this.currentPiece.isFire() && verticalMove < 0)
			return true;
		else
			return false;
	}


	private boolean validMove(int xi, int yi, int xf, int yf) {
		int horizontalMove = xf - xi;
		int verticalMove = yf - yi;
<<<<<<< HEAD
		int inBetweenPieceX = 0;
		int inBetweenPieceY = 0;

		// determine if there is a piece to capture
		if (this.currentPiece.isFire()) 
			inBetweenPieceY = yi + 1;

		else
			inBetweenPieceY = yi - 1;


		if (horizontalMove > 0)
			inBetweenPieceX = xi + 1;

		else
			inBetweenPieceX = xi - 1;
=======


>>>>>>> 7f252028167060a4e50c4388baeef933e7fd6b4a

		if (Math.abs(verticalMove) == 1 && Math.abs(horizontalMove) == 1) {
			if (this.currentPiece.isKing())
				return true;
			else
				return correctDirectionMove(verticalMove);
		}
<<<<<<< HEAD
		else if (this.pieceAt(inBetweenPieceX, inBetweenPieceY) != null && Math.abs(verticalMove) == 2 && Math.abs(horizontalMove) == 2) {
=======
		else if ( Math.abs(verticalMove) == 2 && Math.abs(horizontalMove) == 2) {
>>>>>>> 7f252028167060a4e50c4388baeef933e7fd6b4a
			if (this.currentPiece.isKing())
				return true;
			else
				return correctDirectionMove(verticalMove);
		}

		else 
			return false;
	}

	public void select(int x, int y) {
		if (this.pieceAt(x, y) == null) {
			this.currentPiece.move(x, y);
			this.turnFinished = true;
		}
		this.currentPiece = this.pieceAt(x, y);
		this.selectedPieceX = x;
		this.selectedPieceY = y;
		this.currentPieceSelected = true;
		System.out.println("new piece location x(horiztonal): " + this.selectedPieceX + ", y(vertical): " + this.selectedPieceY + "\n");
	}

	public void place(Piece p, int x, int y) {
		if (!this.outOfBounds(x, y) && p != null) 
				this.gamePieces[x][y] = p;
	}

	public Piece remove(int x, int y) {
		if (this.outOfBounds(x, y)) {
			System.out.println("Tried to remove a piece that was out of bounds at (" + x + ", " + y + ").");
			return null;
		}
		else if (this.gamePieces[x][y] == null) {
			System.out.println("Tried to remove a piece that was not there at (" + x + ", " + y + ").");
			return null;
		}
		else {
			Piece pointer = this.gamePieces[x][y];
			this.gamePieces[x][y] = null;
			return pointer;
		}
	}


	public boolean canEndTurn() {
		if (this.currentPiece == null)
			return false;
		else if (this.turnFinished || this.currentPiece.hasCaptured())
			return true;
		else
			return false;
	}

	public void endTurn() {
		if (this.currentPlayer == 0)
			this.currentPlayer = 1;
		else
			this.currentPlayer = 0;

		this.currentPieceSelected = false;
		this.turnFinished = false;

		if (this.currentPiece != null) {
			this.currentPiece.doneCapturing();
			this.currentPiece = null;
		}
	}


	public String winner() {
		int player0PiecesLeft = this.countPlayerPiecesLeft(0);
		int player1PiecesLeft = this.countPlayerPiecesLeft(1);

		if (player1PiecesLeft == 0 && player0PiecesLeft == 0)
			return "No one";
		else if (player1PiecesLeft == 0)
			return "Water";
		else if (player0PiecesLeft == 1)
			return "Fire";
		else
			return null;
	}

	private int countPlayerPiecesLeft(int currentPlayer) {
		int total = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	if (this.gamePieces[i][j].side() == this.currentPlayer)
            		total += 1;
            }
        }
        return total;
	}


	public static void main(String[] args) {
		Board gameBoard = new Board(false);
		gameBoard.startBoardGame();
	}
}