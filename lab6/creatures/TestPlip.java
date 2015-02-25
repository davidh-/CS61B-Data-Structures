package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

import java.util.List;

/** Tests the plip class   
 *  @authr FIXME
 */

public class TestPlip {

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Plip p = new Plip(2);
        Plip offspring = p.replicate();
        assertNotSame(p, offspring);
        assertEquals(1.0, p.energy(), 0.01);
        assertEquals(1.0, offspring.energy(), 0.01);
    }

    @Test
    public void testChoose() {
        // This tests if there are no empty spaces, the Plip should stay
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible()); 
        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);


        //You can create new empties with new Empty();
        // This tests if it replicates to another empty space if energy > 1.0.
        Plip replicateTest = new Plip(1.2);
        HashMap<Direction, Occupant> emptyAllAround = new HashMap<Direction, Occupant>();
        emptyAllAround.put(Direction.TOP, new Empty());
        emptyAllAround.put(Direction.BOTTOM, new Impassible());
        emptyAllAround.put(Direction.LEFT, new Impassible());
        emptyAllAround.put(Direction.RIGHT, new Impassible());
        List<Direction> empties = replicateTest.getNeighborsOfType(emptyAllAround, "empty");
        Direction moveDir = empties.get(0);
        Action actualRepTest = replicateTest.chooseAction(emptyAllAround);
        Action expectedRepTest = new Action(Action.ActionType.REPLICATE, moveDir);
        assertEquals(expectedRepTest, actualRepTest);

        //Despite what the spec says, you cannot test for Cloruses nearby yet.
        //Sorry! 

        // This tests the else condition, Plip should stay otherwise
        Plip otherwiseTest = new Plip(0.3);
        HashMap<Direction, Occupant> emptyButTwo = new HashMap<Direction, Occupant>();
        emptyButTwo.put(Direction.TOP, new Impassible());
        emptyButTwo.put(Direction.BOTTOM, new Empty());
        emptyButTwo.put(Direction.LEFT, new Impassible());
        emptyButTwo.put(Direction.RIGHT, new Impassible());
        Action actualOtherwiseTest = otherwiseTest.chooseAction(emptyButTwo);
        Action expectedOtherwiseTest = new Action(Action.ActionType.STAY);
        assertEquals(expectedOtherwiseTest, actualOtherwiseTest);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }
} 
