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

	public void move(int x, int y) {

	}

	public boolean hasCaptured() {
		return this.hasCaptured;
	} 

	public void doneCapturing() {
		this.hasCaptured = false;
	}



	public static void main(String[] args) {

	}
}