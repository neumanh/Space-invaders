package animation;
import biuoop.DrawSurface;

/**
 * Displays the screen in the end of the game.
 *
 * @author Hadas Neuman
 *
 */
public class EndScreen implements Animation {

    private boolean win;
    private int score;

    /**
     * A constructor.
     *
     * @param score
     *            is the score of the game
     * @param win
     *            tells if the player has wan
     */
    public EndScreen(int score, boolean win) {
        this.score = score;
        this.win = win;;
    }

    /**
     * Display the end screen.
     *
     * @param d
     *            is the drawing surface
     * @param dt
     *            is the change in time
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        String message;
        if (this.win) {
            message = "     You Win!";
        } else {
            message = "Game Over :(";
        }

        d.setColor(java.awt.Color.white);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(java.awt.Color.black);
        d.drawText((d.getWidth() / 2) - 180, d.getHeight() / 2, message, 50);
        message = "Your score is " + this.score;
        d.drawText((d.getWidth() / 2) - 130, (d.getHeight() / 2 + 50), message, 32);
    }

    /**
     * Indicates if the game should be stopped.
     *
     * @return true if the game should continue, or false otherwise
     */
    @Override
    public boolean shouldStop() {
        return false;
    }

}
