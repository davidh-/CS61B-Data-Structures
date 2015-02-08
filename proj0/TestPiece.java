import static org.junit.Assert.*;
import org.junit.Test;

public class TestPiece {
	/** TestPiece class will test the Piece class of proj0.
	 *  @author David Dominguez Hooper
	 *  [Do not modify this file.]
	 */
	

	//dont test move() method
    
    @Test
    public void testPieceConstructor() {
    	Board testBoard = new Board(false);
    	Piece testPiece = new Piece(false, testBoard, 0, 0, "pawn");
    	assertEquals(false, testPiece.isFire());
    }

    @Test
    public void testIsFire() {

    }

    @Test
    public void testSide() {

    }

    @Test
    public void testIsKing() {

    }

    @Test
    public void testIsBomb() {

    }

    @Test
    public void testIsShield() {

    }

    @Test
    public void testHasCaptured() {

    }

    @Test
    public void testDoneCapturing() {

    }


	public static void main(String[] args) {
		jh61b.junit.textui.runClasses(TestPiece.class);
	}
}