package game;
import java.io.File;
import java.io.IOException;
import java.util.List;

import animation.Animation;
import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import levels.LevelInformation;

/**
 * This class is in charge of creating the differnet levels, and moving from one
 * level to the next.
 *
 * @author Hadas Neuman
 *
 */
public class GameFlow {

    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private int screenHeight = 600;
    private int screenWidth = 800;
    private GUI gui;
    private Counter score;
    private Counter lives;
    private File file;
    private HighScoresTable hst;
    // private double dt;

    /**
     * A constructor.
     */
    public GameFlow() {
        this.gui = new GUI("Arkanoid", this.screenWidth, this.screenHeight);
        this.score = new Counter(0);
        this.lives = new Counter(1);
        this.runner = new AnimationRunner(this.gui);
        this.keyboard = gui.getKeyboardSensor();

        //The high scores table

        this.file = new File("scores_table.ser");
        this.hst = new HighScoresTable(5);

        if (file.exists()) {
            try {
                hst.load(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**A constructor.
     * @param gui is the gui
     */
    public GameFlow(GUI gui) {
        this.screenHeight = 600;
        this.screenWidth = 800;
        this.keyboard = gui.getKeyboardSensor();
        this.gui = gui;

        //The high scores table

        this.file = new File("scores_table.ser");
        this.hst = new HighScoresTable(5);

        if (file.exists()) {
            try {
                hst.load(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Runs the levels.
     *
     * @param levels
     *            is the level that should run
     */
    public void runLevels(List<LevelInformation> levels) {

        helperRunLevels(levels);
    }

    /**Runs the levels.
     *
     * @param levels is the levels information
     */
    public void helperRunLevels(List<LevelInformation> levels) {

        this.score = new Counter(0);
        this.lives = new Counter(3);
        this.runner = new AnimationRunner(this.gui);

        boolean win = true;

        //boolean newGame = true;
        int roundNum = 1; //The number of rounds

        LevelInformation spaceInvadorsLevel = levels.get(0);

        Velocity alienFormationVel = new Velocity(50, 0);
        int speedIncrese = 30;


        while (true) {

            GameLevel level = new GameLevel(this.runner, this.keyboard, this.gui,
                    this.score, this.lives, spaceInvadorsLevel, alienFormationVel);

            level.setRoundNum(roundNum); //updating the number of rounds
            roundNum++;

            level.initialize();

            while ((this.lives.getValue() > 0) && (level.getBlockCounter().getValue() > 0)) {
                level.playOneTurn();
                // System.out.println(level.getBlockCounter().getValue());
            }

            if (this.lives.getValue() <= 0) {
                win = false;
                break;
            }


            // Increase the aliens formation velocity
            double dx = alienFormationVel.getDx();
            double dy = alienFormationVel.getDy();

            dx += speedIncrese;

            alienFormationVel = new Velocity(dx, dy);
        }

        int scoreRank = this.hst.getRank(this.score.getValue());
        if (scoreRank <= this.hst.size()) { // The score should enter the score table
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Enter The highest Score Table!", "What is your name?", "");
            this.hst.add(new ScoreInfo(name, this.score.getValue()));
            try {
                this.hst.save(this.file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Animation a = new EndScreen(this.score.getValue(), win);
        Animation ak = new KeyPressStoppableAnimation(this.keyboard, " ", a);
        this.runner.run(ak);

        a = new HighScoresAnimation(this.hst);
        ak = new KeyPressStoppableAnimation(this.keyboard, " ", a);
        this.runner.run(ak);
    }


    /**Gets the high score table.
     * @return the high score table
     */
    public HighScoresTable getHst() {
        return this.hst;
    }



}

