package animation;
import biuoop.DrawSurface;

/**interface Animation.
 *
 * @author Hadas Neuman
 *
 */
public interface Animation {
    /**Draws one frame of the game.
     *
     * @param d is the draw surface
     * @param dt is the change in time
     */
    void doOneFrame(DrawSurface d, double dt);

    /**Checks if the game needs to be stopped.
     *
     * @return true is there are no more game-lives
     */
    boolean shouldStop();
    //void setStop(boolean stop);
}