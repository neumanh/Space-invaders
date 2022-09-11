package game;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The high-scores table.
 *
 * @author Hadas Neuman
 *
 */
public class HighScoresTable implements Serializable {

    private List<ScoreInfo> scores;
    private int size;

    /**
     * Create an empty high-scores table with the specified size.
     *
     * @param size
     *            is the size of the table
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.scores = new ArrayList<ScoreInfo>(size);
    }

    /**
     * Create an empty high-scores table with the specified size.
     *
     */
    public HighScoresTable() {
        this.scores = new ArrayList<ScoreInfo>();
    }

    /**
     * Adds a high-score.
     *
     * @param score
     *            is the score to add.
     */
    public void add(ScoreInfo score) {

        if (this.scores.size() < this.size) { // the table is not full
            this.scores.add(score);
        } else { // the table is full
            // checks if the score is higher than the lowest score in our list
            if (score.compareTo(this.scores.get(this.size - 1)) > 0) {
                this.scores.add(score);
                Collections.sort(this.scores);
                Collections.reverse(this.scores);
                this.scores.remove(this.scores.size() - 1); // removing the last and
                // lowest score
            }
        }
        Collections.sort(this.scores);
        Collections.reverse(this.scores);
    }

    /**
     * Return table size.
     *
     * @return the size of the table
     */
    public int size() {
        return this.size;
    }

    /**
     * Return the current high scores.
     *
     * @return the current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * Returns the rank of the current score.
     *
     * @param score
     *            the score to check its rank
     * @return the rank of the score
     */
    public int getRank(int score) {
        int rank = this.size + 1;

        if (this.scores.size() < this.size) {
            // Table not full yet - real rank.
            rank = 0;
        } else {
            // Check if the score bigger than the last one
            if (this.scores.get(this.size - 1).getScore() < score) {
                // real rank.
                rank = 0;
            }
        }

        if (rank == 0) {
            for (ScoreInfo tempScore : this.scores) {

                if (score < tempScore.getScore()) {
                    rank++;
                }
                /*
                 * for (int i = 0; i < this.size; i++) { ScoreInfo tempScore =
                 * this.scores.get(i); if (score > tempScore.getScore()) { rank = i + 1; } }
                 */
            }
        }

        return rank;
    }

    /**
     * Clears the table.
     *
     */
    public void clear() {
        this.scores = new ArrayList<ScoreInfo>(size);
    }

    /**
     * Loads table data from file.
     *
     * @param filename is a file name
     * @throws IOException if found a problem
     */
    public void load(File filename) throws IOException {

        HighScoresTable newTable = null;
        try {
            newTable = loadFromFile(filename);
        } catch (IOException e) {
            System.err.println("Failed to load table from file:" + filename);
        }
        this.scores = newTable.getHighScores();
        this.size = newTable.size;

    }

    /**
     * Saves table data to the specified file.
     *
     * @param filename
     *            is the file name
     * @throws IOException if found a problem
     */
    public void save(File filename) throws IOException {

        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**Reads a table from file.
     *
     * @param filename is the file name
     * @return the table from the file
     * @throws IOException if found a problem
     */
    public static HighScoresTable loadFromFile(File filename) throws IOException {

        HighScoresTable newTable = new HighScoresTable();
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
            newTable = (HighScoresTable) objectInputStream.readObject();
            return newTable;
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            return newTable;

        } catch (ClassNotFoundException e) { // The class in the stream is
            // unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);
            return newTable;

        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return newTable;

        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }

    }

    /**
     * Creates a String that represents the variable.
     *
     * @return a String that represents the variable
     */
    @Override
    public String toString() {
        String str = "\tBest Scores\n\n";
        for (ScoreInfo tempScore : this.scores) {
            str += tempScore.toString();
        }
        return str;
    }

}