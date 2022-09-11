package game;
import biuoop.DrawSurface;

/**
 * Sprite interface.
 *
 * @author Hadas Neuman
 */

public interface Sprite {
    /**
     * Draws the sprite to the screen.
     *
     * @param d
     *            is the surface the ball should be drawn on
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     *
     * @param dt is the difference in time
     */
    void timePassed(double dt);
}