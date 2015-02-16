import java.text.DecimalFormat;

public class Planet
{
    public static final double GRAVITY_CONSTANT = 6.67e-11;
    private double px, py, vx, vy, ax, ay, fx, fy, mass, radius;
    private String img;

    public Planet(double px, double py, double vx,
                  double vy, double mass, String img, double radius)
    {
        this.px = px;
        this.py = py;
        this.vx = vx;
        this.vy = vy;
        this.mass = mass;
        this.img = img;
        this.radius = radius;
    }

    /** Returns the mass. */
    public double getMass() {
        return mass;
    }

    /** Returns the radius. */
    public double getRadius() {
        return radius;
    }


    private double calcDistance(Planet other)
    {
        // Euclidean distance d = sqrt(x^2 + y^2)
        double dx2 = Math.pow(other.px - px, 2);
        double dy2 = Math.pow(other.py - py, 2);
        return Math.sqrt(dx2 + dy2);
    }

    private double calcPairwiseForce(Planet other)
    {
        double r = calcDistance(other);
        return GRAVITY_CONSTANT * mass * other.mass / Math.pow(r, 2);
    }

    private double calcPairwiseForceX(Planet other)
    {
        double r = calcDistance(other);
        return calcPairwiseForce(other) * (other.px - px) / r;
    }

    private double calcPairwiseForceY(Planet other)
    {
        double r = calcDistance(other);
        return calcPairwiseForce(other) * (other.py - py) / r;
    }

    public void setNetForce(Planet[] planets)
    {
        fx = 0;
        fy = 0;
        for (int i = 0; i < planets.length; i++)
        {
            if (!this.equals(planets[i]))
            {
                fx += calcPairwiseForceX(planets[i]);
                fy += calcPairwiseForceY(planets[i]);
            }
        }
    }

    public void update(double dt)
    {
        ax = fx / mass;
        ay = fy / mass;
        vx += dt * ax;
        vy += dt * ay;
        px += dt * vx;
        py += dt * vy;
    }

    public void draw()
    {
        StdDraw.picture(px, py, "images/" + img);
    }

    public String toString()
    {
        DecimalFormat df = new DecimalFormat("0.0000E00");
        return String.format("%11s %11s %11s %11s %11s %11s", df.format(px), df.format(py),
            df.format(vx), df.format(vy), df.format(mass), img);
    }
}