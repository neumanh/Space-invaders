package game;
import java.util.ArrayList;
import java.util.List;

import graphics.Line;
import graphics.Point;
import graphics.Rectangle;

/**
 * class GameEnviroment.
 *
 * @author Hadas Neuman
 *
 */
public class GameEnvironment {

    private List<Collidable> collList;

    /**
     * A constructor - build a new game environment from a given list.
     *
     *@param collList is a list of collidable objects
     */
    public GameEnvironment(java.util.List<Collidable> collList) {
        this.collList = collList;
    }

    /**
     * A constructor - build a new game environment with an empty list.
     *
     */
    public GameEnvironment() {
        this.collList = new ArrayList<Collidable>();
    }

    /** Adds the given collidable to the environment.
     *
     *@param c is a collidable object to add to the list
     */
    public void addCollidable(Collidable c) {
        this.collList.add(c);
    }

    /** Remove the given collidable to the environment.
     *
     *@param c is a collidable object to remove to the list
     */
    public void removeCollidable(Collidable c) {
        this.collList.remove(c);
    }

    /** Calculates the closest collision that is going to occur.
     *
     *@param trajectory is movement of the object
     *@return the information about the closest collision that is going to occur, or null if there is no collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        CollisionInfo best = null;
        CollisionInfo temp = null;
        double bestDist = Double.POSITIVE_INFINITY;
        double tempDist;

        //for each collidable:
        for (Collidable currColl : this.collList) {

            //get rectangle
            Rectangle rect = currColl.getCollisionRectangle();


            //finding the closest collision point
            Point collPoint = trajectory.closestIntersectionToStartOfLine(rect);
            if (collPoint != null) {
                temp = new CollisionInfo(collPoint, currColl);
                tempDist = trajectory.start().distance(collPoint);
                if (tempDist < bestDist) {
                    best = temp;
                    bestDist = tempDist;
                }
            }

        }

        return best;
    }
}