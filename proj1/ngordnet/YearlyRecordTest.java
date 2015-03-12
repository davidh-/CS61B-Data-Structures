package ngordnet;

import java.util.HashMap;
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
        HashMap<String, Integer> data = new HashMap<String, Integer>();
        data.put("surrogate", 340);
        data.put("quayside", 95);
        data.put("merchantman", 181);
        YearlyRecord yr = new YearlyRecord(data);
        yr.rank("surrogate");

        yr.put("potato", 5000);
        yr.rank("potato");

        yr.put("quayside", 10000);
        yr.rank("quayside");

        System.out.println(yr.rank("quayside"));
        yr.put("quayside", NUM_95);        
        yr.put("surrogate", NUM_95);

    }
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(YearlyRecordTest.class);
    }
}


