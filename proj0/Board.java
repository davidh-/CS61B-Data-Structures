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

    private boolean pieceMoved;
    private boolean turnFinished;
    private boolean bombExploded;


	public Board(boolean shouldBeEmpty) {
		this.pieceMoved = false;
		this.bombExploded = false;
		this.gamePieces = new Piece[8][8];
		this.currentPlayer = 0;
		if(!shouldBeEmpty) {
			this.createPieces();
		}
	}


    private void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                	StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                }
                else {                  
                	StdDrawPlus.setPenColor(StdDrawPlus.RED);
                }
                //draw new stuff right here
                if (this.currentPiece != null && i == selectedPieceX && j == selectedPieceY){
                	StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                }
                
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

    	if (a.isBomb()){
    		iconLocation += "bomb";
    	}
    	else if (a.isShield()) {
    		iconLocation += "shield";
    	}
    	else {
    		iconLocation += "pawn";
    	}

    	if (a.isFire()) {
    		iconLocation += "-fire";
    	}
    	else {
    		iconLocation += "-water";
    	}

    	if (a.isKing()){
    		iconLocation += "-crowned";
    	}
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

                if (this.canSelect(x, y)) {
					System.out.println("about to run select");
                	this.select(x, y);
                }
            }  
            if (StdDrawPlus.isSpacePressed() && this.pieceMoved) {
	            if (this.bombExploded) {
	            	this.endTurn();
	            	this.remove(selectedPieceX, selectedPieceY);
	            }
	            else if (this.canEndTurn() && this.pieceMoved) {
	            	this.endTurn();
	            }
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
		if (x > 7 || y > 7 || x < 0 || y < 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public Piece pieceAt(int x, int y) {
		if (this.outOfBounds(x, y) || this.gamePieces[x][y] == null) {
			return null;
		}
		else {
			return this.gamePieces[x][y];
		}
	}


	public boolean canSelect(int x, int y) {
		System.out.println("hit TOP OF CANSelect");
		if (this.outOfBounds(x, y)) {
			System.out.println("out of bounds");
			return false;
		}
		else if (this.gamePieces[x][y] != null) {
			System.out.println("hit CANS");
			return (this.gamePieces[x][y].side() == this.currentPlayer) && ((this.currentPieceSelected == false) || (this.currentPieceSelected && this.pieceMoved == false));
		}
		else {
			if (this.currentPieceSelected && this.pieceMoved == false  && this.validMove(this.selectedPieceX, this.selectedPieceY, x, y)) {
				System.out.println("hit CAN1S");
				return true;
			}
			else {
				System.out.println("hit CAN2S " + this.pieceMoved);
				return this.currentPieceSelected && this.currentPiece.hasCaptured() && this.validMove(this.selectedPieceX, this.selectedPieceY, x, y);
			}
		}
	}

	private boolean correctDirectionMove(int verticalMove) {
		if (this.currentPiece.isFire() && verticalMove > 0) {
			return true;
		}
		else if (!this.currentPiece.isFire() && verticalMove < 0) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean validMove(int xi, int yi, int xf, int yf) {
		System.out.println("xi and yi: " + (xi) + " " + (yi) + "xf and yf: " + (xf) + " " + (yf));
		if (this.pieceAt(xf, yf) != null) {
			System.out.println("hitttt begin");
			return false;
		}
		int horizontalMove = xf - xi;
		int verticalMove = yf - yi;
		int inBetweenPieceX = xi;
		int inBetweenPieceY = yi;

		System.out.println("fuck this");

		if (this.pieceMoved == false && (Math.abs(verticalMove) == 1 || Math.abs(horizontalMove) == 1)) {
			System.out.println("\n" + " this is a one by one move");

			if (this.currentPiece.isKing() && ((Math.abs(verticalMove) == 0 && Math.abs(horizontalMove) == 1) || (Math.abs(verticalMove) == 1 && Math.abs(horizontalMove) == 0))) {
				System.out.println("hit 1");
				this.turnFinished = true;				
				return true;

			}
			else if (Math.abs(verticalMove) == 1 && Math.abs(horizontalMove) == 1) {
				System.out.println("hit 2");	
				this.turnFinished = true;
				if (this.currentPiece.isKing()){
					System.out.println("hit 2a");	
					return true;
				}
				else{
					System.out.println("hit 2b");	
					return correctDirectionMove(verticalMove);
				}
			}
			else {
				System.out.println("hit 3");	
				return false;
			}
		}

		//capture code ...... >>>>>

		else if (Math.abs(verticalMove) == 2 || Math.abs(horizontalMove) == 2) {
			boolean isKingAndLateralCaptureMove = (this.currentPiece.isKing() && (((Math.abs(verticalMove) == 2) && (Math.abs(horizontalMove) == 0)) || ((Math.abs(verticalMove) == 0) && (Math.abs(horizontalMove) == 2))));
			if (Math.abs(verticalMove) == 2 && Math.abs(horizontalMove) == 2) {
				//this one is for diagonal
				if (this.currentPiece.isKing()) {
					if (horizontalMove > 0 && verticalMove > 0) {
						System.out.println("hit king only capture for diagonal 1");
						inBetweenPieceX = xi + 1;
						inBetweenPieceY = yi + 1;
					}
					else if (horizontalMove > 0 && verticalMove < 0) {
						System.out.println("hit king only capture for diagonal 2");
						inBetweenPieceX = xi + 1;						
						inBetweenPieceY = yi - 1;
					}
					if (horizontalMove < 0 && verticalMove > 0) {
						System.out.println("hit king only capture for diagonal 3");
						inBetweenPieceX = xi - 1;
						inBetweenPieceY = yi + 1;
					}
					else if (horizontalMove < 0 && horizontalMove < 0) {
						System.out.println("hit king only capture for diagonal 4");
						inBetweenPieceX = xi - 1;
						inBetweenPieceY = yi - 1;
					}
				}
				else {
					System.out.println("hit diagonal capture");	
					if (this.currentPiece.isFire()) { 
						System.out.println("hit diagonal capture 1");	
						inBetweenPieceY = yi + 1;
					}
					else {
						System.out.println("hit diagonal capture 2");	
						inBetweenPieceY = yi - 1;
					}
					if (horizontalMove > 0) {
						System.out.println("hit diagonal capture 3");	
						inBetweenPieceX = xi + 1;
					}
					else {
						System.out.println("hit diagonal capture 4");	
						inBetweenPieceX = xi - 1;
					}
				}
			}
			else if (isKingAndLateralCaptureMove) {
				//this one is for up down or left and right
				System.out.println("hit king only capture for lateral");	
				if (xf == xi && verticalMove > 0) {
					System.out.println("hit king only capture for lateral 1");
					inBetweenPieceY = yi + 1;
				}
				else if (xf == xi && verticalMove < 0) {
					System.out.println("hit king only capture for lateral 2");
					inBetweenPieceY = yi - 1;
				}
				if (yf == yi && horizontalMove > 0) {
					System.out.println("hit king only capture for lateral 3");
					inBetweenPieceX = xi + 1;
				}
				else if (yf == yi && horizontalMove < 0) {
					System.out.println("hit king only capture for lateral 4");
					inBetweenPieceX = xi - 1;
				}
			}
			Piece possibleCapture = this.pieceAt(inBetweenPieceX, inBetweenPieceY);
			
			if ((possibleCapture != null)) {
				System.out.println("hit 1st" + " " + inBetweenPieceX + " " + inBetweenPieceY + " " + Math.abs(verticalMove)  + " " + Math.abs(horizontalMove) + " " +  (possibleCapture != null) + " " +  possibleCapture.isFire() + " " +  this.currentPiece.isFire() );
			}
			else {
				System.out.println("hit 2nd" + " " + inBetweenPieceX + " " + inBetweenPieceY + " " + Math.abs(verticalMove) + " " + Math.abs(horizontalMove) + " " +  (possibleCapture != null) + " " +  this.currentPiece.isFire() );
			}
			if (possibleCapture != null) {

				boolean isCaptureOfOppositeSide = ((possibleCapture.isFire() && !this.currentPiece.isFire()) || (!possibleCapture.isFire() && this.currentPiece.isFire()));
				
				if (Math.abs(verticalMove) == 2 && Math.abs(horizontalMove) == 2 && isCaptureOfOppositeSide) {
					if (this.currentPiece.isKing()){
						System.out.println("4th");
						return true;
					}
					else {
						System.out.println("5th");
						return correctDirectionMove(verticalMove);
					}
				}
				else if (isKingAndLateralCaptureMove && isCaptureOfOppositeSide) {
					System.out.println("6th");
					return true;
				}
				else {
					System.out.println("7th");
					return false;
				}
			}
			else {
				System.out.println("8th");
				return false;
			}
		}
		else {
			System.out.println("9th");
			return false;
		}
	}

	private boolean canMultiCapture(int xi, int yi, int xf, int yf) {
		if (Math.abs(xf - xi) == 2 || Math.abs(yf - yi) == 2) {
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					System.out.println("\n1canMulticapture: " + (xi + i) + " " + (yi + j) + " xi & yi: " + xi + " " + yi + " xf & yf: " + xf + " " + yf);
					if (!(i == 0 && j == 0) && this.pieceAt(xi + i, yi + j) != null ) {
						System.out.println("2canMulticapture piece in middle: " + (xi + i) + " " + (yi + j));
						if (this.validMove(xi, yi, xf, yf)) {
							System.out.println("BINGO: " + (xf) + " " + (yf));
							return true;
						}
					}
				}
			}
			return false;
		}
		else
			return false;
	}

	public void select(int x, int y) {
		if (this.gamePieces[x][y] == null) {
			System.out.println("check if select to a empty space works");
			this.currentPiece.move(x, y);
			this.pieceMoved = true;
		}
		this.currentPiece = this.pieceAt(x, y);
		this.selectedPieceX = x;
		this.selectedPieceY = y;
		this.currentPieceSelected = true;

		if(this.currentPiece.isBomb() && this.currentPiece.hasCaptured()) {
			this.bombExploded = true;
		}

		System.out.println("new piece location x(horiztonal): " + this.selectedPieceX + ", y(vertical): " + this.selectedPieceY + "\n \n");
	}

	public void place(Piece p, int x, int y) {
		if (this.outOfBounds(x, y) || p == null) {
			return;
		}
		else {
			this.gamePieces[x][y] = p;
		}
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
		if (this.bombExploded) {
			return true;
		}
		else if (this.currentPiece == null) {
			return false;
		}
		else if (this.turnFinished || this.currentPiece.hasCaptured()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void endTurn() {
		if (this.currentPlayer == 0) {
			this.currentPlayer = 1;
		}
		else {
			this.currentPlayer = 0;
		}

		this.currentPieceSelected = false;
		this.pieceMoved = false;
		this.bombExploded = false;

		if (this.currentPiece != null) {
			this.currentPiece.doneCapturing();
			this.currentPiece = null;
		}
	}


	public String winner() {
		int player0PiecesLeft = this.countPlayerPiecesLeft(0);
		int player1PiecesLeft = this.countPlayerPiecesLeft(1);

		if (player1PiecesLeft == 0 && player0PiecesLeft == 0) {
			return "No one";
		}
		else if (player1PiecesLeft == 0) {
			return "Water";
		}
		else if (player0PiecesLeft == 0) {
			return "Fire";
		}
		else {
			return null;
		}
	}

	private int countPlayerPiecesLeft(int currentPlayer) {
		int total = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
            	if (this.gamePieces[i][j] != null && this.gamePieces[i][j].side() == this.currentPlayer) {
            		total += 1;
            	}
            }
        }
        return total;
	}


	public static void main(String[] args) {
		Board gameBoard = new Board(false);
		gameBoard.startBoardGame();
	}
}