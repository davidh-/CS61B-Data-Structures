public class Piece {
	/** Piece class will handle the Piece operations of proj0.
	 *  @author David Dominguez Hooper - Finished on 2/14/15 - 3:14AM
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
	public void move(int x, int y) {
		if (Math.abs(x - this.positionX) == 2 || Math.abs(y - this.positionY) == 2) {			
			if (this.isBomb()) {
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if ((this.gameBoard.pieceAt(x + i, y + j) != null) && this.gameBoard.pieceAt(x + i, y + j).isShield() == false) {
							this.gameBoard.remove(x + i, y + j);
						}
					}
				}
			}
			int [] capturedXY = this.locateCapturePiece(this.positionX, this.positionY, x, y);
			int capturedX = capturedXY[0];
			int capturedY = capturedXY[1];
			if (this.gameBoard.pieceAt(capturedX, capturedY) != null) {
				this.gameBoard.remove(capturedX, capturedY);
			}
			this.hasCaptured = true;
		}
		this.gameBoard.remove(this.positionX, this.positionY);
		this.gameBoard.place(this, x, y);
		this.positionX = x;
		this.positionY = y;
		if ((this.isFire() && y == 7) || (!this.isFire() && y == 0)) {
			this.isKing = true;
		}
	}
	public boolean hasCaptured() {
		return this.hasCaptured;
	} 
	public void doneCapturing() {
		this.hasCaptured = false;
	}
	private int[] locateCapturePiece(int xi, int yi, int xf, int yf) {
		int horizontalMove = xf - xi;
		int verticalMove = yf - yi;
		int inBetweenPieceX = xi;
		int inBetweenPieceY = yi;
		boolean isKingAndLateralCaptureMove = (this.isKing() && (((Math.abs(verticalMove) == 2) && (Math.abs(horizontalMove) == 0)) || ((Math.abs(verticalMove) == 0) && (Math.abs(horizontalMove) == 2))));
		if (Math.abs(verticalMove) == 2 && Math.abs(horizontalMove) == 2) {
			if (this.isKing()) {
				if (horizontalMove > 0 && verticalMove > 0) {
					inBetweenPieceX = xi + 1;
					inBetweenPieceY = yi + 1;
				}
				else if (horizontalMove > 0 && verticalMove < 0) {
					inBetweenPieceX = xi + 1;						
					inBetweenPieceY = yi - 1;
				}
				if (horizontalMove < 0 && verticalMove > 0) {
					inBetweenPieceX = xi - 1;
					inBetweenPieceY = yi + 1;
				}
				else if (horizontalMove < 0 && verticalMove < 0) {
					inBetweenPieceX = xi - 1;
					inBetweenPieceY = yi - 1;
				}
			}
			else {
				if (this.isFire()) { 
					inBetweenPieceY = yi + 1;
				}
				else {
					inBetweenPieceY = yi - 1;
				}
				if (horizontalMove > 0) {
					inBetweenPieceX = xi + 1;
				}
				else {
					inBetweenPieceX = xi - 1;
				}
			}
		}
		else if (isKingAndLateralCaptureMove) {
			if (xf == xi && verticalMove > 0) {
				inBetweenPieceY = yi + 1;
			}
			else if (xf == xi && verticalMove < 0) {
				inBetweenPieceY = yi - 1;
			}
			if (yf == yi && horizontalMove > 0) {
				inBetweenPieceX = xi + 1;
			}
			else if (yf == yi && horizontalMove < 0) {
				inBetweenPieceX = xi - 1;
			}
		}
		return new int[] {inBetweenPieceX, inBetweenPieceY};
	}
}