package gameobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import biuoop.DrawSurface;
import game.GameLevel;
import game.Sprite;
import game.Velocity;
import graphics.Point;
import graphics.Rectangle;

/**The aliens formation.
 *
 * @author Hadas Neuman
 *
 */
public class AliensFormation implements Sprite /*implements Collidable, Sprite, HitNotifier*/ {

    private Rectangle rect;
    private List<Block> aliens;
    private Velocity vel;
    private Velocity initialVel;
    private int nextLine;
    private Point initialLocation;
    private long lastShot;
    private int maxY;
    private double speedIncrese;


    /**A constructor.
     *
     */
    public AliensFormation() {

        this.nextLine = 20;
        this.aliens = new ArrayList<>();
        this.lastShot = 0;

        //TEMP
        this.vel = new Velocity(50, 0);
        this.initialVel = vel;

        this.maxY = 660;

        //this.speedIncrese = 1.3;
        this.speedIncrese = 10;


    }

    /**A constructor.
     * @param vel is the initial velocity of the formation
     *
     */
    public AliensFormation(Velocity vel) {

        this.nextLine = 20;
        this.aliens = new ArrayList<>();
        this.lastShot = 0;

        this.vel = vel;
        this.initialVel = vel;

        this.maxY = 660;

        //this.speedIncrese = 1.3;
        this.speedIncrese = 10;

    }

    /**A constructor.
     *
     * @param aliens is the aliens (=blocks) list
     * @param vel is the initial velocity of the formation
     */
    public AliensFormation(List<Block> aliens, Velocity vel) {
        this.aliens = aliens;
        this.vel = vel;
        this.initialVel = vel;
        this.lastShot = 0;

        this.nextLine = 20;

        this.maxY = 660;

        int width = this.findMostRightPos() - this.findMostLeftPos();
        int hight = this.findBottomPos() - this.findTopPos();

        Point upperLeft = new Point(this.findMostLeftPos(), this.findTopPos());
        this.initialLocation = new Point(this.findMostLeftPos(), this.findTopPos());

        this.rect = new Rectangle(upperLeft, width, hight);

        this.speedIncrese = 10;
        //this.speedIncrese = 1.3;
        //this.rect = new Rectangle(new Point(10, 50), width, hight);
    }

    /**Sets the velocity.
     *
     * @param newVel is the new velocity
     */
    public void setVelocity(Velocity newVel) {
        this.vel = newVel;
    }

    /**Updates the rectangle to the start location.
     *
     */
    public void toTheStart() {
        int currentX = this.findMostLeftPos();
        int currentY = this.findTopPos();

        int xDelta = this.initialLocation.getIntX() - currentX;
        int yDelta = this.initialLocation.getIntY() - currentY;


        for (Block tempAlien: this.aliens) {
            int currX = tempAlien.getCollisionRectangle().getUpperLeft().getIntX();
            int currY = tempAlien.getCollisionRectangle().getUpperLeft().getIntY();

            tempAlien.updateX(currX + xDelta);
            tempAlien.updateY(currY + yDelta);

        }
        this.updateRect();
        this.vel = this.initialVel;

    }

    /**Updates the rectangle according to the aliens.
     *
     */
    public void updateRect() {
        int width = this.findMostRightPos() - this.findMostLeftPos();
        int hight = this.findBottomPos() - this.findTopPos();

        Point upperLeft = new Point(this.findMostLeftPos(), this.findTopPos());

        this.rect = new Rectangle(upperLeft, width, hight);
    }

    /**Gets the most right x location.
     *
     * @return the most right x location.
     */
    public int findMostRightPos() {
        int mostRight = 0;

        for (Block alien : this.aliens) {
            int currX = alien.getCollisionRectangle().getUpperLeft().getIntX()
                    + (int) (alien.getCollisionRectangle().getWidth());
            if (currX > mostRight) {
                mostRight = currX;
            }
        }

        return mostRight;
    }

    /**Gets the most left x location.
     *
     * @return the most left x location.
     */
    public int findMostLeftPos() {
        int mostLeft = Integer.MAX_VALUE;

        for (Block alien : this.aliens) {
            int currX = alien.getCollisionRectangle().getUpperLeft().getIntX();
            if (currX < mostLeft) {
                mostLeft = currX;
            }
        }
        return mostLeft;
    }

    /**Gets the bottom y location.
     *
     * @return the bottom y location.
     */
    public int findBottomPos() {
        int bottom = 0;

        for (Block alien : this.aliens) {
            int currY = alien.getCollisionRectangle().getUpperLeft().getIntY()
                    + (int) (alien.getCollisionRectangle().getHeight());
            if (currY > bottom) {
                bottom = currY;
            }
        }
        return bottom;
    }

    /**Gets the highest y location.
     *
     * @return the highest y location.
     */
    public int findTopPos() {
        int top = Integer.MAX_VALUE;

        for (Block alien : this.aliens) {
            int currTopY = alien.getCollisionRectangle().getUpperLeft().getIntY();
            if (currTopY < top) {
                top = currTopY;
            }
        }
        return top;
    }

    /**Adds an alien to the formation.
     *
     * @param alien is the alien to add
     */
    public void addAlien(Block alien) {
        this.aliens.add(alien);
        this.updateRect();
        this.initialLocation = new Point(this.findMostLeftPos(), this.findTopPos());

    }

    /**Removes an alien from the formation.
     *
     * @param alien is the alien to remove
     */
    public void removeAlien(Block alien) {
        this.aliens.remove(alien);
        this.updateRect();
    }

    /**Actually does nothing. For Interface use.
     * @param d is the drawing surface
     */
    public void drawOn(DrawSurface d) {
    }

    /** Called by the game level and tells the formation to move.
     *
     * @param dt is the change in time
     */
    public void timePassed(double dt) {
        if (!(this.gotToTheEnd())) {
            this.move(dt);
        }

    }

    /** Finds the alien that shoots the next shot.
     *
     * @return the block of the alien that shoots the next shot.
     */
    public Block findShooter() {
        Random rand = new Random(); // create a random-number generator
        int num = rand.nextInt(10); // get integer in range 1-400
        //num *= 2;
        num = (num * 2) + 1;

        int lowest = 0;
        Block lowestAlien = null;

        //System.out.println("rand num:\t" + num);
        for (Block tempAlien: this.aliens) {
            //System.out.println("tempAlien.getColumn():\t" + tempAlien.getColumn());
            if (tempAlien.getColumn() == num) {
                int alienUpperLeft = tempAlien.getCollisionRectangle().getUpperLeft().getIntY();
                if (alienUpperLeft > lowest) {
                    lowest = alienUpperLeft;
                    lowestAlien = tempAlien;
                }

            }
        }
        return lowestAlien;
    }

    /** Creates the shoot from the aliens formation.
     *
     * @param g is the game level that running.
     */
    public void shoot(GameLevel g) {

        long timePassed = System.currentTimeMillis() - this.lastShot;
        long milliSecondToSleep = (long) (0.5 * 1000);
        if (timePassed > milliSecondToSleep) {
            Block shooterAlien = this.findShooter();
            if (shooterAlien != null) {
                int x = (shooterAlien.getCollisionRectangle().getUpperLeft().getIntX()
                        + (shooterAlien.getWidth() / 2));
                int y = (shooterAlien.getCollisionRectangle().getUpperLeft().getIntY()
                        + (int) (shooterAlien.getCollisionRectangle().getHeight()) + 10);

                //System.out.println("x: " + x + "; y: " + y + "\n");
                Point p = new Point(x, y);
                Velocity newVel = new Velocity(this.vel.getDx(), 500);

                g.createBullet(newVel, p, true);

                this.lastShot = System.currentTimeMillis();
            }

        }


    }

    /**Check if the formation go to the end.
     *
     * @return true is the formation got to the end of the screen.
     */
    public boolean gotToTheEnd() {
        int currentBottomY = this.rect.getUpperLeft().getIntY() + (int) (this.rect.getHeight()) + 140;
        //System.out.println("\n\n%%%%%%%%%%%%%%%%%%%%% gotToTheEnd %%%%%%%%%%%%%%%%%%%%%%%%\n" + currentBottomY);
        return (currentBottomY > this.maxY);
    }

    /** Moves the formation left, right and down.
     *
     * @param dt is the difference in time
     */
    public void move(double dt) {

        int mostRightX = this.rect.getRightLine().getEnd().getIntX();
        int mostLeftX = this.rect.getUpperLeft().getIntX();

        double dx = this.vel.getDxWithDt(dt);
        if (!(this.gotToTheEnd())) {

            //if (((mostRightX + 15) < 800) && ((mostLeftX - 15) > 0)) { //In the range
            if (((mostRightX + dx + 10) < 800) && (((mostLeftX + dx) - 10) > 0)) { //In the range

                for (Block alien : this.aliens) {
                    double currX = alien.getCollisionRectangle().getUpperLeft().getX();
                    int newX = (int) Math.round(currX + dx);
                    //System.out.println("\n888888888888\n currX = " + currX + "   newX = " + newX + "\n");
                    alien.updateX(newX);
                    currX = alien.getCollisionRectangle().getUpperLeft().getX();
                }
            } else { // Not in the range
                dx = this.vel.getDx();
                dx *= (-1);
                //dx *= this.speedIncrese;

                double isMinus = (dx / Math.abs(dx));
                double absSpeedIncrease = this.speedIncrese * isMinus;
                dx += absSpeedIncrease;

                //double dy = this.vel.getDy();
                double dy = 0;
                this.vel = new Velocity(dx, dy); // Change direction

                // Get down
                for (Block alien : this.aliens) {

                    double currY = alien.getCollisionRectangle().getUpperLeft().getY();
                    int newY = (int) Math.round(currY + this.nextLine);

                    alien.updateY(newY);
                }
            }

            this.updateRect();
        }
    }

    /**
     * Adds the formation to a game.
     *
     * @param g is a game the block should be added to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

}
