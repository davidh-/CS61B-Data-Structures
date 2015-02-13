public class Piece {
	/** Piece class will handle the Piece operations of proj0.
	 *  @author David Dominguez Hooper
	 *  [Do not modify this file.]
	 */
	private int isWaterPiece;
	private Board gameBoard;
	private int positionX;
	private int positionY;
	private String type;

	private boolean isKing;
	private boolean hasCaptured;

	public Piece(boolean isFire, Board b, int x, int y, String type) {
		if(isFire)
			this.isWaterPiece = 0;
		else
			this.isWaterPiece = 1;
		this.gameBoard = b;
		this.positionX = x;
		this.positionY = y;
		this.type = type;
		this.isKing = false;
		this.hasCaptured = false;
	}

	public boolean isFire() {
		return this.isWaterPiece == 0;
	}

	public int side() {
		// Returns 0 if the piece is a fire piece, 
		// or 1 if the piece is a water piece.
		return this.isWaterPiece;
	}

	public boolean isKing() {
		return this.isKing;
	}

	public boolean isBomb() {
		return this.type == "bomb";
	} 

	public boolean isShield() {
		return this.type == "shield";
	}

	private int[] locateCapturePiece(int xi, int yi, int xf, int yf) {
		int horizontalMove = xf - xi;
		int verticalMove = yf - yi;

		int inBetweenPieceX = xi;
		int inBetweenPieceY = yi;

		boolean isKingAndLateralCaptureMove = (this.isKing() && (((Math.abs(verticalMove) == 2) && (Math.abs(horizontalMove) == 0)) || ((Math.abs(verticalMove) == 0) && (Math.abs(horizontalMove) == 2))));
		
		if (Math.abs(verticalMove) == 2 && Math.abs(horizontalMove) == 2) {
			//this one is for diagonal
			if (this.isKing()) {
				if (horizontalMove > 0 && verticalMove > 0) {
					System.out.println("hit king only capture for lateral 1");
					inBetweenPieceX = xi + 1;
					inBetweenPieceY = yi + 1;
				}
				else if (horizontalMove > 0 && verticalMove < 0) {
					System.out.println("hit king only capture for lateral 2");
					inBetweenPieceX = xi + 1;						
					inBetweenPieceY = yi - 1;
				}
				if (horizontalMove < 0 && verticalMove > 0) {
					System.out.println("hit king only capture for lateral 3");
					inBetweenPieceX = xi - 1;
					inBetweenPieceY = yi + 1;
				}
				else if (horizontalMove < 0 && horizontalMove < 0) {
					System.out.println("hit king only capture for lateral 4");
					inBetweenPieceX = xi - 1;
					inBetweenPieceY = yi - 1;
				}
			}
			else {
				System.out.println("hit diagonal capture");	
				if (this.isFire()) { 
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


		return new int[] {inBetweenPieceX, inBetweenPieceY};
	}

	public void move(int x, int y) {
		if (Math.abs(x - this.positionX) == 2 || Math.abs(y - this.positionY) == 2) {
			int [] capturedXY = this.locateCapturePiece(this.positionX, this.positionY, x, y);
			int capturedX = capturedXY[0];
			int capturedY = capturedXY[1];
			this.gameBoard.remove(capturedX, capturedY);
			this.hasCaptured = true;
		}
		this.gameBoard.remove(this.positionX, this.positionY);
		this.gameBoard.place(this, x, y);
		this.positionX = x;
		this.positionY = y;

		// check if you need to King the piece
		if ((this.isFire() && y == 7) || (!this.isFire() && y == 0))
			this.isKing = true;

	}


	public boolean hasCaptured() {
		return this.hasCaptured;
	} 

	public void doneCapturing() {
		this.hasCaptured = false;
	}

}