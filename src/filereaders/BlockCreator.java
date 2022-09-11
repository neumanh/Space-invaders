package filereaders;

import gameobjects.Block;

/**The Block creator interface.
 *
 * @author Hadas Neuman
 *
 */
public interface BlockCreator {

    /**Creates a block at the specified location.
     *
     * @param xpos is the x position
     * @param ypos is the y position
     * @return the new block
     */
    Block create(int xpos, int ypos);
}