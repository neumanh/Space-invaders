package animation;
import biuoop.DrawSurface;
import game.SpriteCollection;

/**The count-down animation that appears before the game starts.
 *
 * @author Hadas Neuman
 *
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private boolean stop;
    private long sleepsFor;

    /**A constructor.
     *
     * @param numOfSeconds is the number of seconds
     * @param countFrom is the number to count from
     * @param gameScreen is the game screen to show
     */
    public CountdownAnimation(double numOfSeconds,
            int countFrom,
            SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.sleepsFor = (long) (1000 * (1000 * this.numOfSeconds) / (1000 * this.countFrom));
        this.stop = false;
    }

    /**Displays the count-down screen.
     *
     * @param d the the drawing surface
     * @param dt is the change in time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        biuoop.Sleeper sleeper = new biuoop.Sleeper();
        String str;
        int x = d.getWidth() / 2 - 30;

        this.gameScreen.drawAllOn(d);
        d.setColor(java.awt.Color.magenta);

        if (this.countFrom > 0) {
            //System.out.println("doOneFrame " + this.countFrom);
            str = String.valueOf(this.countFrom);
            this.countFrom--;
        } else if (this.countFrom == 0) {
            //System.out.println("doOneFrame Go!");
            str = "Go!";
            this.countFrom--;
            x -= 100;
        } else {
            //System.out.println("doOneFrame after Go!");
            str = "";
            this.stop = true;
        }
        if (str != "") {
            d.setColor(java.awt.Color.magenta);
            d.drawText(x, (d.getHeight() / 2), str, 150);
        }
        sleeper.sleepFor(this.sleepsFor);
    }

    /**Indicates if the game should be stopped.
     *
     * @return true if the game should continue or false if not.
     */
    public boolean shouldStop() {
        return this.stop;
    }

    /**Sets the stop variable.
     *
     * @param tmpStop is the stop variable
     */
    public void setStop(boolean tmpStop) {
        this.stop = tmpStop;

    }

}