import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

/** ULLMapTest. You should write additional tests.
 *  @author Josh Hug
 */

public class ULLMapTest {
    @Test
    public void testBasic() {
        ULLMap<String, String> um = new ULLMap<String, String>();
        um.put("Gracias", "Dios Basado");
        assertEquals(um.get("Gracias"), "Dios Basado");
    }
    @Test
    public void testPutAndGet() {
        ULLMap<String, Integer> numbers = new ULLMap<String, Integer>();
        numbers.put("one", 1);
        numbers.put("two", 2);
        numbers.put("ten", 10);
        assertEquals(2, numbers.get("two"), 0);
        assertEquals(10, numbers.get("ten"), 0);
        numbers.put("ten", 1);
        assertEquals(1, numbers.get("ten"), 0);
        numbers.put("two", 10);
        assertEquals(1, numbers.get("ten"), 0);
        assertEquals(10, numbers.get("two"), 0);
    }

    
    @Test
    public void testIterator() {
        ULLMap<Integer, String> um = new ULLMap<Integer, String>();
        um.put(0, "zero");
        um.put(1, "one");
        um.put(2, "two");
        Iterator<Integer> umi = um.iterator();
        System.out.println(umi.next());
    }
    

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(ULLMapTest.class);
    }
} 