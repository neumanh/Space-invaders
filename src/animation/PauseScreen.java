package animation;
import biuoop.DrawSurface;

/**
 * Pauses the game.
 *
 * @author Hadas Neuman
 *
 */
public class PauseScreen implements Animation {

    /**
     * A constructor.
     *
     */
    public PauseScreen() {
    }

    /**
     * Display the paused screen.
     *
     * @param d
     *            is the drawing surface
     * @param dt is the difference in time
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(java.awt.Color.decode("#67BBff"));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(java.awt.Color.black);
        d.drawText(10, d.getHeight() / 2, "                         Paused -- press space to continue", 32);
    }

    /**
     * Indicates if the game should be stopped.
     *
     * @return true if the game should continue or false if not.
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}