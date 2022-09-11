package filereaders;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import animation.Task;
import biuoop.GUI;
import game.GameFlow;
import levels.LevelInformation;

/**Reads the different levels sets.
 *
 * @author Hadas Neuman
 * @param <T>
 *
 */
public class LevelsSetsReader<T> {

    /**Reads the different levels sets.
     *
     * @param reader is the reader
     * @param gui is the game gui
     * @return the selections
     * @throws IOException is found a problem in opening the file
     */
    public List<MenuSelection<T>> fromReader(java.io.Reader reader, GUI gui) throws IOException {

        List<MenuSelection<T>> selections = new ArrayList<>();
        LineNumberReader lnr = new LineNumberReader(reader);

        String description = null;
        String key = null;
        String levelSetFile = null;

        String line = null;

        while ((line = lnr.readLine()) != null) {
            if ((lnr.getLineNumber() % 2) == 1) { // An odd line
                Pattern pattern = Pattern.compile("(.):(.*+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    key = matcher.group(1);
                    description = matcher.group(2);
                }
            } else { // An eve line
                levelSetFile = line;
            }

            if ((description != null) && (levelSetFile != null)) {
                //System.out.println("description: " + description + " key: " + key + " levelSetFile: " + levelSetFile);
                //String finalFileName = "resources/" + levelSetFile;
                String finalFileName = levelSetFile;

                Task<Void> playSet = new Task<Void>() {
                    //boolean shouldPlayGame = false;
                    public Void run() {
                        GameFlow gf = new GameFlow(gui);
                        LevelSpecificationReader lsr = new LevelSpecificationReader();
                        List<LevelInformation> levels = null;
                        try {
                            Reader levelsReader = new InputStreamReader(
                                    ClassLoader.getSystemClassLoader().
                                    getResourceAsStream(finalFileName));

                            levels = lsr.fromReader(levelsReader);

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        List<LevelInformation> tempLevels = levels;
                        gf.runLevels(tempLevels);
                        return null;
                    }
                };

                selections.add(new MenuSelection<T>(key, description, (T) playSet));

                // Initializing the variables
                description = null;
                key = null;
                levelSetFile = null;
            }
        }
        return selections;
    }
}
