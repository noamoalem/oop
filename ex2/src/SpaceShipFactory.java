import oop.ex2.*;

/**
 * This class has a single static method.
 * It is used by the supplied driver to create all the spaceship objects according to the command line
 * arguments.
 */

public class SpaceShipFactory {

    private final static String runnerShip = "r";
    private final static String basherShip = "b";
    private final static String aggressiveShip = "a";
    private final static String humanControlledShip = "h";
    private final static String specialShip = "s";
    private final static String drunkardShip = "d";

    /**
     * Create all the spaceship objects according to the command line arguments.
     * @param args The command line arguments.
     * @return Array of the spaceships that will be on the game.
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        int shipArrayLength = args.length;
        SpaceShip[] spaceShipArray = new SpaceShip[shipArrayLength];
        for (int i=0;i<shipArrayLength;i++){
            switch (args[i]){
                case runnerShip:
                    spaceShipArray[i] = new Runner();
                    break;
                case basherShip:
                    spaceShipArray[i] = new Basher();
                    break;
                case aggressiveShip:
                    spaceShipArray[i] = new Aggressive();
                    break;
                case humanControlledShip:
                    spaceShipArray[i] = new HumanControlled();
                    break;
                case specialShip:
                    spaceShipArray[i] = new Special();
                    break;
                case drunkardShip:
                    spaceShipArray[i] = new Drunkard();
                    break;
            }
        }
        return spaceShipArray;
    }
}
