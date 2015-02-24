package synthesizer;
import org.junit.Test;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Julian Early & Will Huang
 */

public class TestArrayRingBuffer {
	ArrayRingBuffer arb1;
    ArrayRingBuffer arb2;

	@Before
	public void setUp()
	{
		arb1 = new synthesizer.ArrayRingBuffer(1);
        arb2 = new synthesizer.ArrayRingBuffer(3);
	}

    @Test
    public void enqueueTests() {
        assertEquals(true,arb1.isEmpty());
        assertEquals(false,arb1.isFull());
        arb1.enqueue(0.0);
        try
        {
        	arb1.enqueue(1);
        	fail("Expected RuntimeException");
        }
        catch(RuntimeException aRuntimeException)
        {
        	assertThat(aRuntimeException.getMessage(), is("Ring buffer overflow"));
        }
        assertEquals(true,arb1.isFull());
        assertEquals(false,arb1.isEmpty());
    }

    @Test
    public void testRing(){
        arb2.enqueue(33.1); // "33.1"     0     0
        arb2.enqueue(44.8); // "33.1"  44.8     0
        arb2.enqueue(62.3); // "33.1"  44.8  62.3
        assertEquals(33.1,arb2.peek(),0.001); // "33.1"  44.8     62.3

        assertEquals(33.1,arb2.dequeue(),0.001); //    0  "44.8"  62.3
        assertEquals(44.8,arb2.peek(),0.001); // 0  "44.8"     62.3

        arb2.enqueue(-3.4); // -3.4  "44.8"  62.3
        assertEquals(44.8,arb2.dequeue(),0.001); // -3.4     0  "62.3"
        assertEquals(62.3,arb2.peek(),0.001); // -3.4     0  "62.3"

        arb2.enqueue(5.6); // -3.4    5.6  "62.3"
        assertEquals(62.3,arb2.dequeue(),0.001); // "-3.4"   5.6     0
        assertEquals(-3.4,arb2.peek(),0.001); // "-3.4"     5.6  0

        arb2.enqueue(1.2); // "-3.4"   5.6    1.2
        assertEquals(-3.4,arb2.dequeue(),0.001); //    0   "5.6"   1.2
        assertEquals(5.6,arb2.peek(),0.001); //    0  "5.6"  0

        assertEquals(5.6, arb2.dequeue(),0.001); //    0     0   "1.2"
        assertEquals(1.2,arb2.peek(),0.001); // 0     0   "1.2"

        assertEquals(1.2, arb2.dequeue(),0.001); //    0     0   "0"
        arb2.enqueue(10.9); // 10.9    0  "0"
        assertEquals(10.9,arb2.dequeue(),0.001); //    "0"   0   0
        

    }

    @Test
    public void dequeueTest()
    {
    	arb1.enqueue(5);
    	assertEquals(false,arb1.isEmpty());
    	assertThat(arb1.dequeue(),is(5.0));
    	try
    	{
    		arb1.dequeue();
    		fail("Expected RuntimeException");
    	}
    	catch(RuntimeException aRuntimeException)
    	{
    		assertThat(aRuntimeException.getMessage(), is("Ring buffer underflow"));
    	}
    }

    @Test
    public void peekTest()
    {
    	try
    	{
    		arb1.peek();
    		fail("Expected RuntimeException");
    	}
    	catch(RuntimeException aRuntimeException)
    	{
    		assertThat(aRuntimeException.getMessage(), is("Ring buffer underflow"));
    	}
    	arb1.enqueue(5);
    	assertEquals(false,arb1.isEmpty());
    	assertThat(arb1.peek(),is(5.0));
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 