package game;
import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import gameobjects.AliensFormation;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.SpaceShip;
import graphics.Point;
import indicators.LevelIndicator;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;
import levels.LevelInformation;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import listeners.SpaceShipRemover;


/**
 * Game class.
 *
 * @author Hadas Neuman
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private int screenWidth;
    private int screenHeight;
    private GUI gui;
    private Counter aliensCounter;
    private Counter ballsCounter;
    private Counter score;
    private Counter turns;
    private AnimationRunner runner;
    private boolean running;
    private biuoop.KeyboardSensor keyboard;
    private LevelInformation li;
    private SpaceShip spaceShip;
    //private boolean shipGotHit;
    private AliensFormation aliensFormation;
    private int roundNum;


    /**A constructor.
     *
     * @param ar is the animation runner
     * @param ks is the Keyboard Sensor
     * @param gui is the graphic user interface
     * @param score is the score counter
     * @param turns is the turns counter
     * @param li is the level information
     */
    public GameLevel(AnimationRunner ar, KeyboardSensor ks,
            GUI gui, Counter score, Counter turns, LevelInformation li) {
        this.runner = ar;
        this.keyboard = ks;
        this.environment = new GameEnvironment();
        this.screenHeight = 600;
        this.screenWidth = 800;
        this.gui = gui;
        //System.out.println("&&&&&&&&&&&&&&&&&&Initial counter:\t" + );
        this.aliensCounter = new Counter(li.numberOfBlocksToRemove());
        this.ballsCounter = new Counter(0);
        this.score = score;
        this.turns = turns;
        this.sprites = new SpriteCollection();;
        this.running = true;
        this.li = li;
        //this.shipGotHit = false;
        this.aliensFormation = new AliensFormation();
    }

    /**A constructor.
     *
     * @param ar is the animation runner
     * @param ks is the Keyboard Sensor
     * @param gui is the graphic user interface
     * @param score is the score counter
     * @param turns is the turns counter
     * @param li is the level information
     * @param alienFormationVel is the initial velocity of the aliens formation
     */
    public GameLevel(AnimationRunner ar, KeyboardSensor ks,
            GUI gui, Counter score, Counter turns, LevelInformation li, Velocity alienFormationVel) {
        this.runner = ar;
        this.keyboard = ks;
        this.environment = new GameEnvironment();
        this.screenHeight = 600;
        this.screenWidth = 800;
        this.gui = gui;
        //System.out.println("&&&&&&&&&&&&&&&&&&Initial counter:\t" + );
        this.aliensCounter = new Counter(li.numberOfBlocksToRemove());
        this.ballsCounter = new Counter(0);
        this.score = score;
        this.turns = turns;
        this.sprites = new SpriteCollection();;
        this.running = true;
        this.li = li;
        //this.shipGotHit = false;
        this.aliensFormation = new AliensFormation(alienFormationVel);
    }

    /**
     * Add a Collidable to the collidable list.
     *
     *@param c is a collidable to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add a Sprite to the sprites list.
     *
     *@param s is a sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove a Collidable from the collidable list.
     *
     *@param c is a collidable to add
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove a Sprite from the sprites list.
     *
     *@param s is a sprite to add
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initialize a new game: create the Blocks and Ball and Paddle and add them to the game.
     *
     */
    public void initialize() {

        //Adding the background
        this.sprites.addSprite(this.li.getBackground());

        BlockRemover blockR = new BlockRemover(this, this.aliensCounter);
        BallRemover ballR = new BallRemover(this, this.ballsCounter);
        //SpaceShipRemover spaceShipRemover = new SpaceShipRemover(this, this.turns);

        ScoreTrackingListener scoreListener = new ScoreTrackingListener(this, this.score);

        //Creating the score indicator
        ScoreIndicator si = new ScoreIndicator(this.score);
        si.addToGame(this);


        //Creating the lives indicator
        LivesIndicator livesIndicator = new LivesIndicator(this.turns);
        livesIndicator.addToGame(this);

        //Creating the level indicator

        LevelIndicator leveli = new LevelIndicator(this.li.levelName() + " " + this.roundNum);
        leveli.addToGame(this);

        int borderWidth = 1;
        int hedearWidth = 30;


        //make the top border
        Point blockP = new Point(0, hedearWidth);
        java.awt.Color[] bColors = {java.awt.Color.gray};
        Block block = new Block(blockP, screenWidth, borderWidth, bColors, 0);
        block.addToGame(this);
        block.addHitListener(ballR);

        //make the right border
        blockP = new Point((screenWidth - borderWidth), borderWidth + hedearWidth);
        block = new Block(blockP, borderWidth, screenHeight - borderWidth, bColors, 0);
        block.addToGame(this);
        block.addHitListener(ballR);

        //make the bottom border
        //***The end-game region***
        blockP = new Point(borderWidth, screenHeight);
        block = new Block(blockP, (double) screenWidth - (borderWidth * 2), borderWidth, bColors, 0);
        block.addHitListener(ballR);
        block.addToGame(this);

        //make the left border
        blockP = new Point(0, hedearWidth);
        block = new Block(blockP, borderWidth, screenHeight - borderWidth, bColors, 0);
        block.addToGame(this);
        block.addHitListener(ballR);

        // Adding the shields and the aliens to the game
        for (Block b: this.li.blocks()) {
            Block tempBlock = new Block(b);
            tempBlock.addToGame(this);
            tempBlock.addHitListener(blockR);
            tempBlock.addHitListener(ballR);
            if (tempBlock.getStrFilesName()[0] != null) { //this is an alien
                tempBlock.addHitListener(scoreListener);
                this.aliensFormation.addAlien(tempBlock);
            }
        }


        //Creating the spaceShip
        /*
        this.spaceShip = new SpaceShip(this.gui, this.screenWidth, this.screenHeight,
                this.li.paddleWidth(), this.li.paddleSpeed());
        this.spaceShip.addHitListener(spaceShipRemover);
        this.spaceShip.addHitListener(ballR);
        /*SpaceShip sp = new SpaceShip(this.gui, this.screenWidth, this.screenHeight,
                this.li.paddleWidth(), this.li.paddleSpeed());
        sp.addHitListener(spaceShipRemover);
        sp.addHitListener(ballR);
        sp.addToGame(this);*/

        this.aliensFormation.addToGame(this);

    }

    /**Sets the blocks counter.
     *
     * @param counter is the block counter
     */
    public void setBlockCounter(Counter counter) {
        this.aliensCounter = counter;
    }

    /**Sets the score counter.
     *
     * @param counter is the score counter
     */
    public void setScore(Counter counter) {
        this.score = counter;
    }

    /**Sets the balls counter.
     *
     * @param counter is the balls counter
     */
    public void setBallCounter(Counter counter) {
        this.ballsCounter = counter;
    }

    /**Sets the turns counter.
     *
     * @param counter is the balls counter
     */
    public void setTurnsCounter(Counter counter) {
        this.turns = counter;
    }

    /**
     * Run the game -- start the animation loop.
     *
     */
    public void run() {
        while ((this.turns.getValue() > 0)) {
            playOneTurn();
        }
        gui.close();
        return;
    }


    /**
     * Runs one turn of the game.
     */
    public void playOneTurn() {

        this.aliensFormation.toTheStart();
        //this.shipGotHit = false;

        BallRemover ballR = new BallRemover(this, this.ballsCounter);
        SpaceShipRemover spaceShipRemover = new SpaceShipRemover(this, this.turns);

        this.spaceShip = new SpaceShip(this.gui, this.screenWidth, this.screenHeight,
                this.li.paddleWidth(), this.li.paddleSpeed());
        this.spaceShip.addHitListener(spaceShipRemover);
        this.spaceShip.addHitListener(ballR);
        this.spaceShip.addToGame(this);
        this.running = true;
        spaceShipRemover.setUpSpaceShip(this.spaceShip);

        /*
        this.spaceShip = new SpaceShip(this.gui, this.screenWidth, this.screenHeight,
                this.li.paddleWidth(), this.li.paddleSpeed());
        this.spaceShip.addToGame(this);
        this.running = true;*/

        // use the runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));

        this.runner.run(this);

        //removing the paddle
        spaceShip.removeFromGame(this);
        this.removeAllBalls();
    }

    /**Creates balls according to the level information.
     *
     */
    public void createBallsOnTopOfPaddle() {

        BallRemover ballR = new BallRemover(this, this.ballsCounter);


        for (Velocity vel: this.li.initialBallVelocities()) {
            Point center = new Point((this.screenWidth / 2), (this.screenHeight - 30));
            Ball ball = new Ball(center, 5, java.awt.Color.white, vel, this.environment);
            ball.addToGame(this);
            this.ballsCounter.increase(1);
            ballR.setCounter(this.ballsCounter);
        }
    }

    /**Creates with constant parameters.
     *
     */
    public void createBullet() {
        Velocity vel = new Velocity(0, 500);
        Point center = this.spaceShip.getCenter();

        BallRemover ballR = new BallRemover(this, this.ballsCounter);
        Ball ball = new Ball(center, 5, java.awt.Color.white, vel, this.environment);
        ball.addToGame(this);
        this.ballsCounter.increase(1);
        ballR.setCounter(this.ballsCounter);
    }

    /**Creates balls according to the level information.
     *
     * @param vel is the ball velocity
     * @param p is the point
     * @param alien tells if the shooter is an alien
     */
    public void createBullet(Velocity vel, Point p, boolean alien) {

        BallRemover ballR = new BallRemover(this, this.ballsCounter);

        java.awt.Color color = null;
        int radius = 5;
        if (alien) {
            color = java.awt.Color.red;
            radius = 4;
        } else {
            color = java.awt.Color.white;
            radius = 3;
        }
        Ball ball = new Ball(p, radius, color, vel, this.environment);
        //Ball ball = new Ball(newP, radius, color, vel, this.environment);
        ball.addToGame(this);

        this.ballsCounter.increase(1);
        ballR.setCounter(this.ballsCounter);
    }

    /**Draws one frame.
     *
     * @param d is the drawing surface.
     * @param dt is the difference in time
     */
    public void doOneFrame(DrawSurface d, double dt) {

        // drawing the background
        d.setColor(java.awt.Color.decode("35"));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        //pause?
        if (this.keyboard.isPressed("p")) {
            Animation a = new PauseScreen();
            Animation ak = new KeyPressStoppableAnimation(this.keyboard, " ", a);
            this.runner.run(ak);
        }

        //shoot?
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.spaceShip.shoot(this);
        }

        //aliens shoot?
        this.aliensFormation.shoot(this);


        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);

        /*if (this.shipGotHit) {
            this.turns.decrease(1);
            this.running = false;
        }*/

        if (this.spaceShip.shipGotHit()) {
            //this.turns.decrease(1);
            this.running = false;
        }
        if (this.aliensCounter.getValue() == 0) {
            this.running = false;
        }

        if (this.aliensFormation.gotToTheEnd()) {
            this.turns.decrease(1);
            this.running = false;
        }

        /*
        if (this.ballsCounter.getValue() == 0) {
            this.lives.decrease(1);
            this.running = false;
        }*/
    }

    /**Gets the block counter.
     *
     * @return the blocks counter.
     */
    public Counter getBlockCounter() {
        return this.aliensCounter;
    }

    /**Gets the aliens formation.
     *
     * @return the aliens formation.
     */
    public AliensFormation getAliens() {
        return this.aliensFormation;
    }

    /**Indicates if the game should be stopped.
     *
     * @return true is the game should be stopped, or false otherwise
     */
    public boolean shouldStop() {

        return (!(this.running));
    }

    /**Removes the alien.
     *
     * @param alien is the block that got hit
     */
    public void removeFromAliensFormation(Block alien) {
        this.aliensFormation.removeAlien(alien);
    }

    /**Removes all the balls from the game.
     *
     */
    public void removeAllBalls() {
        this.sprites.removeAllBalls(this);
    }


    /**Gets the balls counter.
     *
     * @return the balls counter
     */
    public Counter getBallsCounter() {
        return this.ballsCounter;
    }

    /**Sets the static number of rounds.
     * @param roundNum the roundNum to set
     */
    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }


}