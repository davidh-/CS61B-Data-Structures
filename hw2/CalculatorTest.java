import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    /* Do not change this to be private. For silly testing reasons it is public. */
    public Calculator tester;

    /**
     * setUp() performs setup work for your Calculator.  In short, we 
     * initialize the appropriate Calculator for you to work with.
     * By default, we have set up the Staff Calculator for you to test your 
     * tests.  To use your unit tests for your own implementation, comment 
     * out the StaffCalculator() line and uncomment the Calculator() line.
     **/
    @Before
    public void setUp() {
        // tester = new StaffCalculator(); // Comment me out to test your Calculator
        tester = new Calculator();   // Un-comment me to test your Calculator
    }

    // TASK 1: WRITE JUNIT TESTS
    // YOUR CODE HERE
    @Test
    public void testAdditionWithTwoPositives() {
        int add = tester.add(3, 4);
        assertEquals(7, add);
    }
    @Test
    public void testAdditionWithTwoNegatives() {
        int negative = tester.add(-2, -5);
        assertEquals(-7, negative);
    }
    @Test
    public void testAdditionWithOneNegative() {
        int positive = tester.add(-3, 5);
        assertEquals(2, positive);
    }


    @Test
    public void testMultiplicationWithTwoPositives() {
        int positive = tester.multiply(16, 2);
        assertEquals(32, positive);
    }
    @Test
    public void testMultiplicationWithTwoNegatives() {
        int positive = tester.multiply(-8, -10);
        assertEquals(80, positive);
    }
    @Test
    public void testMultiplicationWithOneNegative() {
        int negative = tester.multiply(-3, 5);
        assertEquals(-15, negative);
    }
    @Test
    public void testMultiplicationWithOneZero() {
        int zero = tester.multiply(-3, 0);
        assertEquals(0, zero);
    }
    @Test
    public void testMultiplicationWithBigNumbers() {
        int zero = tester.multiply(1234, 5678);
        assertEquals(7006652, zero);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(CalculatorTest.class);
    }       

}