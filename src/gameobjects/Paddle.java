package gameobjects;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.Collidable;
import game.GameLevel;
import game.Sprite;
import game.Velocity;
import graphics.Point;
import graphics.Rectangle;

/**
 * Game Paddle.
 *
 * @author Hadas Neuman
 */

public class Paddle implements Sprite, Collidable {
    private Rectangle rect;
    private biuoop.KeyboardSensor keyboard;
    private int screenWidth;
    private int screenHeight;
    private int speed;

    /** A consructor - creates new paddle from gui and screen size parameters.
     *
     * @param gui is the graphical user interface
     * @param screenWidth is the screen width
     * @param screenHeight is the screen height
     * @param padWidth is the paddle width
     * @param speed is the paddle speed
     */
    public Paddle(GUI gui, int screenWidth, int screenHeight, int padWidth, int speed) {
        this.keyboard = gui.getKeyboardSensor();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        Point p = new Point((this.screenWidth - padWidth) / 2, (this.screenHeight - 20));
        rect = new Rectangle(p, padWidth, 1);
        this.speed = speed;
    }

    /**Moves the paddle one step to the left.
     *
     * @param dt is the difference in time
     */
    public void moveLeft(double dt) {
        double newX = this.rect.getUpperLeft().getX() - (this.speed * dt);
        double y = this.rect.getUpperLeft().getY();

        //Checking if the paddle is steel in the limits
        if (newX >= 0) {
            //updated the new location
            Point newUpperLeft = new Point(newX, y);
            this.rect = new Rectangle(newUpperLeft, this.rect.getWidth(), this.rect.getHeight());
        }
    }

    /**Moves the paddle one step to the Right.
     *
     * @param dt is the difference in time
     */
    public void moveRight(double dt) {
        double newX = this.rect.getUpperLeft().getX() + (this.speed * dt);
        double y = this.rect.getUpperLeft().getY();

        //Checking if the paddle is steel in the limits
        if (newX <= (this.screenWidth - this.rect.getWidth())) {
            //updated the new location
            Point newUpperLeft = new Point(newX, y);
            this.rect = new Rectangle(newUpperLeft, this.rect.getWidth(), this.rect.getHeight());
        }

    }

    /**Check the keyboard sensor and moves the paddle one step.
     *
     * @param dt is the difference in time
     */
    public void timePassed(double dt) {
        //support also left keyboard
        if ((keyboard.isPressed(KeyboardSensor.LEFT_KEY)) || (keyboard.isPressed("a"))) {
            this.moveLeft(dt);
        }
        if ((keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) || (keyboard.isPressed("d"))) {
            this.moveRight(dt);
        }

    }

    /**Draws the paddle on a given draw surface.
     * @param d is the given draw surface that the paddle should be drawn on
     */
    public void drawOn(DrawSurface d) {

        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        //int height = (int) this.rect.getHeight();
        int height = 15;


        d.setColor(java.awt.Color.yellow);
        d.fillRectangle(x, y, width, height);
    }

    /**Returns the "collision shape" of the object.
     * @return the rectangle that presents the shape
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**Notifies the object that we collided with it at collisionPoint with a given velocity.
     * @param ball is the hitting ball
     * @param collisionPoint is the point the the object collided with this block
     * @param currentVelocity is the current velocity of the object
     * @return the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    public Velocity hit(Ball ball, Point collisionPoint, Velocity currentVelocity) {

        //finding the block corners
        double x1 = this.rect.getUpperLeft().getX();
        double x2 = x1 + this.rect.getWidth();
        double y1 = this.rect.getUpperLeft().getY();
        double y2 = y1 + this.rect.getHeight();
        double dX = currentVelocity.getDx();
        double dY = currentVelocity.getDy();

        //finding the angle:
        //1 - finding the relative location
        double collX = collisionPoint.getX();
        double collY = collisionPoint.getY();
        double part = (this.rect.getWidth() / 5);
        double ul = this.rect.getUpperLeft().getX();
        double angle = 0;
        Velocity newVel;

        if (collX < (ul + (part))) { //the first left region
            angle = 150;
        } else if ((collX >= (ul + part)) && (collX < (ul + (2 * part)))) { //the second left region
            angle = 120;
        } else if ((collX >= (ul + (3 * part))) && (collX < (ul + (4 * part)))) { //the first right region
            angle = 60;
        } else if (collX > (ul + (4 * part))) { //the second right region
            angle = 30;
        }
        if (this.rect.getUpperLeft().getIntY() < collY) { // if the ball is under the paddle
            angle *= (-1); //  turn the angle upside down
        }

        //finding the speed
        double newSpeed = Math.sqrt((dX * dX) + (dY * dY));

        if (angle != 0) { //this is NOT the middle region
            newVel = currentVelocity.fromAngleAndSpeed(angle, newSpeed);
        } else { //this is the middle region

            //check if the collision point is on the horizontal borders
            if (((collisionPoint.getX() == x1) && (0 < dX))
                    || ((collisionPoint.getX() == x2) && (dX < 0))) {
                dX *= (-1);
            }

            //check if the collision point is on the vertical borders
            if (((collisionPoint.getY() == y1) && (0 < dY))
                    || ((collisionPoint.getY() == y2) && (dY < 0))) {
                dY *= (-1);
            }
            newVel = new Velocity(dX, dY);
        }
        return newVel;
    }

    /**
     * Adds the paddle to a game.
     *
     * @param g is a game the paddle should be added to
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove the paddle from a game.
     *
     * @param g is a game the paddle should be added to
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }
}
