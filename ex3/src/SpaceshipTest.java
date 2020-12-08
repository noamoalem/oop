import oop.ex3.spaceship.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The SpaceshipTest class implements a tests of the Spaceship class methods.
 */
public class SpaceshipTest {
    private static final int SUCCESS = 0;
    private static final int NO_PLACE = -3;
    private static final int INVALID_ID = -1;
    private static final int CAPACITY_REQUIRE = -2;
    private static final int LOCKER_CAPACITY = 10;
    private Spaceship spaceship;
    private final int numOfCrewMembers = 10;
    private final int numOfLockers = 5;
    private final String spaceshipName = "USS Discovery";

    @Before
    /**
     * This method initialize Spaceship object. to use this object in the tests.
     */
    public void createTestObjects(){
        int[] crewMembers = new int[numOfCrewMembers];
        for (int i = 0; i<numOfCrewMembers ;i++){ //creat array of crew member id's.
            crewMembers[i]=i;
        }
        spaceship = new Spaceship(spaceshipName, crewMembers, numOfLockers);
    }

    @Test
    /**
     * This method check the method getLongTermStorage of Spaceship class.
     */
    public void testGetLongTermStorage(){
        assertTrue("Need to be long-term storage object",
                spaceship.getLongTermStorage() instanceof LongTermStorage);
    }

    @Test
    /**
     * This method check the method creatLocker of Spaceship class.
     */
    public void testCreatLocker(){
        assertEquals("Need success", SUCCESS, spaceship.createLocker(3,LOCKER_CAPACITY));
        assertEquals("invalid id", INVALID_ID, spaceship.createLocker(-3,LOCKER_CAPACITY));
        assertEquals("invalid capacity", CAPACITY_REQUIRE, spaceship.createLocker(0,-8));
        for (int i = 1; i<5; i++){
            spaceship.createLocker(i,LOCKER_CAPACITY);
        }
        // no more place for new lockers.
        assertEquals("Need to be -1", NO_PLACE, spaceship.createLocker(2,LOCKER_CAPACITY));
    }

    @Test
    /**
     * This method check the method getCrewIds of Spaceship class.
     */
    public void testGetCrewIds(){
        int[] arrayExpected = new int[numOfCrewMembers];
        for (int i = 0; i<numOfCrewMembers ;i++){
            arrayExpected[i]=i;
        }
        assertTrue("Need to be int array", spaceship.getCrewIDs() instanceof int[]);
        assertEquals("Need to be length 10",numOfCrewMembers,spaceship.getCrewIDs().length);
        assertArrayEquals("Need to be int array object filled with number 0 to 9", arrayExpected,
                spaceship.getCrewIDs());
    }

    @Test
    /**
     * This method check the method getLockers of Spaceship class.
     */
    public void testGetLockers(){
        assertEquals("Need to be 5",5, spaceship.getLockers().length);
        assertTrue("Need to be int array of lockers", spaceship.getLockers() instanceof Locker[]);
    }

}
