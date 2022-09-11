package filereaders;

import java.util.Map;
import java.util.TreeMap;

import gameobjects.Block;

/**Creates Blocks according to the information.
 *
 * @author Hadas Neuman
 *
 */
public class BlocksFromSymbolsFactory {

    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;


    /**A constructor.
     *
     *
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new TreeMap<String, Integer>();
        this.blockCreators = new TreeMap<String, BlockCreator>();
    }


    /**Returns true if 's' is a valid space symbol.
     *
     * @param s space symbol
     * @return true if 's' is a valid space symbol or false otherwise
     */
    public boolean isSpaceSymbol(String s) {
        return (this.spacerWidths.containsKey(s));
    }

    /**Returns true if 's' is a valid block symbol.
     *
     * @param s is block symbol.
     * @return true if 's' is a valid block symbol or false otherwise
     */
    public boolean isBlockSymbol(String s) {
        return (this.blockCreators.containsKey(s));
    }

    /**Adds spacerWidths to the map.
     *
     * @param s is the key symbol
     * @param i is the spacerWidths
     */
    public void addspacerWidths(String s, Integer i) {
        this.spacerWidths.put(s, i);
    }

    /**Adds BlockCreator to the map.
     *
     * @param s is the key symbol
     * @param b is the BlockCreator
     */
    public void addBlockCreator(String s, BlockCreator b) {
        this.blockCreators.put(s, b);
    }

    /**Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s spacer-symbol
     * @return the width
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**Return the Creator that creates a block according to the definitions associated.
     *
     * @param s is the definition
     * @param x is the x position
     * @param y is the y position
     * @return a block created according to the string
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }
}
