import java.awt.Image;
import javax.swing.*;

/**
 * This class responsible for the special spaceship behavior during the game.
 * This spaceship will always accelerate,and turn left, when she get close to another ship by 0.25 units,
 * tries to fire, to put the shield on for 40 rounds, and turn away from this ship.
 */

public class Special extends SpaceShip{

    /**The distance from another spaceship that the special spaceship attempt to put the shield on and fire.*/
    private final static double SHIELD_AND_FIRE = 0.25;

    private int roundCounter = 0;

    private static final int SHIELD_ON = 40;

    /** The image of the special spaceship */
    public static final Image SPECIAL_SHIP_IMAGE =
            new ImageIcon(Special.class.getResource("SpongeBob.gif"), "").getImage();

    /** The image of the special spaceship with an active shield */
    public static final Image SPECIAL_SHIP_SHIELD_IMAGE =
            new ImageIcon(Special.class.getResource("Spongebobs_House.gif"), "").getImage();

    /**
     * Does the actions of the special spaceship for this round according to this ship characteristics.
     * Uses the super-class doAction method.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game){

        int whereToTurn = TURN_LEFT; // 0 no turn, -1 turn right, 1 turn left.
        SpaceShip closestShip = game.getClosestShipTo(this);
        double angleToShip = this.getPhysics().angleTo(closestShip.shipPhysics);

        shieldOff();
        if (checkDistanceIsSmaller(closestShip, SHIELD_AND_FIRE)){ // Check if it got close to another ship.
            if (roundCounter<= SHIELD_ON){ // Shield stays for 40 rounds.
                putShieldOn();
                fire(game);
                roundCounter++;
                if (angleToShip>0){
                    whereToTurn = TURN_RIGHT;
                }
            }
            else {
                roundCounter = 0;
            }
        }
        this.getPhysics().move(true, whereToTurn);
        super.doAction(game);
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * @return the image of this ship.
     */
    public Image getImage(){
        if (shieldIsOn){
            return Special.SPECIAL_SHIP_SHIELD_IMAGE;
        }
        return Special.SPECIAL_SHIP_IMAGE;
    }
}
