package animation;

import java.awt.Image;

import javax.swing.ImageIcon;

import biuoop.DrawSurface;
import game.Sprite;

/**The menu background.
 *
 * @author Hadas Neuman
 *
 */
public class MenuBackground implements Sprite {

    /**Draws the background on the screen.
     *
     * @param d is the drawing surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        try {
            /*Image img = new ImageIcon(ClassLoader.getSystemClassLoader().
                    getResource("resources/images/main_menu_background.png")).getImage();*/
            Image img = new ImageIcon(ClassLoader.getSystemClassLoader().
                    getResource("images/main_menu_background.png")).getImage();

            d.drawImage(0, 0, img);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**Does nothing.
     *
     * @param dt is the change in time
     */
    @Override
    public void timePassed(double dt) {
    }

}
