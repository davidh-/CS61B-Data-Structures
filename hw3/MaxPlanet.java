import java.util.Comparator;
/* Import anything else you need here. */

/**
 * MaxPlanet.java
 */

public class MaxPlanet {

    /** Returns the Planet with the maximum value according to Comparator c. */
    public static Planet maxPlanet(Planet[] planets, Comparator<Planet> c) {
        Planet maxOfThePlanets = planets[0];
        for (int i = 1; i < planets.length; i++) {
        	if (c.compare(maxOfThePlanets, planets[i]) < 0) {
        		maxOfThePlanets = planets[i];
        	}
        }
        return maxOfThePlanets;
    }
}