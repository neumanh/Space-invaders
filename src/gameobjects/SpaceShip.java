package gameobjects;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.GUI;
import game.Collidable;
import game.GameLevel;
import game.Sprite;
import game.Velocity;
import graphics.Point;
import graphics.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;

/**The Space ship.
 *
 * @author Hadas Neuman
 *
 */
public class SpaceShip implements Sprite, Collidable, HitNotifier {

    private Paddle paddle;
    //private biuoop.KeyboardSensor keyboard;
    private long lastShot;
    private List<HitListener> hitListeners;
    private boolean gotHit;


    /**A constructor.
     *
     * @param gui is the graphic user interface
     * @param screenWidth is the screen width
     * @param screenHeight is the screen height
     * @param padWidth is the paddle width
     * @param speed is the space ship speed
     */
    public SpaceShip(GUI gui, int screenWidth, int screenHeight, int padWidth, int speed) {
        this.paddle = new Paddle(gui, screenWidth, screenHeight, padWidth, speed);
        //this.keyboard = gui.getKeyboardSensor();
        this.lastShot = 0;
        hitListeners = new ArrayList<HitListener>();
        gotHit = false;
    }

    /**Returns the "collision shape" of the object.
     * @return the rectangle that presents the shape
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle.getCollisionRectangle();
    }

    /**Notifies the object that we collided with it at collisionPoint with a given velocity.
     * @param hitter is the hitting ball
     * @param collisionPoint is the point the the object collided with this block
     * @param currentVelocity is the current velocity of the object
     * @return null
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        this.gotHit = true;
        //return this.paddle.hit(hitter, collisionPoint, currentVelocity);
        return null;
    }

    /** Indicates the ship state.
     *
     * @return true if the ship got hit
     */
    public boolean shipGotHit() {
        return this.gotHit;
    }

    /**Draws the paddle on a given draw surface.
     * @param d is the given draw surface that the paddle should be drawn on
     */
    public void drawOn(DrawSurface d) {
        this.paddle.drawOn(d);
    }

    /**Check the keyboard sensor and moves the paddle one step.
     *
     * @param dt is the difference in time
     */
    public void timePassed(double dt) {
        this.paddle.timePassed(dt);
    }

    /**Gets the center of the space ship.
     *
     * @return the center of the space ship.
     */
    public Point getCenter() {
        Point p = this.paddle.getCollisionRectangle().getUpperLeft();
        double width = this.getCollisionRectangle().getWidth();

        Point newP = new Point(p.getX() + (width / 2), (p.getY() - 10));

        return newP;
    }

    /**
     * Adds the space ship to a game.
     *
     * @param g is a game the space ship should be added to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Removes the space ship from a game.
     *
     * @param g is a game the space ship should be added to
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
        //this.paddle.removeFromGame(g);
    }

    /**Shoots at the aliens.
     *
     * @param g is the game level
     */
    public void shoot(GameLevel g) {

        long timePassed = System.currentTimeMillis() - this.lastShot;
        long milliSecondToSleep = (long) (0.35 * 1000);
        if (timePassed > milliSecondToSleep) {
            Velocity vel = new Velocity(0, -500);
            Point p = this.getCenter();

            g.createBullet(vel, p, false);
            this.lastShot = System.currentTimeMillis(); // timing
        }
    }

    /**Adds a listener to the space ship.
     *
     * @param hl is the listener to add
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**Remove a listener from the block.
     *
     * @param hl is the listener to remove
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);

    }

    /**Notifies if a hit accrued.
     *
     * @param hitter is the hitting ball
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            Block tempBlock = new Block(this.paddle.getCollisionRectangle());
            hl.hitEvent(tempBlock, hitter);
        }
    }

}
