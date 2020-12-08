import oop.ex3.spaceship.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The LongTermStorage class implements a Long-Term Storage object that has capacity of 1000.
 *
 */
public class LongTermStorage {
    private static final int NOPLACE = -1;
    private static final int SUCCESS = 0;
    private static final int CAPACITY = 1000;
    private int teaken;
    private Map<String, Integer> longLockerMap;

    /**
     * The constructor, initialize an empty long-term storage with 1000 capacity.
     */
    public LongTermStorage(){
        teaken = 0;
        longLockerMap = new HashMap<>();
    }

    /**
     * This method adds n Items of the given type to the long- term storage unit
     * @param item The item wanted to add to the locker.
     * @param n The number of items wanted to add.
     * @return 0 - The items add successfully , -1 - The items not add.
     */
    public int addItem(Item item, int n){
        if (n<=SUCCESS){
            System.out.println("Error: Your request cannot be completed at this time.");
            return NOPLACE;
        }
        if (item.getVolume()*n <= getAvailableCapacity()){
            teaken += item.getVolume()*n;
            longLockerMap.put(item.getType(), n+this.getItemCount(item.getType()));
            return SUCCESS;
        }
        System.out.println("Error: Your request cannot be completed at this time. Problem: no room for " +n+
                " Items of " +item.getType()+ " type");
        return NOPLACE;
    }

    /**
     * This method resets the long-term storage inventory.
     */
    public void resetInventory(){
        teaken = 0;
        longLockerMap.clear();
    }

    /**
     * This method returns the number of Items of type type in the long-term storage contains.
     * @param type The item type.
     * @return The number of items of type type in the long-term storage.
     */
    public int getItemCount(String type){
        if (longLockerMap.containsKey(type)) {
            return longLockerMap.get(type);
        }
        return SUCCESS;
    }

    /**
     * This method returns a map of all the Items contained in the long-term storage unit,
     * and their respective quantities.
     * @return A map of all the Items contained in the long-term storage.
     */
    public Map<String, Integer> getInventory(){
        return longLockerMap;
    }

    /**
     * This method returns the long-term storage total capacity.
     * @return The long-term storage total capacity.
     */
    public int getCapacity(){
        return CAPACITY;
    }

    /**
     * This method returns the long-term storage available capacity,
     * @return The long-term storage available capacity,
     */
    public int getAvailableCapacity(){
        return CAPACITY-teaken;
    }
}
