/**
 * This class responsible for the runner spaceship behavior during the game.
 * This spaceship attempts to run away from the fight.
 */

public class Runner extends SpaceShip {

    private static final double NEED_TO_TELEPOT_RAD = 0.23;
    private static final double NEED_TO_TELEPOT_UNIT = 0.25;

    /**
     * Does the actions of the runner spaceship for this round according the runner characteristics.
     * Uses the super-class doAction method.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        int whereToTurn = NO_TURN; // 0 no turn, -1 turn right, 1 turn left.
        SpaceShip closestShip = game.getClosestShipTo(this);
        double angleToShip = this.getPhysics().angleTo(closestShip.shipPhysics);

        shieldOff();
        if (angleToShip< NEED_TO_TELEPOT_RAD && checkDistanceIsSmaller(closestShip, NEED_TO_TELEPOT_UNIT)){
            teleport();
        }
        // Find if the runner needs to turn in order to run from the other spaceships.
        if (angleToShip<0){
            whereToTurn = TURN_LEFT;
        }
        if (angleToShip>0){
            whereToTurn = TURN_RIGHT;
        }
        this.getPhysics().move(true,whereToTurn);
        super.doAction(game); // Do the reset of the action that take place in every round.
    }
}
