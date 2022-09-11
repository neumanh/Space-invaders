package filereaders;

import java.awt.Color;

/**Contains information about the block.
 *
 * @author Hadas Neuman
 *
 */
public class BlockInfo {

    private String symbol;
    private int width;
    private int height;
    private java.awt.Color[] colors;
    private String[] images;
    private int hitPoints;
    private java.awt.Color stroke;

    /**A constructor.
     *
     * @param symbol is the symbol of the block
     * @param width is the width
     * @param height is the height
     * @param colors is  the colors array
     * @param images is the images array
     * @param hitPoints is the hitPoints
     * @param stroke is the stroke
     */
    public BlockInfo(String symbol, int width, int height, Color[] colors,
            String[] images, int hitPoints, Color stroke) {
        this.symbol = symbol;
        this.width = width;
        this.height = height;
        this.colors = colors;
        this.images = images;
        this.hitPoints = hitPoints;
        this.stroke = stroke;
    }


    /**Gets the width.
     * @return the width
     */
    public String getSymbol() {
        return this.symbol;
    }


    /**Gets the width.
     * @return the width
     */
    public int getWidth() {
        return this.width;
    }

    /**Gets the height.
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    /**Gets the colors array.
     * @return the colors
     */
    public java.awt.Color[] getColors() {
        return this.colors;
    }

    /**Gets the images array.
     * @return the images
     */
    public String[] getImages() {
        return this.images;
    }

    /**Gets the hitPoints.
     * @return the hitPoints
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**Gets the stroke color.
     * @return the stroke
     */
    public java.awt.Color getStroke() {
        return this.stroke;
    }




}
