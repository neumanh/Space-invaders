package game;
import graphics.Point;

/**
 * Velocity class.
 *
 * @author HadasNeuman
 */
public class Velocity {

    private double dx;
    private double dy;

    /**
     * A constructor - Creates new Velocity.
     *
     * @param dx is the change in position on the `x` axe
     * @param dy is the change in position on the `y` axe
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * An accessor - Gets the dx.
     *
     * @return the dx of the current variable
     */
    public double getDx() {
        return dx;
    }

    /**
     * An accessor - Gets the dy.
     *
     * @return the dy of the current variable
     */
    public double getDy() {
        return dy;
    }



    /**
     * An accessor - Gets the dx.
     * @param dt is the difference in time
     * @return the dx of the current variable
     */
    public double getDxWithDt(double dt) {
        return (dx * dt);
    }

    /**
     * An accessor - Gets the dy.
     * @param dt is the difference in time
     * @return the dy of the current variable
     */
    public double getDyWithDt(double dt) {
        return (dy * dt);
    }
    /**
     * Creats new object from an angle and a speed.
     *
     * @param angle is the angle of the movement
     * @param speed is the speed of the movement
     *
     * @return the new valocity
     */
    public Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx1 = Math.cos(Math.toRadians(angle)) * speed;
        double dy1 = Math.sin(Math.toRadians(angle)) * speed;

        dy1 *= -1;

        return new Velocity(dx1, dy1);
    }

    /**
     * Apply a change in the position of a given point.
     *
     * @param p is a point in an initial position
     * @return new point with new position according to the current velocity
     */
    public Point applyToPoint(Point p) {
        Point np = new Point(p.getX() + this.dx, p.getY() + this.dy);
        return np;
    }

    /**Adjusts to velocity to the change in time.
     *
     * @param dt is the change in time
     * @return the updated velocity
     */
    public Velocity adjustToDt(double dt) {
        return new Velocity((this.dx * dt), (this.dy * dt));
    }
}