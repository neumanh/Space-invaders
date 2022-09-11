package animation;
import java.awt.Image;

import javax.swing.ImageIcon;

import biuoop.DrawSurface;
import game.HighScoresTable;
import game.ScoreInfo;

/**
 * Shoe the high score table on the screen.
 *
 * @author Hadas Neuman
 *
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable scores;


    /**
     * A constructor.
     *
     * @param scores
     *            is the scores table
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    /**
     * Display the scores on the screen.
     *
     * @param d
     *            is the drawing surface
     * @param dt
     *            is the difference in time
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        int yLocation = d.getHeight() / 4;

        /* Get cuuent directory */
        //String currentDir = System.getProperty("user.dir");
        //System.out.println("Current dir using System:" +currentDir);

        /* Draw background */
        try {
            Image img = new ImageIcon(ClassLoader.getSystemClassLoader().
                    getResource("images/high_score_background.png")).getImage();

            d.drawImage(0, 0, img);

            //scores
            d.setColor(java.awt.Color.decode("#3e9999"));
            for (ScoreInfo tempScore : this.scores.getHighScores()) {
                yLocation += 60;
                // d.drawText(10, yLocation, tempScore.toString(), 18);
                d.drawText(180, yLocation, tempScore.getName(), 23);
                d.drawText(580, yLocation, String.valueOf(tempScore.getScore()), 23);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**Indicates if the animation should stop.
     * @return true if the animation should continue
     */
    public boolean shouldStop() {
        return false;
    }
}