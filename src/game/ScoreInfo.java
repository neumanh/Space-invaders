package game;
import java.io.Serializable;

/**
 * Represents one line in the score table and hold a name and a score.
 *
 * @author Hadas Neumanh
 *
 */
public class ScoreInfo implements Comparable<ScoreInfo>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String name;
    private int score;

    /**
     * A constructor.
     *
     * @param name
     *            is the name
     * @param score
     *            is the score
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Gets the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the score of the player.
     *
     * @return the score of the player
     */
    public int getScore() {
        return this.score;
    }

    /**Creates a String that represents the variable.
     *
     * @return a String that represents the variable
     */
    public String toString() {
        return (this.name + "\t\t\t" + this.score + "\n");
    }

    /**
     * Compares between to objects.
     *
     * @param other
     *            is another object
     *
     *  @return 1 if this is bigger, -1 is smaller and 0 if equals
     */

    public int compareTo(ScoreInfo other) {
        if (this.score == other.score) {
            return 0;
        } else if ((this.score) > other.score) {
            return 1;
        } else {
            return -1;
        }
    }


}