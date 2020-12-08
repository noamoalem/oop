/**
 * This class responsible for the aggressive spaceship behavior during the game.
 * This spaceship pursues other ships and tries to fire at them.
 */
public class Aggressive extends SpaceShip {

    private static final double NEED_TO_SHOT = 0.21;

    /**
     * Does the actions of the aggressive spaceship for this round according the aggressive characteristics.
     * Uses the super-class doAction method.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        int whereToTurn = NO_TURN; // 0 no turn, -1 turn right, 1 turn left.
        SpaceShip closestShip = game.getClosestShipTo(this);
        double angleToShip = this.getPhysics().angleTo(closestShip.shipPhysics); // Find angle this spaceship
        // should turn in order to face the position of another ship.

        shieldOff();
        if (angleToShip<0) {
            whereToTurn = TURN_RIGHT;
        }
        if (angleToShip>0){
            whereToTurn = TURN_LEFT;
        }
        this.getPhysics().move(true,whereToTurn);
        if (angleToShip< NEED_TO_SHOT){
            fire(game);
        }
        super.doAction(game); // Do the reset of the action that take place in every round.
    }
}
