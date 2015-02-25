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

/** Tests the Clorus class   
 *  @author David Dominguez Hooper
 */

public class TestClorus {

    @Test
    public void testChoose1() {
        // This tests if there are no empty spaces, the Clorus should stay
        Clorus c = new Clorus(5);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible()); 
        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }
    @Test
    public void testChoose2() {
        // This tests if any Plips are seen, the Clorus will ATTACK one 
        // of them randomly.
        Clorus c = new Clorus(5);
        Plip p1 = new Plip(1.2);
        Plip p2 = new Plip(2.0);
        HashMap<Direction, Occupant> twoPlipsAround = new HashMap<Direction, Occupant>();
        twoPlipsAround.put(Direction.TOP, new Empty());
        twoPlipsAround.put(Direction.BOTTOM, new Impassible());
        twoPlipsAround.put(Direction.LEFT, p1);
        twoPlipsAround.put(Direction.RIGHT, p2); 
        List<Direction> plipLocations = c.getNeighborsOfType(twoPlipsAround, "plip");
        Direction attackDir = plipLocations.get(0);
        Action actual = c.chooseAction(twoPlipsAround);
        Action expected = new Action(Action.ActionType.ATTACK, attackDir);
        assertEquals(expected, actual);
    }
    @Test
    public void testChoose3() {
        // This tests if the Clorus has energy greater than or equal to one, 
        // it will REPLICATE to a random empty square.
        Clorus c = new Clorus(5);
        HashMap<Direction, Occupant> emptyButOne = new HashMap<Direction, Occupant>();
        emptyButOne.put(Direction.TOP, new Empty());
        emptyButOne.put(Direction.BOTTOM, new Impassible());
        emptyButOne.put(Direction.LEFT, new Impassible());
        emptyButOne.put(Direction.RIGHT, new Impassible());
        List<Direction> empties = c.getNeighborsOfType(emptyButOne, "empty");
        Direction replicateDir = empties.get(0);
        Action actual = c.chooseAction(emptyButOne);
        Action expected = new Action(Action.ActionType.REPLICATE, replicateDir);
        assertEquals(expected, actual);
    }
    @Test
    public void testChoose4() {
        // This tests if otherwise, the Clorus will MOVE.
        Clorus c = new Clorus(0.5);
        HashMap<Direction, Occupant> emptyButOne = new HashMap<Direction, Occupant>();
        emptyButOne.put(Direction.TOP, new Impassible());
        emptyButOne.put(Direction.BOTTOM, new Impassible());
        emptyButOne.put(Direction.LEFT, new Empty());
        emptyButOne.put(Direction.RIGHT, new Impassible());
        List<Direction> empties = c.getNeighborsOfType(emptyButOne, "empty");
        Direction moveDir = empties.get(0);
        Action actual = c.chooseAction(emptyButOne);
        Action expected = new Action(Action.ActionType.MOVE, moveDir);
        assertEquals(expected, actual);
    }
    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
} 
