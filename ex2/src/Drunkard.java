import java.util.concurrent.ThreadLocalRandom;

/**
 * This class responsible for the drunkard spaceship behavior during the game.
 * This spaceship will always accelerate , If the nearest ship is closer than 0.20 units, the ship will
 * randomly do one of the following action: do nothing, turn right, turn left, teleport, fire or put the
 * shield on.
 */

public class Drunkard extends SpaceShip {

    private static final double DO_SOMETHING = 0.20;
    private static final int TELEPORT = 4;
    private static final int FIRE = 3;
    private static final int SHIELD = 2;

    /**
     * Does the actions of the drunkard spaceship for this round according the drunkard characteristics.
     * Uses the super-class doAction method.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        int whatToDo; // 0-do nothing, -1-turn right, 1-turn left, 2-shield activation, 3-firing, 4-teleport.
        int whereToTurn = 0;
        SpaceShip closestShip = game.getClosestShipTo(this);
        double angleToShip = this.getPhysics().angleTo(closestShip.shipPhysics);

        shieldOff();
        if (this.getPhysics().distanceFrom(closestShip.shipPhysics) < DO_SOMETHING) {
            whatToDo = ThreadLocalRandom.current().nextInt(-1, 5);
            switch (whatToDo) {
                case TELEPORT:
                    teleport();
                    break;
                case SHIELD:
                    putShieldOn();
                    break;
                case FIRE:
                    fire(game);
                    break;
                case TURN_RIGHT:
                case TURN_LEFT:
                case NO_TURN:
                    this.getPhysics().move(true, whatToDo);
            }
        }
        this.getPhysics().move(true, 0);
        super.doAction(game); // Do the reset of the action that take place in every round.
    }
}
