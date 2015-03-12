package ngordnet;

import org.junit.Test;
import static org.junit.Assert.*;
/** 
 *  @author David Dominguez Hooper
 */

public class YearlyRecordTest {
    private static final int NUM_95 = 95;
    private static final int NUM_181 = 181;
    private static final int NUM_1 = 1;
    private static final int NUM_1000 = 1000;
    private static final int NUM_200 = 200;

    @Test
    public void testBasic() {
        YearlyRecord yr = new YearlyRecord();
        yr.put("hello", NUM_1);
        yr.put("quayside", NUM_95);        
        yr.put("surrogate", NUM_95);
        yr.put("merchantman", NUM_181);      
        System.out.println(yr.words());
        int q = yr.rank("quayside");
        System.out.println("quayside rank: " + q + "\n");
        int s = yr.rank("surrogate");
        System.out.println("surrogate rank: " + s + "\n");
        int m = yr.rank("merchantman");
        System.out.println("merchantman rank: " + m + "\n");
        int h = yr.rank("hello");
        System.out.println("hello rank: " + h + "\n");

        yr.put("jumbo", NUM_1000);
        yr.put("rhino", NUM_200); 

        int j = yr.rank("jumbo");
        System.out.println("jumbo rank: " + j + "\n");
        int r = yr.rank("rhino");
        System.out.println("rhino rank: " + r + "\n");



    }
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(YearlyRecordTest.class);
    }
}


