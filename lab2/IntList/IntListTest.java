import static org.junit.Assert.*;
import org.junit.Test;

public class IntListTest {

    /** Example test that verifies correctness of the IntList.list static 
     *  method. The main point of this is to convince you that 
     *  assertEquals knows how to handle IntLists just fine.
     */

    @Test 
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.list(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    @Test
    public void testdSquareList() {
      IntList L = IntList.list(1, 2, 3);
      IntList.dSquareList(L);
      assertEquals(IntList.list(1, 4, 9), L);
    }

    /** Do not use the new keyword in your tests. You can create
     *  lists using the handy IntList.list method.  
     * 
     *  Make sure to include test cases involving lists of various sizes
     *  on both sides of the operation. That includes the empty list, which
     *  can be instantiated, for example, with 
     *  IntList empty = IntList.list(). 
     *
     *  Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     *  Anything can happen to A. 
     */

    //TODO:  Create testSquareListRecursive()
    //TODO:  Create testDcatenate and testCatenate
    @Test
    public void testSquareListRecursive() {
        IntList L = IntList.list(1, 2, 3, 4);
        IntList newL = IntList.squareListRecursive(L);
        assertEquals(IntList.list(1, 4, 9, 16), newL);
        assertEquals(IntList.list(1, 2, 3, 4), L);
    }

    @Test
    public void testDcatenate() {
        IntList A = IntList.list(1, 2, 3, 4);
        IntList B = IntList.list(5, 6, 7, 8);
        IntList abDcatenate = IntList.dcatenate(A, B);
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6, 7, 8), abDcatenate);
    }

    @Test
    public void testCatenate() {
        IntList A = IntList.list(1, 2, 3, 4);
        IntList B = IntList.list(5, 6, 7, 8);
        IntList abcatenate = IntList.catenate(A, B);
        assertEquals(IntList.list(1, 2, 3, 4, 5, 6, 7, 8), abcatenate);
        assertEquals(IntList.list(1, 2, 3, 4), A);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(IntListTest.class);
    }       
}   
