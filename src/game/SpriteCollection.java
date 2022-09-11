package game;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import gameobjects.Ball;

/**
 * SpriteCollection class.
 *
 * @author Hadas Neuman
 */
public class SpriteCollection {

    private List<Sprite> spriteList;

    /**
     * A constructor - build a new Sprite Collection from a given list.
     *
     * @param spriteList
     *            is a list of sprite objects
     */
    public SpriteCollection(java.util.List<Sprite> spriteList) {
        this.spriteList = spriteList;
    }

    /**
     * A constructor - build a new Sprite Collection with an empty list.
     *
     */
    public SpriteCollection() {
        this.spriteList = new ArrayList<Sprite>();
    }

    /**
     * Adds the given sprite to the collection.
     *
     * @param s
     *            is a sprite object to add to the list
     */
    public void addSprite(Sprite s) {
        this.spriteList.add(s);
    }

    /**
     * Remove the given sprite to the collection.
     *
     * @param s
     *            is a sprite object to remove to the list
     */
    public void removeSprite(Sprite s) {
        this.spriteList.remove(s);
    }

    /**
     * Call timePassed on all sprites.
     * @param dt is the difference in time
     */
    public void notifyAllTimePassed(double dt) {
        // Make a copy of the sprites before iterating over them.
        List<Sprite> sl = new ArrayList<Sprite>(this.spriteList);
        // for each sprite
        for (Sprite s : sl) {
            s.timePassed(dt);
        }
    }

    /**
     * Call drawOn(d) on all sprites.
     *
     * @param d
     *            is the drawing surface
     */
    public void drawAllOn(DrawSurface d) {
        // for each sprite
        for (Sprite s : this.spriteList) {
            s.drawOn(d);
        }
    }

    /**Removes all balls from the game.
     *
     * @param game is the running level
     */
    public void removeAllBalls(GameLevel game) {
        List<Sprite> tempSpriteList = new ArrayList<>(this.spriteList);
        for (Sprite sprite : tempSpriteList) {
            if (sprite instanceof Ball) {
                //System.out.println("The Sprite is a ball!:)");
                Ball ballToRemove = (Ball) sprite;
                ballToRemove.removeFromGame(game);
                Counter tempCaunter = game.getBallsCounter();
                tempCaunter.decrease(1);
                game.setBallCounter(tempCaunter);
            }
        }
    }
}