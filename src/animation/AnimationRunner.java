package animation;
import biuoop.DrawSurface;
import biuoop.GUI;

/**
 * The class that runs the animation.
 *
 * @author Hadas Neuman
 *
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private biuoop.Sleeper sleeper;
    private double dt;

    /**
     * A constructor.
     *
     * @param gui
     *            is the graphical user interface
     */
    public AnimationRunner(GUI gui) {
        this.gui = gui;
        this.framesPerSecond = 60;
        this.sleeper = new biuoop.Sleeper();
        this.dt = 1 / (double) framesPerSecond;
    }

    /**
     * Runs the animation.
     *
     * @param animation
     *            is the object that should run on the screen
     */
    public void run(Animation animation) {

        // set the framePerSeconds number to 60
        int millisecondsPerFrame = 1000 / this.framesPerSecond;

        // The animation loop
        while (!animation.shouldStop()) {
            /*
             * Paddle paddle = new Paddle(this.gui, this.screenWidth,
             * this.screenHeight); paddle.addToGame(this);
             */

            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d, dt);
            //System.out.println("animation " + animation);

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }

        }
    }
}