import oop.ex3.spaceship.*;
import org.junit.*;
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 * The LockerTest class implements a tests of the Locker class methods.
 */
public class LockerTest {
    private static int LOCKER_BIG_CAPACITY = 100;
    private static int LOCKER_SMALL_CAPACITY = 10;
    private static final int HASMOVED = 1;
    private static final int SUCCESS = 0;
    private static final int NOPLACE = -1;
    private static final int CONSTRAINT = -2;
    private static final int ADDING_NEGATIVE = -1;
    private static final int REMOVING_NEGATIVE = -1;
    private static Item baseballBat = ItemFactory.createSingleItem("baseball bat");
    private static Item helmetSize1 = ItemFactory.createSingleItem("helmet, size 1");
    private static Item helmetSize3 = ItemFactory.createSingleItem("helmet, size 3");
    private static Item sporesEngine = ItemFactory.createSingleItem("spores engine");
    private static Item football = ItemFactory.createSingleItem("football");
    private Locker locker;
    private Locker locker2;
    private LongTermStorage longTerm;
    private Item[][] constraintsItems;

    @Before
    /**
     * This method initialize 2 Locker object. to use them object in the tests.
     */
    public void createTestObjects(){
        longTerm = new LongTermStorage();
        constraintsItems = ItemFactory.getConstraintPairs();
        locker = new Locker(longTerm, LOCKER_BIG_CAPACITY, constraintsItems);
        locker2 = new Locker(longTerm, LOCKER_SMALL_CAPACITY, constraintsItems);
    }

    @Test
    /**
     * This method check the method addItem of locker class.
     */
    public void testAddItem(){
        assertEquals("Need to be -1", ADDING_NEGATIVE, locker.addItem(helmetSize1,0));
        assertEquals("Need to be -1", ADDING_NEGATIVE, locker.addItem(helmetSize1,-3));
        // adding 10 helmet, check return 0.
        assertEquals("Need to be 0", SUCCESS, locker.addItem(helmetSize1,10));

        // adding 100 baseball bat , 90 moves to long term locker. check return 1.
        assertEquals("Need to be 1", NOPLACE, locker.addItem(baseballBat,100));

        // adding 120 spores engine ,volume 10 .
        assertEquals("Need to be -1", NOPLACE, locker.addItem(sporesEngine,120));

        // adding 4 spores engine ,volume 10 .
        assertEquals("Need to be 1", SUCCESS, locker.addItem(sporesEngine,4));

        assertEquals("Need to be 1", SUCCESS, locker.addItem(helmetSize3,6));

        // locker is full
        assertEquals("Need to be -1", NOPLACE,locker.addItem(baseballBat,1));
    }
    @Test
    /**
     * This method check the method addItem of locker class.
     */
    public void testAddItem2(){
        // spores Engine volume is 10. need to move to long term.
        assertEquals("Need to be 1", HASMOVED, locker2.addItem(sporesEngine,1));
        // adding 1 helmet Size 3 volume is 5.
        assertEquals("Need to be 0", SUCCESS, locker2.addItem(helmetSize3,1));
        // adding 1 more helmet Size 3. so 2 needs to move to long term.
        assertEquals("Need to be 1", HASMOVED, locker2.addItem(helmetSize3,1));
        locker2.addItem(football,1);
        assertEquals("constraint!", CONSTRAINT, locker2.addItem(baseballBat,1));
        locker2.removeItem(football,1);
        locker2.addItem(baseballBat,1);
        assertEquals("constraint!", CONSTRAINT, locker2.addItem(football,1));
        locker2.addItem(helmetSize1,1);
        locker2.addItem(helmetSize3,1);
        // locker is full, but adding 1 helmet Size 3 can cause the item move to long-term (it will be more
        // then 50%. so check that it wont happened and no item will be added/
        assertEquals("locker is full!",NOPLACE, locker2.addItem(helmetSize3,1));
    }

    @Test
    /**
     * This method check the method removeItem of locker class.
     */
    public void testRemoveItem(){
        // no items to remove locker is empty.
        assertEquals("Need to be -1", REMOVING_NEGATIVE, locker.removeItem(baseballBat,1));
        // negative number of  items to remove.
        assertEquals("Need to be -1", REMOVING_NEGATIVE, locker.removeItem(baseballBat,-1));
        locker.addItem(helmetSize3,3);
        assertEquals("Need to be -1", REMOVING_NEGATIVE, locker.removeItem(helmetSize3,-1));
        // remove successfully
        assertEquals("Need to be 0", SUCCESS,locker.removeItem(helmetSize3,1));
        locker.addItem(helmetSize1,3);
        // less than 4 Items of helmet Size 1 in the locker.
        assertEquals("Need to be -1", REMOVING_NEGATIVE,locker.removeItem(helmetSize1,4));
    }

    @Test
    /**
     * This method check the method getItemCount of locker class.
     */
    public void testGetItemCount(){
        locker.addItem(helmetSize3,3);
        assertEquals("Need to be 3", 3,locker.getItemCount(helmetSize3.getType()));
        // adding 3 more, should cause 7 move to long term, 4 stay in the locker.
        locker.addItem(helmetSize3,8);
        assertEquals("Need to be 4", 4,locker.getItemCount(helmetSize3.getType()));
        locker.addItem(helmetSize1,3);
        assertEquals("Need to be 3", 3,locker.getItemCount(helmetSize1.getType()));
        assertEquals("Need to be 0", 0,locker.getItemCount(football.getType()));
    }

    @Test
    /**
     * This method check the method getInventory of locker class.
     */
    public void testGetInventory(){
        HashMap<String,Integer> map = new HashMap<>();

        locker.addItem(helmetSize3,1);
        map.put("helmet, size 3",1);
        assertEquals(map,locker.getInventory());

        locker.addItem(helmetSize3,2);
        map.put("helmet, size 3",3);
        assertEquals(map,locker.getInventory());

        locker.addItem(football,1);
        map.put("football",1);
        assertEquals(map,locker.getInventory());
    }

    @Test
    /**
     * This method check the method getCapacity of locker class.
     */
    public void testGetCapacity(){
        assertEquals("Need to be 100",LOCKER_BIG_CAPACITY, locker.getCapacity());
        assertEquals("Need to be 100",LOCKER_SMALL_CAPACITY, locker2.getCapacity());
    }

    @Test
    /**
     * This method check the method getAvailableCapacity of locker class.
     */
    public void testGetAvailableCapacity(){
        assertEquals("Need to be 100",LOCKER_BIG_CAPACITY,locker.getAvailableCapacity());
        locker.addItem(sporesEngine,2);
        assertEquals("Need to be 80",80,locker.getAvailableCapacity());
        locker.addItem(helmetSize3,2);
        assertEquals("Need to be 70",70,locker.getAvailableCapacity());
        locker.removeItem(helmetSize3,1);
        assertEquals("Need to be 75",75,locker.getAvailableCapacity());
        locker.addItem(sporesEngine,4);
        assertEquals("Need to be 75",75,locker.getAvailableCapacity());
    }
}
