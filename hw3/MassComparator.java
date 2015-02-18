import java.util.Comparator;

/**
 * MassComparator.java
 */

public class MassComparator implements Comparator<Planet> {

    public MassComparator() {
    }

    /** Returns the difference in mass as an int.
     *  Round after calculating the difference. */
    public int compare(Planet planet1, Planet planet2) {
		if ((planet1.getMass() - planet2.getMass()) > 0) {
			return 1;
		}
		else if ((planet1.getMass() - planet2.getMass()) == 0) {
			return 0;
		}
		else {
			return -1;
		}
    }
}