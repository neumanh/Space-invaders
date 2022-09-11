/**
 *
 */
package animation;
//

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.Sprite;

/**The Menu screen.
 *
 * @author Hadas Neuman
 * @param <T> is the returned value
 *
 */
public class MenuAnimation<T> implements Menu<T> {

    private List<String> keys;
    private List<String> choices;
    private List<T> returnValues;
    private String title;
    private Sprite background;
    private KeyboardSensor keyboard;
    private boolean stop;

    private List<Menu<T>> subMenues;

    /**A constructor.
     *
     * @param title is the title of the menu
     * @param background is the menu background
     */
    public MenuAnimation(String title, Sprite background) {
        this.title = title;
        this.background = background;
        this.keys = new ArrayList<String>();
        this.choices = new ArrayList<String>();
        this.returnValues = new ArrayList<T>();
        this.stop = false;
        this.subMenues = new ArrayList<>();
    }

    /**A constructor.
     *
     * @param title is the title of the menu
     * @param keyboard is the keyboard sensor
     */
    public MenuAnimation(String title, KeyboardSensor keyboard) {
        this.title = title;
        this.keys = new ArrayList<String>();
        this.choices = new ArrayList<String>();
        this.returnValues = new ArrayList<T>();
        this.keyboard = keyboard;
        this.background = new MenuBackground();
        this.stop = false;
        this.subMenues = new ArrayList<>();
    }


    /**
     * Sets the background.
     *
     * @param menuBackground is the background
     */
    public void setBackground(Sprite menuBackground) {
        this.background = menuBackground;
    }

    /**
     * The background of the menu.
     *
     * @return a sprite representing the background
     */
    public Sprite getBackground() {
        return this.background;
    }


    /**Draws the background of the menu.
     *
     * @param s is sprite to draw.
     * @param d is the drawing surface.
     */
    public void getBackground(Sprite s, DrawSurface d) {
        s.drawOn(d);
    }

    /**Adds a selection to the menu.
     *
     * @param key is the key
     * @param message is the message to display
     * @param returnVal is the returned value
     */
    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.choices.add(message);
        this.returnValues.add(returnVal);
        this.subMenues.add(null);
    }

    /**Gets the player choice.
     *
     * @return the player choice
     */
    @Override
    public T getStatus() {
        for (String tempKey: this.keys) {
            if (this.keyboard.isPressed(tempKey)) {
                int index = this.keys.indexOf(tempKey);
                //this.stop = true;
                return this.returnValues.get(index);
            }
        }
        //this.stop = true;
        return null;
    }


    /**Tells whether the game should continue.
     *
     * @return false
     */
    @Override
    public boolean shouldStop() {
        if (this.stop) {
            this.stop = false;
            return true;
        }
        return false;
    }

    /**Displays the menu.
     *
     * @param d the the drawing surface
     * @param dt is the change in time
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.background.drawOn(d);
        int yLocation = d.getHeight() / 4 + 100;
        d.setColor(java.awt.Color.decode("#3e9999"));
        d.drawText((d.getWidth() / 3), yLocation, this.title, 50);
        for (int i = 0; i < this.choices.size(); i++) {
            yLocation += 60;
            d.drawText(220, yLocation, this.choices.get(i), 23);
            d.drawText(540, yLocation, this.keys.get(i), 23);
        }
        this.stop = true;
    }

    /**Changing the status of the stop variable.
     *
     * @param newStop is the stop variable
     */
    public void setStop(boolean newStop) {
        this.stop = newStop;
    }

    /**Adds sub menu to the menu.
     * @param key is the key
     * @param message is the message to display
     * @param subMenu is the sub menu
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.choices.add(message);
        this.keys.add(key);
        this.returnValues.add((T) subMenu);
        this.subMenues.add(subMenu);
    }

}
