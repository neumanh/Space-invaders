package game;
import gameobjects.Ball;
import graphics.Point;
import graphics.Rectangle;

/**
 * Collidable interface.
 *
 * @author Hadas Neuman
 */

public interface Collidable {
    /**
     *  Return the "collision shape" of the object.
     *
     * @return the object rectangle
     */
   Rectangle getCollisionRectangle();

   /**
    *  Notify the object that we collided with it at collisionPoint with
    *  a given velocity.
    *  The return is the new velocity expected after the hit (based on
    *  the force the object inflicted on us).
    *
    * @param collisionPoint is the collision point.
    * @param currentVelocity is the current ball velocity.
    * @param hitter is thr hitting ball.
    * @return the new ball velocity
    */
   Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}