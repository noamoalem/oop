import oop.ex3.spaceship.*;

/**
 * The Spaceship class implements a Spaceship object that has crew members and a storage place.
 */
public class Spaceship {
    private static final int SUCCESS = 0;
    private static final int NO_PLACE = -3;
    private static final int INVALID_ID = -1;
    private static final int CAPACITY_REQUIRE = -2;
    private final String spaceShipName;
    private final int[] crewMembers;
    private final int numLockers;
    private Locker[] lockerArray;
    private int lockerCounter;
    private ItemFactory itemFactory;
    private Item[][] constraintsItems;
    private LongTermStorage longTermStorage;

    /**
     * The constructor, initialize a Spaceship.
     *
     * @param name The spaceship name.
     * @param crewIDs The spaceship crew members.
     * @param numOfLockers The spaceship limit of lockers.
     */
    public Spaceship(String name, int[] crewIDs, int numOfLockers){
        spaceShipName = name;
        crewMembers = crewIDs;
        numLockers = numOfLockers;
        lockerArray = new Locker[numOfLockers];
        lockerCounter = 0;
        constraintsItems = ItemFactory.getConstraintPairs();
        longTermStorage = new LongTermStorage();
    }

    /**
     * This method returns the long-term storage object associated with that Spaceship.
     * @return long-term storage object associated with that Spaceship.
     */
    public LongTermStorage getLongTermStorage(){
        return longTermStorage;
    }

    /**
     * This method creates a Locker object, and adds it as part of the Spaceship storage.
     * @param crewID id number of the crew member.
     * @param capacity the wanted capacity for the locker.
     * @return  0 If locker added to the spaceship storage
     *         -1 If the id invalid (<=0).
     *         -2 If the given capacity does not meet the Locker class requirements.
     *         -3 If the Spaceship already contains the allowed number of lockers
     */
    public int createLocker(int crewID, int capacity){
        if (validId(crewID)) {
            if (lockerCounter<numLockers){
                if (capacity>SUCCESS){
                lockerArray[lockerCounter] = new Locker(this.longTermStorage, capacity, constraintsItems);
                lockerCounter++;
                return SUCCESS;
                }
                return CAPACITY_REQUIRE;
            }
            return NO_PLACE;
        }
        return INVALID_ID;
    }

    /**
     * This methods returns an array with the crew ids.
     * @return array with the crew ids.
     */
    public int[] getCrewIDs(){
        return crewMembers;
    }

    /**
     * This methods returns an array of the Lockers, whose length is the number of locker the spaceship can
     * contain.
     * @return array of the Lockers.
     */
    public Locker[] getLockers(){
        return lockerArray;
    }

    /**
     * This methods checks if a given id is valid.
     * @return true if the id number valid, false otherwise.
     */
    private boolean validId(int id){
        for (int i: crewMembers){
            if (i == id)
                return true;
        }
        return false;
    }
}

