import java.awt.*;
import oop.ex2.*;

/**
 * This class responsible for the human controlled spaceship behavior during the game.
 * The human controlled spaceship controlled by the user.
 * left-arrow and right-arrow to turn, up-arrow to accelerate, ’d’ to fire a shot, ’s’ to turn on the shield,
 * ’a’ to teleport.
 */

public class HumanControlled extends SpaceShip{

    /**
     * Does the actions of the human controlled ship for this round according to the user input.
     * Uses the super-class doAction method.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game){
        shieldOff();
        if(game.getGUI().isTeleportPressed()){
            teleport();
        }
        accelerateAndTurn(game);
        if (game.getGUI().isShieldsPressed()){
            putShieldOn();
        }
        if (game.getGUI().isShotPressed()){
            fire(game);
        }
        super.doAction(game); // Do the reset of the action that take place in every round.
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        if (shieldIsOn) {
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.SPACESHIP_IMAGE;
    }

    /**
     * Check if the user pressed on left-arrow, right-arrow, and turn lef or right or accelerate.
     * @param game the game object to which this ship belongs.
     */
    private void accelerateAndTurn(SpaceWars game){
        int whereToTurn = 0;
        if (game.getGUI().isRightPressed()){
            whereToTurn = TURN_RIGHT;
        }
        if (game.getGUI().isLeftPressed()){
            whereToTurn = TURN_LEFT;
        }
        if (game.getGUI().isRightPressed() && game.getGUI().isLeftPressed()) {
            whereToTurn = NO_TURN;
        }

        boolean accelerate = false;
        if (game.getGUI().isUpPressed()){
            accelerate = true;
        }
        this.getPhysics().move(accelerate,whereToTurn);
    }
}
