package ngordnet;

import org.junit.Test;
import static org.junit.Assert.*;
/** 
 *  @author David Dominguez Hooper
 */

public class  YearlyRecordTest{

	@Test
	public void testBasic() {
		YearlyRecord yr = new YearlyRecord();
        yr.put("quayside", 95);        
        yr.put("surrogate", 95);
        yr.put("merchantman", 181);      
        System.out.println(yr.words());

	}

	public static void main(String[] args) {
        jh61b.junit.textui.runClasses(YearlyRecordTest.class);
    }
}


