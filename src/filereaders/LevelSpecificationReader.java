package filereaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;

import biuoop.DrawSurface;
import game.Sprite;
import game.Velocity;
import gameobjects.Block;
import levels.LevelInformation;

/**Reads the levels information.
 *
 * @author Hadas Neuman
 *
 */
public class LevelSpecificationReader {


    /**Reads the levels information.
     *
     * @param reader is the file-reader
     * @return the level information
     */
    public List<LevelInformation> fromReader(java.io.Reader reader)  {

        List<LevelInformation> levels = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(reader)) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                List<String> oneLevel = new ArrayList<>();
                while ((line != null) && !(line.contains("END_LEVEL"))) { // We are in a level
                    oneLevel.add(line);
                    line = br.readLine(); // continue to the other line
                }

                LevelInformation li = getLevelInfo(oneLevel);
                levels.add(li); // Adding the new level
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return levels;
    }


    /**Gets the balls velocity.
     *
     * @param s is the string
     * @return the velocities
     */
    public List<Velocity> getBallsVelocity(String s) {
        List<Velocity> ballsVells = new ArrayList<>();
        s += " "; // Adding a terminator space
        Pattern pattern = Pattern.compile("(-?\\d+?),(-?\\d+?)\\s");
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            double dx = Double.parseDouble(matcher.group(1));
            double dy = Double.parseDouble(matcher.group(2));

            ballsVells.add(new Velocity(dx, dy));
        }
        return ballsVells;
    }

    /**Gets the level information from strings and creates LevelInformation variable.
     *
     * @param oneLevel is the level
     * @return level information
     */
    public LevelInformation getLevelInfo(List<String> oneLevel) {
        String levelName = null;
        List<Velocity> ballVelocities = new ArrayList<>();
        String bgImageName = null;
        java.awt.Color bgColor = null;
        int paddleSpeed = -1;
        int paddleWidth = -1;
        String blockDefinitionsFile = null;
        int blocksStartX = -1;
        int blocksStartY = -1;
        int rowHeight = -1;
        int numBlocks = -1;
        List<String> blockInfo = new ArrayList<>();
        List<Block> blocksArray = new ArrayList<>();

        // Parsing the level information
        for (Iterator<String> iterator = oneLevel.iterator(); iterator.hasNext();) {
            String line = iterator.next();
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }

            Pattern pattern = Pattern.compile("level_name:(.+)"); // Level name
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                levelName = matcher.group(1);
            }

            pattern = Pattern.compile("ball_velocities:(.+)"); // Level name
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                ballVelocities = this.getBallsVelocity(matcher.group(1));
            }

            pattern = Pattern.compile("background:(.+)"); // The background
            matcher = pattern.matcher(line);
            // Check whether the filling is a color or an image
            if (matcher.find()) {
                String bg = matcher.group(1);
                Pattern pattern2 = Pattern.compile("image.(.+)\\)");
                Matcher matcher2 = pattern2.matcher(bg);
                if (matcher2.find()) {
                    bgImageName = matcher2.group(1);
                } else { //this is a color
                    pattern2 = Pattern.compile("color.(.+)\\)");
                    matcher2 = pattern2.matcher(bg);
                    if (matcher2.find()) {
                        ColorsParser tempColor = new ColorsParser();
                        bgColor = tempColor.colorFromString(matcher2.group(1));
                    }
                }
            }
            // The paddle speed
            pattern = Pattern.compile("paddle_speed:(\\d+)"); // Paddle speed
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                paddleSpeed = Integer.parseInt(matcher.group(1));
            }

            // The paddle width
            pattern = Pattern.compile("paddle_width:(\\d+)"); // Paddle width
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                paddleWidth = Integer.parseInt(matcher.group(1));
            }

            // Blocks definition file
            pattern = Pattern.compile("block_definitions:(\\S+)"); // Level name
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                blockDefinitionsFile = matcher.group(1);
            }

            // The block starting x position
            pattern = Pattern.compile("blocks_start_x:(\\d+)");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                blocksStartX = Integer.parseInt(matcher.group(1));
            }

            // The block starting y position
            pattern = Pattern.compile("blocks_start_y:(\\d+)");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                blocksStartY = Integer.parseInt(matcher.group(1));
            }

            // The row height
            pattern = Pattern.compile("row_height:(\\d+)");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                rowHeight = Integer.parseInt(matcher.group(1));
            }

            // The number of blocks
            pattern = Pattern.compile("num_blocks:(\\d+)");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                numBlocks = Integer.parseInt(matcher.group(1));
            }

            pattern = Pattern.compile("START_BLOCKS");
            matcher = pattern.matcher(line);
            if (matcher.find()) {
                while (!(line.contains("END_BLOCKS")) && (line != null)) { // We are in a blocks definition
                    line = iterator.next(); // Jump to the next line
                    blockInfo.add(line); // Add to the list
                }
            }
        }
        //Creating the level information
        String updtblockDefinitionsFile = blockDefinitionsFile;

        InputStreamReader is = new InputStreamReader(
                ClassLoader.getSystemClassLoader().
                getResourceAsStream(updtblockDefinitionsFile));

        BlocksFromSymbolsFactory fact = null;
        try {
            fact = BlocksDefinitionReader.fromReader(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //finding the x and y positions and creating the blocks
        int y = blocksStartY;
        for (String s: blockInfo) {
            // Going through one line
            int x = blocksStartX;
            for (int i = 0; i < s.length(); i++) {
                // Going through each symbol
                String symbol = s.substring(i, i + 1);
                if (fact.isSpaceSymbol(symbol)) {
                    x += fact.getSpaceWidth(symbol); // Adding a space
                }
                if (fact.isBlockSymbol(symbol)) {
                    Block b = fact.getBlock(symbol, x, y);
                    b.setColumn(i);
                    blocksArray.add(b);
                    x += b.getWidth();
                }
            }
            y += rowHeight;
        }
        LevelProperties lp = new LevelProperties(levelName, bgImageName, bgColor,
                paddleSpeed, paddleWidth, numBlocks, ballVelocities);
        return createLevelInfo(lp, blocksArray);
    }

    /**Creates the level information variable.
     *
     * @param lp is level properties
     * @param blocksArray is the blocks array
     * @return the level information variable
     */
    public LevelInformation createLevelInfo(LevelProperties lp, List<Block> blocksArray) {

        LevelInformation li = new LevelInformation() {

            public int paddleWidth() {
                return lp.getPaddleWidth();
            }

            public int paddleSpeed() {
                return lp.getPaddleSpeed();
            }

            public int numberOfBlocksToRemove() {
                return lp.getNumBlocks();
            }

            public int numberOfBalls() {
                return lp.getBallsVelocity().size();
            }

            public String levelName() {
                return lp.getLevelName();
            }

            public List<Velocity> initialBallVelocities() {
                return lp.getBallsVelocity();
            }

            public Sprite getBackground() {
                Sprite sprite = new Sprite() {
                    private int firstTimeRead = 1;
                    private java.awt.Image image;

                    public void timePassed(double dt) {
                    }

                    public void drawOn(DrawSurface d) {
                        if (!(lp.getBgImageName() == null)) {
                            try {
                                if (firstTimeRead == 1) {
                                    String filename = lp.getBgImageName();
                                    /*image = new ImageIcon(ClassLoader.getSystemClassLoader().
                                    getResource("resources/" + lp.getBgImageName())).getImage();*/
                                    image = new ImageIcon(ClassLoader.getSystemClassLoader().
                                            getResource(filename)).getImage();
                                    firstTimeRead = 0;
                                    System.out.println("Load image backgroud\n");
                                }
                                d.drawImage(0, 0, image);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        } else { // This is a color
                            d.setColor(lp.getBgColor());
                            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
                        }
                    }
                };
                return sprite;
            }

            public List<Block> blocks() {
                return blocksArray;
            }
        };

        return li;
    }
}
