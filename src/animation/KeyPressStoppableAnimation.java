package animation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Decorates the different animations.
 *
 * @author Hadas Neuman
 *
 */
public class KeyPressStoppableAnimation implements Animation {

    private boolean isAlreadyPressed;
    private KeyboardSensor keyboard;
    private boolean stop;
    private Animation animation;
    private String key;

    /**A constructor.
     *
     * @param sensor is the keyboard sensor
     * @param key is the key
     * @param animation is the animation to run
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.isAlreadyPressed = true;
        this.keyboard = sensor;
        this.animation = animation;
        this.key = key;
        this.stop = false;
    }

    /**Does one frame.
     *
     * @param d is the drawing surface
     * @param dt is the difference in time
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        //System.out.println(this.animation);
        this.animation.doOneFrame(d, dt);
        if ((this.key == " ") || (this.key == "")) {
            this.key = KeyboardSensor.SPACE_KEY;
        }
        if (this.keyboard.isPressed(this.key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }

    }

    /**Indicates if the animation should stop.
     *@return true if the animation should continue
     */
    @Override
    public boolean shouldStop() {
        if (this.stop) {
            this.stop = false;
            return true;
        }
        return false;
    }
}