package game;
import graphics.Point;

/**
 * Class CollisionInfo.
 *
 * @author Hadas Neuman
 *
 */
public class CollisionInfo {
    private Point callPoint;
    private Collidable callObj;

    /**
     * A constructor - build a new CollisionInfo object.
     *
     *@param callPoint is the point at which the collision occurs
     *@param callObj is the collidable object involved in the collision
     */
    public CollisionInfo(Point callPoint, Collidable callObj) {
        this.callPoint = callPoint;
        this.callObj = callObj;
    }

    /**
     * Gets the point at which the collision occurs.
     *
     * @return the point at which the collision occurs.
     */
    public Point collisionPoint() {
        return this.callPoint;
    }

    /**
     * Gets the collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.callObj;
    }
}