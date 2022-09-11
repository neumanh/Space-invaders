package filereaders;

import java.awt.Color;
import java.util.List;

import game.Velocity;


/**
 *
 * @author neuman
 *
 */
public class LevelProperties {

    private String levelName;
    private String bgImageName;
    private int paddleSpeed;
    private int paddleWidth;
    private int numBlocks;
    private List<Velocity> ballsVelocity;
    private java.awt.Color bgColor;

    /** A constructor.
     * @param levelName is the level name
     * @param bgImageName is the background image
     * @param bgColor is the background color
     * @param paddleSpeed is the paddle speed
     * @param paddleWidth is the paddle width
     * @param numBlocks is the number of blocks to remove
     * @param ballsVelocity is the balls velocitys
     */
    public LevelProperties(String levelName, String bgImageName, Color bgColor,
            int paddleSpeed, int paddleWidth, int numBlocks,
            List<Velocity> ballsVelocity) {
        this.levelName = levelName;
        this.bgImageName = bgImageName;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.numBlocks = numBlocks;
        this.ballsVelocity = ballsVelocity;
        this.bgColor = bgColor;
    }

    /**Gets the level name.
     * @return the levelName
     */
    public String getLevelName() {
        return levelName;
    }

    /**Gets the level background image.
     * @return the bgImageName
     */
    public String getBgImageName() {
        return bgImageName;
    }

    /**Gets the paddle speed.
     * @return the paddleSpeed
     */
    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    /**Gets the paddle width.
     * @return the paddleWidth
     */
    public int getPaddleWidth() {
        return paddleWidth;
    }

    /**Gets the number of block.
     * @return the num_blocks
     */
    public int getNumBlocks() {
        return numBlocks;
    }

    /**Gets the balls velocity array.
     * @return the ballsVelocity
     */
    public List<Velocity> getBallsVelocity() {
        return ballsVelocity;
    }

    /**Gets the background color.
     * @return the bgColor
     */
    public java.awt.Color getBgColor() {
        return bgColor;
    }
}






