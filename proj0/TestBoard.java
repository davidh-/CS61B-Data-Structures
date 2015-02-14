import static org.junit.Assert.*;
import org.junit.Test;

public class TestBoard {
	/** TestBoard class will test the Board class of proj0.
	 *  @author David Dominguez Hooper
	 *  [Do not modify this file.]
	 */

    @Test
    public void testWinnerWith3outcomes() {
    	Board testBoard = new Board(true);
    	assertEquals("No one", testBoard.winner());

    	Piece testPieceFire = new Piece(true, testBoard, 3, 5, "pawn");
    	Piece testPieceWater = new Piece(false, testBoard, 2, 6, "pawn");

    	testBoard.place(testPieceFire, 3, 5);
    	assertEquals(testPieceFire, testBoard.pieceAt(3, 5));
    	testBoard.place(testPieceWater, 2, 6);
    	assertEquals(testPieceWater, testBoard.pieceAt(2, 6));


    	assertEquals(1, testBoard.countPlayerPiecesLeft()[0]);
    	assertEquals(1, testBoard.countPlayerPiecesLeft()[1]);

    	assertEquals(testBoard.winner(), null);
    	testBoard.select(3, 5);

    	assertEquals(1, testBoard.countPlayerPiecesLeft()[0]);
    	assertEquals(1, testBoard.countPlayerPiecesLeft()[1]);

    	testBoard.select(1, 7);
    	assertEquals(1, testBoard.countPlayerPiecesLeft()[0]);
    	assertEquals(0, testBoard.countPlayerPiecesLeft()[1]);

    	testBoard.endTurn();

    	assertEquals(1, testBoard.countPlayerPiecesLeft()[0]);
    	assertEquals(0, testBoard.countPlayerPiecesLeft()[1]);


      	assertEquals("Fire", testBoard.winner());


    }

	public static void main(String[] args) {
		jh61b.junit.textui.runClasses(TestBoard.class);
	}
}