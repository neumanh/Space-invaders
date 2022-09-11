package animation;

import biuoop.KeyboardSensor;
import game.HighScoresTable;

/**The Show High Scores Task.
 *
 * @author Hadas Neuman
 *
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation animation;

    /**A constructor.
     * @param runner is the animation runner
     * @param hst is the high score table
     * @param keyboard is the keyboard
     */
    public ShowHiScoresTask(AnimationRunner runner, HighScoresTable hst, KeyboardSensor keyboard) {
        this.runner = runner;
        this.animation = new KeyPressStoppableAnimation(keyboard, " ", (new HighScoresAnimation(hst)));
    }

    /**A constructor.
     *
     * @param runner is the animation runner
     * @param animation is the animation to run
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation animation) {
        this.runner = runner;
        this.animation = animation;
    }

    /**Runs the task.
     * @return null
     */
    public Void run() {
        this.runner.run(this.animation);
        return null;
    }

    /**Updates the table.
     *
     * @param newHst is the new high score table
     * @param keyboard is the keyboard sensor
     */
    public void updateScores(HighScoresTable newHst, KeyboardSensor keyboard) {
        this.animation = new KeyPressStoppableAnimation(keyboard, " ", (new HighScoresAnimation(newHst)));
    }
}