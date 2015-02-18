import java.util.Comparator;

public class RadiusComparator implements Comparator<Planet> {

	public RadiusComparator() {
	}

	public int compare(Planet planet1, Planet planet2) {
		if ((planet1.getRadius() - planet2.getRadius()) > 0) {
			return 1;
		}
		else if ((planet1.getRadius() - planet2.getRadius()) == 0) {
			return 0;
		}
		else {
			return -1;
		}
	}
}