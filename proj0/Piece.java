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
	private boolean isFireType;

	public Piece(boolean isFire, Board b, int x, int y, String type) {

	}

	public boolean isFire() {
		return false;
	}

	public int side() {
		// Returns 0 if the piece is a fire piece, 
		// or 1 if the piece is a water piece.
		return this.isWaterPiece;
	}

	public boolean isKing() {
		return false;
	}

	public boolean isBomb() {
		return false;
	} 

	public boolean isShield() {
		return false;
	}

	public void move(int x, int y) {

	}

	public boolean hasCaptured() {
		return false;
	} 

	public void doneCapturing() {

	}




	public static void main(String[] args) {

	}
}