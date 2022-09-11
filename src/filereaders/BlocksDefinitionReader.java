package filereaders;

import java.io.BufferedReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gameobjects.Block;
import graphics.Point;

/**Reads a block-definitions file and returns a BlocksFromSymbolsFactory object.
 *
 * @author Hadas Neuman
 *
 */
public class BlocksDefinitionReader {

    /**Reads a block-definitions file and returns a BlocksFromSymbolsFactory object.
     *
     * @param reader is the file-reader
     * @return BlocksFromSymbolsFactory object
     * @throws Exception if there is a problem with the file
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) throws Exception {
        BlocksFromSymbolsFactory fact = new BlocksFromSymbolsFactory();

        String defaults = new String();

        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                line += " "; // Adding a terminating space
                Pattern pattern = Pattern.compile("^#"); // Comment
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    continue;
                }
                pattern = Pattern.compile("default(.*)"); // default values for blocks
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    defaults = matcher.group(1);
                }

                pattern = Pattern.compile("bdef symbol:");
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    if (!(defaults == null)) {
                        line = line + " " + defaults; // adding the defaults parameters
                    }

                    BlockInfo bi = (new CreateBlockFromBdef()).createBlock(line);
                    BlockCreator bc = new BlockCreator() {
                        public Block create(int xpos, int ypos) {
                            Block b = new Block(new Point(xpos, ypos), bi.getWidth(),
                                    bi.getHeight(), bi.getColors(), bi.getImages(),
                                    bi.getHitPoints(), bi.getStroke());
                            return b;
                        }
                    };
                    fact.addBlockCreator(bi.getSymbol(), bc);
                }

                pattern = Pattern.compile("sdef symbol:(.)\\s+width:(\\d+)");
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    fact.addspacerWidths(matcher.group(1), Integer.parseInt(matcher.group(2)));
                }

            }
        }
        return fact;
    }
}
