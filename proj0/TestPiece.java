import static org.junit.Assert.*;
import org.junit.Test;

public class TestPiece {
	/** TestPiece class will test the Piece class of proj0.
	 *  @author David Dominguez Hooper
	 *  [Do not modify this file.]
	 */
	

	//dont test move() method
    

    @Test
    public void testIsFire() {
		Board testBoard = new Board(false);
    	Piece testPiece = new Piece(false, testBoard, 0, 0, "pawn");
    	assertEquals(false, testPiece.isFire());
    }

    @Test
    public void testSide() {
		Board testBoard = new Board(false);
    	Piece testPiece = new Piece(false, testBoard, 0, 0, "pawn");
    	assertEquals(1, testPiece.side());
    }

    @Test
    public void testIsKing() {
		Board testBoard = new Board(false);
    	Piece testPiece = new Piece(false, testBoard, 0, 0, "pawn");
    	assertEquals(false, testPiece.isKing());
    }

    @Test
    public void testIsBomb() {
		Board testBoard = new Board(false);
    	Piece testPiece = new Piece(false, testBoard, 0, 0, "pawn");
    	assertEquals(false, testPiece.isBomb());
    }

    @Test
    public void testIsShield() {
		Board testBoard = new Board(false);
    	Piece testPiece = new Piece(false, testBoard, 0, 0, "pawn");
    	assertEquals(false, testPiece.isShield());
    }

    @Test
    public void testHasCaptured() {
		Board testBoard = new Board(false);
    	Piece testPiece = new Piece(false, testBoard, 0, 0, "pawn");
    	assertEquals(false, testPiece.hasCaptured());
    }

    @Test
    public void testDoneCapturing() {
		Board testBoard = new Board(false);
    	Piece testPiece = new Piece(false, testBoard, 0, 0, "pawn");
    	assertEquals(false, testPiece.hasCaptured());
    }


	public static void main(String[] args) {
		jh61b.junit.textui.runClasses(TestPiece.class);
	}
}