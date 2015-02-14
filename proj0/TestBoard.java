import static org.junit.Assert.*;
import org.junit.Test;

public class TestBoard {
	/** TestBoard class will test the Board class of proj0.
	 *  @author David Dominguez Hooper
	 *  [Do not modify this file.]
	 */

    // @Test
    // public void testWinnerWith3outcomes() {
    // 	Board testBoard = new Board(true);
    // 	assertEquals("No one", testBoard.winner());

    // 	Piece testPieceFire = new Piece(true, testBoard, 3, 5, "pawn");
    // 	Piece testPieceWater = new Piece(false, testBoard, 2, 6, "pawn");

    // 	testBoard.place(testPieceFire, 3, 5);
    // 	assertEquals(testPieceFire, testBoard.pieceAt(3, 5));
    // 	testBoard.place(testPieceWater, 2, 6);
    // 	assertEquals(testPieceWater, testBoard.pieceAt(2, 6));

    // 	assertEquals(testBoard.winner(), null);
    // 	testBoard.select(3, 5);

    // 	testBoard.select(1, 7);

    // 	testBoard.endTurn();

    // 	assertEquals(1, testBoard.countPlayerPiecesLeft()[0]);
    // 	assertEquals(0, testBoard.countPlayerPiecesLeft()[1]);

    //   	assertEquals("Fire", testBoard.winner());



    //   	Board testBoard2 = new Board(true);

    // 	Piece testPieceFireBomb = new Piece(true, testBoard2, 2, 2, "bomb");
    // 	Piece testPieceWaterBomb = new Piece(false, testBoard2, 3, 3, "bomb");
    	
    // 	testBoard2.place(testPieceFireBomb, 2, 2);
    // 	assertEquals(testPieceFireBomb, testBoard2.pieceAt(2, 2));
    // 	testBoard2.place(testPieceWaterBomb, 3, 3);
    // 	assertEquals(testPieceWaterBomb, testBoard2.pieceAt(3, 3));

    // 	assertEquals(null, testBoard2.winner());

    // 	assertEquals(1, testBoard2.countPlayerPiecesLeft()[0]);
    // 	assertEquals(1, testBoard2.countPlayerPiecesLeft()[1]);

    // 	testBoard2.select(3, 3);
    // 	assertEquals(testPieceWaterBomb, testBoard2.pieceAt(3, 3));
    // 	testBoard2.select(1, 1);

    // 	testBoard2.endTurn();
    // 	assertEquals("No one", testBoard2.winner());
    // }


    @Test
    public void testEndTurnMultipleTimes() {

      	Board testBoard = new Board(true);

    	Piece testPieceFire = new Piece(true, testBoard, 1, 3, "pawn");
    	Piece testPieceWater = new Piece(false, testBoard, 2, 4, "pawn");

    	testBoard.place(testPieceFire, 1, 3);
    	assertEquals(testPieceFire, testBoard.pieceAt(1, 3));

    	testBoard.place(testPieceWater, 2, 4);
    	assertEquals(testPieceWater, testBoard.pieceAt(2, 4));

    	testBoard.select(1, 3);
    	assertEquals(testPieceFire, testBoard.pieceAt(1, 3));
    	assertEquals(false, testBoard.canEndTurn());


    	testBoard.select(0, 4);
    	assertEquals(true, testBoard.canEndTurn());
    	assertEquals(false, testBoard.canSelect(1, 5));


    }








	public static void main(String[] args) {
		jh61b.junit.textui.runClasses(TestBoard.class);
	}
}