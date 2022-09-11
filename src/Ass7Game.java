

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import animation.Animation;
import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import animation.MenuAnimation;
import animation.ShowHiScoresTask;
import animation.Task;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import filereaders.LevelSpecificationReader;
import game.GameFlow;
import levels.LevelInformation;

/**The Game.
 *
 * @author Hadas Neuman
 *
 */
public class Ass7Game {

    private int screenWidth = 800;
    private int screenHeight = 600;

    /**Runs the game.
     * @param args is string arguments from the user
     */
    public static void main(String[] args) {

        Ass7Game ass6game = new Ass7Game();
        String fileName = null;

        fileName = "definitions/space_invadores_definitions.txt";

        try {
            ass6game.showMenu(fileName);
        } catch (IOException e) {
            System.out.println("Problem with the file " + fileName);
            e.printStackTrace();
        }
    }


    /**Shows the menu.
     * @param fileName is the file name
     * @throws IOException if can't open the high score table file
     */
    public void showMenu(String fileName) throws IOException {

        GUI gui = new GUI("Arkanoid", this.screenWidth, this.screenHeight);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui);

        GameFlow gf = new GameFlow(gui);

        //Creating the tasks
        MenuAnimation<Task<Void>> menu = new MenuAnimation<Task<Void>>("Main Menu", keyboard);

        Task<Void> quit = new Task<Void>() {
            public Void run() {
                System.exit(0);
                return null;
            }
        };

        Task<Void> playSet = new Task<Void>() {
            //boolean shouldPlayGame = false;
            public Void run() {
                GameFlow gf = new GameFlow(gui);
                LevelSpecificationReader lsr = new LevelSpecificationReader();
                List<LevelInformation> levels = null;
                try {
                    Reader levelsReader = new InputStreamReader(
                            ClassLoader.getSystemClassLoader().
                            getResourceAsStream(fileName));

                    levels = lsr.fromReader(levelsReader);

                } catch (Exception e) {
                    System.out.println("Problam found trying to read levels from the file " + fileName + "\n");
                    e.printStackTrace();
                }
                List<LevelInformation> tempLevels = levels;
                gf.runLevels(tempLevels);
                return null;
            }
        };

        Animation a = new HighScoresAnimation(gf.getHst());
        Animation ak = new KeyPressStoppableAnimation(keyboard, " ", a);
        ShowHiScoresTask highScroeTask = new ShowHiScoresTask(runner, ak);

        menu.addSelection("s", "Start Game", playSet);
        menu.addSelection("h", "High Scores", highScroeTask);
        menu.addSelection("q", "Quit", quit);

        while (true) {
            runner.run(menu);

            Task<Void> task = menu.getStatus();
            if (!(task == null)) {
                task.run();
                menu.setStop(false);
            }
            highScroeTask.updateScores(gf.getHst(), keyboard);
        }

    }
}
