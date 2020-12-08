import oop.ex3.spaceship.*;
import org.junit.*;
import java.util.HashMap;
import static org.junit.Assert.*;
/**
 * The LongTermTest class implements a tests of the LongTermStorage class methods.
 */
public class LongTermTest {
    private static Item baseballBat = ItemFactory.createSingleItem("baseball bat");
    private static Item helmetSize1 = ItemFactory.createSingleItem("helmet, size 1");
    private static Item helmetSize3 = ItemFactory.createSingleItem("helmet, size 3");
    private static Item sporesEngine = ItemFactory.createSingleItem("spores engine");
    private static Item football = ItemFactory.createSingleItem("football");
    private static final int NOPLACE = -1;
    private static final int SUCCESS = 0;
    private static final int ADDING_NEGATIVE = -1;
    private static final int CAPACITY = 1000;
    private LongTermStorage longTerm;

    @Before
    /**
     * This method initialize LongTermStorage object. to use this object in the tests.
     */
    public void createTestObjects(){
        longTerm = new LongTermStorage();
        longTerm = new LongTermStorage();
        HashMap map = new HashMap<String, Integer>();
    }

    @Test
    /**
     * This method check the method addItem of Long-Term Storage class.
     */
    public void testAddItem(){
        // adding 100 helmet size 1,volume 3.
        assertEquals("Need to be 0",SUCCESS, longTerm.addItem(helmetSize1,100));
        assertEquals("Need to be 0",SUCCESS,longTerm.addItem(sporesEngine,70));
        // locker is full
        assertEquals("Need to be -1",NOPLACE, longTerm.addItem(baseballBat,1));
        // adding n<= 0 items.
        assertEquals("Need to be -1",ADDING_NEGATIVE, longTerm.addItem(baseballBat,0));
        assertEquals("Need to be -1",ADDING_NEGATIVE, longTerm.addItem(baseballBat,-1));
    }

    @Test
    /**
     * This method check the method resetInventory of Long-Term Storage class.
     */
    public void testResetInventory(){
        HashMap map = new HashMap<String, Integer>();
        assertEquals("Need to be 0",SUCCESS, longTerm.addItem(helmetSize1,100));
        longTerm.resetInventory();
        // available capacity need to be 1000.
        assertEquals("Need to be 1000",CAPACITY, longTerm.getAvailableCapacity());
        // map is empty, long term locker map need to be empty.
        assertEquals(map,longTerm.getInventory());
    }

    @Test
    /**
     * This method check the method getItemCount of Long-Term Storage class.
     */
    public void testGetItemCount(){
        longTerm.addItem(baseballBat,10);
        assertEquals("Need to be 10",10,longTerm.getItemCount(baseballBat.getType()));
        longTerm.addItem(baseballBat,10);
        assertEquals("Need to be 20",20,longTerm.getItemCount(baseballBat.getType()));
        longTerm.addItem(football,7);
        assertEquals("Need to be 7",7,longTerm.getItemCount(football.getType()));
    }

    @Test
    /**
     * This method check the method getInventory of Long-Term Storage class.
     */
    public void testGetInventory(){
        HashMap<String,Integer> map = new HashMap<>();

        longTerm.addItem(helmetSize1,1);
        map.put("helmet, size 1",1);
        assertEquals(map,longTerm.getInventory());

        longTerm.addItem(helmetSize3,2);
        map.put("helmet, size 3",2);
        assertEquals(map,longTerm.getInventory());

        longTerm.addItem(helmetSize3,2);
        map.put("helmet, size 3",4);
        assertEquals(map,longTerm.getInventory());

        longTerm.addItem(football,1);
        map.put("football",1);
        assertEquals(map,longTerm.getInventory());
    }

    @Test
    /**
     * This method check the method getCapacity of Long-Term Storage class.
     */
    public void testGetCapacity(){
        assertEquals("Need to be 1000",CAPACITY, longTerm.getCapacity());
    }

    @Test
    /**
     * This method check the method getAvailableCapacity of Long-Term Storage class.
     */
    public void testGetAvailableCapacity(){
        assertEquals("Need to be 1000",CAPACITY,longTerm.getAvailableCapacity());
        // adding 10 helmet size 1,volume 3.
        longTerm.addItem(helmetSize1,10);
        assertEquals("Need to be 970",970,longTerm.getAvailableCapacity());

        // adding 100 helmet, size 3 ,volume: 5.
        longTerm.addItem(helmetSize3,100);
        assertEquals("Need to be 470",470,longTerm.getAvailableCapacity());

        longTerm.addItem(helmetSize3,94);
        //locker ia full
        assertEquals("Need to be 0",0,longTerm.getAvailableCapacity());

    }
}
