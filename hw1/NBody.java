public class NBody {
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];

		In in = new In(filename);

		int numPlanets = in.readInt();
		double universeRadius = in.readDouble();

		Planet[] planets = new Planet[numPlanets];

		for (int i = 0; i < numPlanets; i++) {
			planets[i] = getPlanet(in);
		}

		StdDraw.setScale(-universeRadius, +universeRadius);
		StdDraw.picture(0, 0, "images/starfield.jpg");

		for (Planet planet : planets) {
			planet.draw();
		}
		double time = 0;
		StdAudio.play("audio/2001.mid");
		while (time < T) {
			for (Planet planet : planets) {
				planet.setNetForce(planets);
			}
			for (Planet planet : planets) {
				planet.update(dt);
			}
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Planet planet : planets) {
				planet.draw();
			}
			StdDraw.show(10);
			time += dt;
			System.out.println(time + " " + T );
		}
	}

	public static Planet getPlanet(In in) {
		double x = in.readDouble();
		double y = in.readDouble();
		double xVelocity = in.readDouble();
		double yVelocity = in.readDouble();
		double mass = in.readDouble();
		String img = in.readString();
		Planet planet = new Planet(x, y, xVelocity, yVelocity, mass, img);
		return planet;
	}
}