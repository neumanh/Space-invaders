package gameobjects;

import biuoop.DrawSurface;
import game.CollisionInfo;
import game.GameEnvironment;
import game.GameLevel;
import game.Sprite;
import game.Velocity;
import graphics.Line;
import graphics.Point;

/**
 * Ball class.
 *
 * @author HadasNeuman
 */
public class Ball implements Sprite {

    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity vel;
    private GameEnvironment ge;
    // private int counter = 0; // for debug use

    /**
     * A constructor - Creates new Ball with 2 limits.
     *
     * @param center
     *            is the center point of the ball
     * @param r
     *            is the radius of the ball
     * @param color
     *            is the color of the ball
     * @param vel
     *            is the ball velocity
     * @param ge
     *            is the game environment
     */
    public Ball(Point center, int r, java.awt.Color color, Velocity vel, GameEnvironment ge) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.vel = vel;
        this.ge = ge;
    }

    /**
     * Accessor - gets the x value of the private variable center.
     *
     * @return the x value of center
     */
    public int getX() {
        return (int) Math.round(this.center.getX());
    }

    /**
     * Accessor - gets the y value of the private variable center.
     *
     * @return the y value of center
     */
    public int getY() {
        return (int) Math.round(this.center.getY());
    }

    /**
     * Accessor - gets the size of this ball.
     *
     * @return the r value of this Ball
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Accessor - gets the velocity of the ball.
     *
     * @return the vel value of the current Ball
     */
    public Velocity getVelocity() {
        return this.vel;
    }

    /**
     * Accessor - gets the color of this ball.
     *
     * @return the color value of this Ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Set the velocity of the current Ball.
     *
     * @param v
     *            is the desired velocity
     */
    public void setVelocity(Velocity v) {
        this.vel = v;
    }

    /**
     * Set the velocity of the current Ball.
     *
     * @param dx
     *            is the desired velocity in the x axe
     * @param dy
     *            is the desired velocity in the y axe
     */
    public void setVelocity(double dx, double dy) {
        Velocity v = new Velocity(dx, dy);
        this.vel = v;
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface
     *            is the surface the ball should be drawn on
     */
    public void drawOn(DrawSurface surface) {
        int x = (int) Math.round(this.center.getX());
        int y = (int) Math.round(this.center.getY());

        surface.setColor(this.getColor());
        surface.fillCircle(x, y, this.r);
        surface.setColor(java.awt.Color.black);
        surface.drawCircle(x, y, this.r);
    }

    /**
     * Moves the ball one step according to its velocity.
     *
     * @param dt is the difference in time
     */
    public void moveOneStep(double dt) {

        // compute the ball trajectory

        Velocity newVel = this.vel.adjustToDt(dt);
        Point endPoint = newVel.applyToPoint(center);
        Point roundedEndPoint = new Point(Math.round(endPoint.getX()), Math.round(endPoint.getY()));
        Point roundedCenter = new Point(this.getX(), this.getY());

        Line traj = new Line(roundedCenter, roundedEndPoint);

        // Check if moving on this trajectory will hit anything.
        CollisionInfo collInfo = this.ge.getClosestCollision(traj);

        // If there is no hit, then move the ball to the end of the trajectory.
        if (collInfo == null) {
            this.center = endPoint;
        } else {
            // there is a hit:
            // move the ball to "almost" the hit point, but just slightly before
            // it.
            double x = collInfo.collisionPoint().getX();
            double y = collInfo.collisionPoint().getY();
            if (this.vel.getDx() < 0) {
                x++;
            } else {
                x--;
            }
            if (this.vel.getDy() < 0) {
                y++;
            } else {
                y--;
            }
            this.center = new Point(x, y);

            // notify the hit object (using its hit() method) that a collision
            // occurred.
            // update the velocity to the new velocity returned by the hit()
            // method.
            this.vel = collInfo.collisionObject().hit(this, collInfo.collisionPoint(), this.vel);
        }
    }

    /**
     * Tells the ball that the time passed, and the ball needs to move.
     *
     * @param dt is the difference in time
     */
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * Adds the ball to a game.
     *
     * @param g
     *            is a game the ball should be added to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove the ball from a game.
     *
     * @param g
     *            is a game the ball should be removed from
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }
}
