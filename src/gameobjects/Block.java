package gameobjects;

import java.io.File;
//import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import biuoop.DrawSurface;
import game.Collidable;
import game.GameLevel;
import game.Sprite;
import game.Velocity;
import graphics.Point;
import graphics.Rectangle;
import listeners.HitListener;
import listeners.HitNotifier;

/**The block class.
 *
 * @author Hadas Neuman
 *
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private java.awt.Color[] colors;
    private File[] filesNames;
    private String[] strFilesName;
    private int hitPoints;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private java.awt.Color stroke;
    private java.awt.Image backgroundImage;
    private int firstTimeRead = 1;
    private int column;

    /** A constructor - creates new block from location parameters, color and number of hits.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param colors is the block's colors
     * @param hitPoints is the number of hits
     */
    public Block(Point upperLeft, double width, double height, java.awt.Color[] colors, int hitPoints) {
        this(upperLeft, width, height, colors, hitPoints, null);
    }

    /** A constructor - for space ship remover use.
     *
     * @param rect is the rectangle.
     */
    public Block(Rectangle rect) {
        this.rect = rect;
    }

    /**A constructor with one color.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param color is the block's colors
     * @param hitPoints is the number of hits
     * @param stroke is the frame color
     */
    public Block(Point upperLeft, double width, double height, java.awt.Color color,
            int hitPoints, java.awt.Color stroke) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
        this.colors = new java.awt.Color[hitPoints];
        //filling the colors array with one color
        for (int i = 0; i < hitPoints; i++) {
            this.colors[i] = color;
        }
        this.filesNames = null;
        this.stroke = stroke;
    }

    /**A constructor with one file name.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param fileName is the image file name
     * @param hitPoints is the number of hits
     * @param stroke is the frame color
     */
    public Block(Point upperLeft, double width, double height, File fileName, int hitPoints, java.awt.Color stroke) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
        this.filesNames = new File[hitPoints];
        //filling the file array with one color
        for (int i = 0; i < hitPoints; i++) {
            this.filesNames[i] = fileName;
        }
        this.colors = null;
        this.stroke = stroke;
    }

    /**A constructor with one color.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param color is the block's colors
     * @param hitPoints is the number of hits
     */
    public Block(Point upperLeft, double width, double height, java.awt.Color color, int hitPoints) {
        this(upperLeft, width, height, color, hitPoints, null);
    }

    /**A constructor with one file.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param file is the image file
     * @param hitPoints is the hit points
     */
    public Block(Point upperLeft, double width, double height, File file, int hitPoints) {
        this(upperLeft, width, height, file, hitPoints, null);
    }

    /** A constructor with files array.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param fileNames is the image file name
     * @param hitPoints is the number of hits
     */
    public Block(Point upperLeft, double width, double height, File[] fileNames, int hitPoints) {
        this(upperLeft, width, height, fileNames, hitPoints, null);

    }

    /**A constructor.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param colors is the block's colors
     * @param hitPoints is the number of hits
     * @param stroke is the stroke line color
     */
    public Block(Point upperLeft, double width, double height,
            java.awt.Color[] colors, int hitPoints, java.awt.Color stroke) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
        this.filesNames = null;
        this.stroke = stroke;
        this.colors = colors;
    }

    /** A constructor with colors and images array.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param colors is the block's colors
     * @param images is the block's images
     * @param hitPoints is the number of hits
     * @param stroke is the stroke line color
     */
    public Block(Point upperLeft, double width, double height, java.awt.Color[] colors,
            String[] images, int hitPoints, java.awt.Color stroke) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
        this.filesNames = null;
        this.stroke = stroke;
        this.strFilesName = images;
        File[] files = null;
        if (!(images[0] == null)) {
            //creating the files from the strings
            files = new File[images.length];
            for (int i = 0; i < images.length; i++) {
                files[i] = new File(images[i]);
            }
        }
        this.colors = colors;
        this.filesNames = files;

    }

    /**A copy constructor with colors and images array.
     *
     * @param other is another block
     */
    public Block(Block other) {
        this.rect = new Rectangle(other.rect);
        this.hitPoints = other.getHitPoints();
        this.hitListeners = new ArrayList<HitListener>();
        this.filesNames = other.getFilesNames();
        this.stroke = other.getStroke();
        this.colors = other.colors;
        this.filesNames = other.filesNames;
        this.strFilesName = other.getStrFilesName();
        this.column = other.getColumn();

    }

    /** A constructor with files array.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param filesNames is the names of the images
     * @param hitPoints is the number of hit points
     * @param stroke is the stroke line color
     */
    public Block(Point upperLeft, double width, double height, File[] filesNames,
            int hitPoints, java.awt.Color stroke) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
        this.colors = null;
        this.stroke = stroke;
        this.filesNames = filesNames;
    }


    /**A constructor with strings array representing files name.
     *
     * @param upperLeft is the upper left point
     * @param width is the width of the rectangle
     * @param height is the width of the rectangle
     * @param strFileNames is the names of the images
     * @param hitPoints is the number of hit points
     * @param stroke is the stroke line color
     */
    public Block(Point upperLeft, double width, double height,
            String[] strFileNames, int hitPoints, java.awt.Color stroke) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.hitPoints = hitPoints;
        this.hitListeners = new ArrayList<HitListener>();
        this.colors = null;
        this.stroke = stroke;
        this.strFilesName = strFileNames;

        //creating the files from the strings
        File[] files = new File[strFileNames.length];

        for (int i = 0; i < strFileNames.length; i++) {
            files[i] = new File(strFileNames[i]);
        }

        this.filesNames = files;
    }

    /**A getter - find the "collision shape" of the object.
     * @return the rectangle that presents the shape
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**A getter - find the hit points of the block.
     * @return the rectangle that presents the shape
     */
    public int getHitPoints() {
        return this.hitPoints;
    }
    /**Notifies the object that we collided with it at collisionPoint with a given velocity.
     * @param collisionPoint is the point the the object collided with this block
     * @param currentVelocity is the current velocity of the object
     * @param hitter is the hitting ball
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {

        //finding the block corners
        double x1 = this.rect.getUpperLeft().getX(); // Left
        double x2 = x1 + this.rect.getWidth();       // Right
        double y1 = this.rect.getUpperLeft().getY(); // Top
        double y2 = y1 + this.rect.getHeight();      // Bottom

        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();

        //check if the collision point is on the horizontal borders
        if (((collisionPoint.getX() == x1) && (0 < newDx)) || ((collisionPoint.getX() == x2) && (newDx < 0))) {
            newDx *= (-1);
        }
        //check if the collision point is on the vertical borders
        if (((collisionPoint.getY() == y1) && (0 < newDy)) || ((collisionPoint.getY() == y2) && (newDy < 0))) {
            newDy *= (-1);
        }

        this.notifyHit(hitter);

        //Decreasing the hit points
        if (0 < this.hitPoints) {
            this.hitPoints--;
            this.firstTimeRead = 1;

        }


        //this.phl.hitEvent(this, hitter);
        return new Velocity(newDx, newDy);
    }

    /**Draw the block on a given draw surface.
     * @param d is the draw surface
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();

        int colorPoint;
        if (this.hitPoints == 0) { //the borders
            colorPoint = 0;
        } else {
            //colorPoint = this.colors.length - (this.hitPoints);
            colorPoint = this.hitPoints - 1;
            //System.out.println("color Points: " + colorPoint + "color: " + this.colors[colorPoint]);

        }
        if (!(this.colors[0] == null)) { //fill with colors
            d.setColor(this.colors[colorPoint]);
            d.fillRectangle(x, y, width, height);
        } else { // fill with image
            String filename = this.strFilesName[colorPoint];
            try {
                if (firstTimeRead == 1) { //prevent redundant loading

                    this.backgroundImage = new ImageIcon(ClassLoader.getSystemClassLoader().
                            getResource(filename)).getImage();

                    this.firstTimeRead = 0;
                }

                // draw image
                d.drawImage(this.rect.getUpperLeft().getIntX(),
                        this.rect.getUpperLeft().getIntY(), this.backgroundImage);

            } catch (Exception ex) {
                System.out.println("Could not open the image file: " + filename);
                ex.printStackTrace();
            }
        }

        //drawing the frame
        if (!(this.stroke == null)) {
            d.setColor(this.stroke);
            d.drawRectangle(x, y, width, height);
        }
    }

    /**Tells the block that the time passed.
     *
     * @param dt is the difference in time
     */
    public void timePassed(double dt) {
    }

    /**
     * Adds the block to a game.
     *
     * @param g is a game the block should be added to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove the block from a game.
     *
     * @param game is a game the block should be removed from
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**Notifies if a hit accrued.
     *
     * @param hitter is the hitting ball
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**Adds a listener to the block.
     *
     * @param hl is the listener to add
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**Remove a listener from the block.
     *
     * @param hl is the listener to remove
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);

    }

    /**Gets the colors array.
     * @return the colors
     */
    public java.awt.Color[] getColors() {
        return colors;
    }


    /**Gets the files array.
     * @return the filesNames
     */
    public File[] getFilesNames() {
        return filesNames;
    }

    /**Gets the stroke of the block.
     * @return the stroke
     */
    public java.awt.Color getStroke() {
        return stroke;
    }

    /**Gets the width of the block.
     *
     * @return the width
     */
    public int getWidth() {
        return (int) this.rect.getWidth();
    }

    /**Gets the files name.
     * @return the strFilesName
     */
    public String[] getStrFilesName() {
        return strFilesName;
    }

    /** Updates the X location of the block.
     *
     * @param newX is the new X location
     */
    public void updateX(int newX) {
        this.rect.setX(newX);
    }

    /** Updates the Y location of the block.
     *
     * @param newY is the new Y location
     */
    public void updateY(int newY) {
        this.rect.setY(newY);
    }

    /**Gets the column of the block.
     * @return the column
     */
    public int getColumn() {
        return this.column;
    }

    /**Sets the column of the block.
     * @param newColumn the column to set
     */
    public void setColumn(int newColumn) {
        this.column = newColumn;
    }

}