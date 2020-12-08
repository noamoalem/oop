import oop.ex3.spaceship.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The Locker class implements a Locker object that has capacity, long-term storage that related to the
 * locker.
 */
public class Locker {
    private static final int HASMOVED = 1;
    private static final int SUCCESS = 0;
    private static final int NOPLACE = -1;
    private static final int CONSTRAINT = -2;
    private static final int NONEEDLONGSTORAGE = 2;
    private static final double TWENTY_PERCENT_LIMIT = 0.2;
    private static final double FIFTHY_PERCENT_LIMIT = 0.5;
    private static final int POSITIVE_NUMBER = 0;
    private final int capacity;
    private final int maxVolSameType;
    private int taken;
    private Map<String, Integer> lockerMap;
    private LongTermStorage longTermStorage;
    private Item[][] constraintsItems;


    /**
     * The constructor. initializes a Locker object with the given capacity.
     *
     * @param capacity The total amount of storage units this locker can hold.
     */
    public Locker(LongTermStorage lts, int capacity, Item[][] constraints) {
        this.capacity = capacity;
        longTermStorage = lts;
        constraintsItems = constraints;
        maxVolSameType = (int) Math.floor((capacity * TWENTY_PERCENT_LIMIT));
        taken = POSITIVE_NUMBER;
        lockerMap = new HashMap<>();
    }

    /**
     * This method adds n Items of the given type to the locker.
     *
     * @param item The item wanted to add.
     * @param n    The number of items wanted to add.
     * @return 0 - The addition was successful, 1 - This action causes n Items to be moved to long-term
     * storage , -1 no room for n Items in the short/long term locker.
     */
    public int addItem(Item item, int n) {
        int sumVolOfItem = (getItemCount(item.getType()) + n) * item.getVolume(); // The volume of given item
        // in the locker plus the volume of the amount wanted to be stored.

        if (item.getVolume()*n > getAvailableCapacity()){
            System.out.println("Error: Your request cannot be completed at this time. Problem: no room for "
                    + n + " items of type " + item.getType());
            return NOPLACE;
        }
        if (n<=0){
            System.out.println("Error: Your request cannot be completed at this time");
            return NOPLACE;
        }
        if (checkConstraint(item)) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: the locker " +
                    "cannot contain items of type " +item.getType()+ " ,as it contains a contradicting item");
            return CONSTRAINT;
        }

        if (moveToLong(item, n) == SUCCESS) { // We need and can move items to the long term storage.
            if (getItemCount(item.getType())== SUCCESS && item.getVolume() < this.getCapacity()){
                taken += item.getVolume()*(int) (n*TWENTY_PERCENT_LIMIT);
                lockerMap.put(item.getType(), (int) (n*TWENTY_PERCENT_LIMIT));
            }
            int amountToAdd = (maxVolSameType -
                    this.getItemCount(item.getType())*item.getVolume())/item.getVolume();
            if (amountToAdd>POSITIVE_NUMBER){
                // so we need to add more to get to the 20%
                taken += amountToAdd*item.getVolume();
                lockerMap.put(item.getType(),amountToAdd+this.getItemCount(item.getType()));
            }

            removeItem(item, (getItemCount(item.getType())) - (maxVolSameType / item.getVolume()));
            longTermStorage.addItem(item, volToItem(item, sumVolOfItem - maxVolSameType));
            System.out.println("Warning: Action successful, but has caused items to be moved to storage");
            return HASMOVED;// Items moved successfully to the long term storage.
        }

        if (moveToLong(item, n) == NOPLACE) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: no room for " +
                    n + " items of type " + item.getType());
            return NOPLACE; // this action requires Items to be moved to long-term storage,
            // but it doesnt have room to accommodate all n Items.
        }
        if ((moveToLong(item, n) == NONEEDLONGSTORAGE) && item.getVolume() * n <= getAvailableCapacity()) {
            taken += item.getVolume() * n;  // adding the items.
            lockerMap.put(item.getType(), this.getItemCount(item.getType())+n);
            return SUCCESS; // Items moved successfully and doesnt cause Items to be moved to long-term
            // storage
        } else {
            System.out.println("Error: Your request cannot be completed at this time. Problem: no room for "
                    + n + " items of type " + item.getType());
            return NOPLACE; // n Items cannot be added to the locker at this time.
        }
    }

    /**
     * This method removes n Items of the type type from the locker.
     *
     * @param item The item wanted to remove.
     * @param n  The number of items wanted to remove.
     * @return 0 - The removing was successful, -1 - The given number to remove is negative/bigger then whats
     * in the locker.
     */
    public int removeItem(Item item, int n) {
        if (n > getItemCount(item.getType())) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: the locker " +
                    "does not contain " + n + " items of type " + item.getType());
            return NOPLACE;
        }
        if (n <= POSITIVE_NUMBER) {
            System.out.println("Error: Your request cannot be completed at this time. Problem: cannot remove "
                    + "a negative number of items of type " + item.getType());
            return NOPLACE;
        }
        taken -= item.getVolume() * n;
        lockerMap.remove(item.getType(),n);
        if (lockerMap.containsKey(item.getType())){
            if (lockerMap.get(item.getType())== POSITIVE_NUMBER){
                lockerMap.remove(item.getType());
            }
        }
        return SUCCESS;
    }

    /**
     * This method returns the number of Items of type type the locker contains.
     *
     * @param type The type to get is count.
     * @return The number of Items of type the locker contains.
     */
    public int getItemCount(String type) {
            if (lockerMap.containsKey(type)) {
                return lockerMap.get(type);
            }
            return POSITIVE_NUMBER;
    }

    /**
     * This method returns a map of all the item types contained in the locker, and their quantities.
     *
     * @return The locker Map.
     */
    public Map<String, Integer> getInventory() {
        return lockerMap;
    }

    /**
     * This method returns the locker total capacity.
     *
     * @return the locker total capacity.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * This method returns the locker available capacity.
     *
     * @return the locker available capacity.
     */
    public int getAvailableCapacity() {
        return capacity - taken;
    }

    /**
     * This method check if n Items should be moved to the long term locker.
     *
     * @param item The item wanted to add to the locker.
     * @param n The number of items wanted to add.
     * @return 0 - The items need and can move to the long term locker.
     *        -1 - some items need to be moved but there is no place in the long term locker.
     *         2 - no item needed to be moved.
     */
    private int moveToLong(Item item, int n) {
        //The volume of given item in the locker plus the volume of the amount wanted to be stored.
        int sumVolOfItem = (getItemCount(item.getType()) + n) * item.getVolume();
        // Check if items of a specific type take up more than 50% of the storage.
        if ((sumVolOfItem) > (int) Math.floor(capacity * FIFTHY_PERCENT_LIMIT)) {
            if (longTermStorage.getAvailableCapacity() >= (sumVolOfItem - maxVolSameType)) {
                return SUCCESS; //There is available place in the long term storage, item need to move there
            }
            return NOPLACE;
        }
        return NONEEDLONGSTORAGE;
    }

    /**
     * This method convert from a given volume of item to the number of items.
     *
     * @param item   a given item.
     * @param volume The volume of the items together.
     * @return The number of items.
     */
    private int volToItem(Item item, int volume) {
        return (int) Math.ceil((double)volume / item.getVolume());
    }

    /**
     * This method check if ll bat in the locker,
     * and otherwise.
     *
     * @param item The item wanted to be added.
     * @return true if constraint will append, false otherwise.
     */
    boolean checkConstraint(Item item) {
        for (int i = 0; i < constraintsItems.length; i++) {
            if (constraintsItems[i][0].getType().equals(item.getType())) {

                if (lockerMap.containsKey(constraintsItems[i][1].getType())) {

                    if (lockerMap.get(constraintsItems[i][1].getType()) > POSITIVE_NUMBER) {

                        return true;
                    }
                }
            }
            if (constraintsItems[i][1].getType().equals(item.getType())) {
                if (lockerMap.containsKey(constraintsItems[i][0].getType())) {
                    if (lockerMap.get(constraintsItems[i][0].getType()) > POSITIVE_NUMBER) {
                        return true;
                    }
                }
            }
        }
        return false; //Item not in constraints list.
    }
}
