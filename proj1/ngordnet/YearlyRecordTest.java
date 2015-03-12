package ngordnet;

import org.junit.Test;
import static org.junit.Assert.*;
/** 
 *  @author David Dominguez Hooper
 */

public class YearlyRecordTest {
    private static final int NUM_95 = 95;
    private static final int NUM_181 = 181;

    @Test
    public void testBasic() {
        YearlyRecord yr = new YearlyRecord();
        yr.put("quayside", NUM_95);        
        yr.put("surrogate", NUM_95);
        yr.put("merchantman", NUM_181);      
        System.out.println(yr.words());
    }
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(YearlyRecordTest.class);
    }
}


