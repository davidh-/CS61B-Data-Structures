public class Planet {

	public double x;
	public double y;
	public double xVelocity;
	public double yVelocity;
	public double mass;
	public String img;

	public double xNetForce;
	public double yNetForce;

	public double xAccel;
	public double yAccel;

	private double radius;

	public static void main(String[] args) {
		Planet p1 = new Planet(1.0, 1.0, 3.0, 4.0, 5.0, "images/jupiter.gif", 1.0);
		p1.draw();
	}

	public Planet(double x, double y, double xVelocity, double yVelocity, double mass, String img, double radius) {
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.mass = mass;
		this.img = img;
		this.radius = radius;
	}

	public double getMass() {
		return this.mass;
	}
	public double getRadius() {
		return this.radius;
	}

	public double calcDistance(Planet planet) {
		return Math.sqrt(((planet.x - this.x)*(planet.x - this.x)) + ((planet.y - this.y)*(planet.y - this.y)));
	}

	public double calcPairwiseForce(Planet planet) {
		final double g = 6.67E-11; 
		double distance = this.calcDistance(planet);
		return (g * this.mass * planet.mass) / (distance * distance);
	}

	public double calcPairwiseForceX(Planet planet) {
		return this.calcPairwiseForce(planet) * (planet.x - this.x) / this.calcDistance(planet);
	}

	public double calcPairwiseForceY(Planet planet) {
		return this.calcPairwiseForce(planet) * (planet.y - this.y) / this.calcDistance(planet);
	}

	public void setNetForce(Planet[] planets) {
		this.xNetForce = 0;
		this.yNetForce = 0;
		for (Planet planet : planets) {
			if (!this.equals(planet)) {
				this.xNetForce += this.calcPairwiseForceX(planet);
				this.yNetForce += this.calcPairwiseForceY(planet);
			}
		}
	}

	public void draw() {
		StdDraw.picture(this.x, this.y, "images/" + this.img);
	}

	public void update(double dt) {
		this.xAccel = this.xNetForce / this.mass;
		this.yAccel = this.yNetForce / this.mass;
		this.xVelocity = this.xVelocity + dt * this.xAccel;
		this.yVelocity = this.yVelocity + dt * this.yAccel;
		this.x = this.x + dt * this.xVelocity;
		this.y = this.y + dt * this.yVelocity;
	}
}


