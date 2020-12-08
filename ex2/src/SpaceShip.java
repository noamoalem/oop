import java.awt.Image;
import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. 
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 *  a base class for the other spaceships or any other option you will choose.
 *  
 * @author oop
 */

public class SpaceShip{

    /** The number of health a spaceship start with.*/
    private final static int HEALTH_START = 22;

    /** The number of maximal energy spaceship start with.*/
    private final static int MAX_ENERGY_START = 210;

    /** The number of current energy a spaceship start with.*/
    private final static int CURRENT_ENERGY_START = 190;

    /** The number that bashing cause the energy level to grow.*/
    private final static int BASHING = 18;

    /** The cost of firing.*/
    private final static int FIRING = 19;

    /** The cost of getting hit.*/
    private final static int HITED = 10;

    /** The cost of teleporting .*/
    private final static int TELEPORTING = 140;

    /** The cost of being with shield on.*/
    private final static int SHIELDUP = 3;

    /** The limit of rounds to fire.*/
    private final static int FIREROUND = 7;

    private int fireCounter = 0;
    private int healthCounter;
    private int maxEnergy;
    private int curEnergy;
    protected SpaceShipPhysics shipPhysics;
    public final static int TURN_LEFT = 1;
    public final static int TURN_RIGHT = -1;
    public final static int NO_TURN = 0;
    public boolean shieldIsOn = false;


    /**
     * The constructor.
     * Creates a new space ship object with a health and energy and with ship physics object for this space
     * ship.
     */
    public SpaceShip(){
        this.healthCounter = HEALTH_START;
        this.maxEnergy = MAX_ENERGY_START;
        this.curEnergy = CURRENT_ENERGY_START;
        this.shipPhysics = new SpaceShipPhysics();
    }

    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        if (curEnergy<maxEnergy) { // Regeneration of the 1 unit of energy of this round.
            curEnergy++;
        }
        if (shieldIsOn){
            curEnergy -= SHIELDUP;
        }
        if (fireCounter>FIREROUND){ // Counting to know when the space ship can shot.
            fireCounter = 0;
        }
        else {
            if (fireCounter != 0) {
                fireCounter++;
            }
        }
    }

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){
        if (shieldIsOn) { // The shield on so the energy goes up.
            maxEnergy += BASHING;
            curEnergy += BASHING;
        }
        else {
            maxEnergy -= HITED;
            healthCounter--;
            if (curEnergy>maxEnergy) {
                curEnergy = maxEnergy;
            }
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        this.healthCounter = HEALTH_START;
        this.maxEnergy = MAX_ENERGY_START;
        this.curEnergy = CURRENT_ENERGY_START;
        this.fireCounter = 0;
        this.shipPhysics = new SpaceShipPhysics();
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return healthCounter == 0;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return shipPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!shieldIsOn){ // Has no shield.
            maxEnergy-=HITED;
            healthCounter--;
            if (curEnergy>maxEnergy){
                curEnergy = maxEnergy;
            }
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * 
     * @return the image of this ship.
     */
    public Image getImage(){
        if (shieldIsOn){
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (fireCounter == 0) { // Check that pass 7 round from the last shot.
            if (curEnergy >= FIRING) { // Check that the space ship has enough energy.
                fireCounter++;
                game.addShot(shipPhysics); // Fire a single shot.
            }
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void putShieldOn() {
        if (curEnergy>=SHIELDUP){ //Check that the space ship has enough energy.
            shieldIsOn = true;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (curEnergy>=TELEPORTING) { //Check that the space ship has enough energy.
            shipPhysics = new SpaceShipPhysics(); // Teleport the space ship.
            curEnergy -= TELEPORTING;
        }
    }

    /**
     * Remove the shield.
     */
    public void shieldOff(){
        shieldIsOn = false;
    }

    /**
     * This method check if the distance between two spaceships is smaller then a given units number.
     * @param other spaceship to check if the distance from her is smaller then some number of units.
     * @param units The number of units to check.
     * @return true if the distance of the two spaceships is smaller then the units number, false otherwise.
     */
    public boolean checkDistanceIsSmaller(SpaceShip other ,double units){
        if (shipPhysics.distanceFrom(other.shipPhysics)<units){
            return true;
        }
        return false;
    }

}
