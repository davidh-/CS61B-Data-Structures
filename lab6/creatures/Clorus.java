package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/** An implementation of a fierce blue colored box that enjoys nothing 
 *  more than snacking on hapless Plips.
 *  @author David Dominguez Hooper
 */
public class Clorus extends Creature {
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;


    /** creates Clorus with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /** creates a Clorus with energy equal to 1. */
    public Clorus() {
        this(1);
    }

    /** The color method for Cloruses should always return the 
     *  color R = 34, G = 0, B = 231.
     */
    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }

    /** If a Clorus attacks another Creature, it should gain that Creature's 
     *  energy. This should happen in the attack() method, not in chooseAction(). 
     *  You do not need to worry about making sure the attacked() creature dies — 
     *  the simulator does that for you. 
     */
    public void attack(Creature c) {
        this.energy += c.energy();
    }

    /** Cloruses should lose 0.03 units of energy on a move action. */
    public void move() {
        this.energy -= 0.03;
    }


    /** Cloruses gain lose 0.01 units of energy on a stay action. */
    public void stay() {
        this.energy -= 0.01;
    }

    /** Cloruses and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Clorus.
     */
    public Clorus replicate() {
        this.energy *= 0.5;
        return new Clorus(this.energy);
    }

    /** Cloruses should obey exactly the following behavioral rules:
     *  1. If there are no empty squares the Clorus will STAY (even if 
     *     there are Plips nearby they could attack).
     *  2. Otherwise, if any Plips are seen, the Clorus will ATTACK one 
     *     of them randomly.
     *  3. Otherwise, if the Clorus has energy greater than or equal to one,
     *     it will REPLICATE to a random empty square.
     *  4. Otherwise, the Clorus will MOVE.
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plipNeighbors = getNeighborsOfType(neighbors, "plip");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        else if (plipNeighbors.size() >= 1) {
            Direction attackDir = HugLifeUtils.randomEntry(plipNeighbors);
            return new Action(Action.ActionType.ATTACK, attackDir);
        }
        else if (this.energy >= 1) {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }
        else {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.MOVE, moveDir);
        }
    }
}
