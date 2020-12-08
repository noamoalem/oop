/**
This class extend SimpleHashSet, implement open hash set.
 */
public class OpenHashSet extends SimpleHashSet {

    private OpenHashingBucket[] hashArray;
    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet(){
        super();
        hashArray = new OpenHashingBucket[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor,lowerLoadFactor);
        hashArray = new OpenHashingBucket[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public OpenHashSet(String[] data){
        super();
        hashArray = new OpenHashingBucket[INITIAL_CAPACITY];
        for (String string: data){
            add(string);
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set, True otherwise.
     */
    @Override
    public boolean add(String newValue) {
        if (contains(newValue)){
            return false;
        }
        if (!checkUpLoadFactor()){
            reSize(hashArray,"*");
        }
        int hashValue = newValue.hashCode();
        if (hashArray[clamp(hashValue)] == null){
            hashArray[clamp(hashValue)] = new OpenHashingBucket();
        }
        hashArray[clamp(hashValue)].addFirst(newValue);
        sizeCounter++;

        return true;
    }

    /**
     * Look for a specified value in the set. using the method contain of linked list.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set, False otherwise.
     */
    @Override
    public boolean contains(String searchVal) {
        int hashValue = searchVal.hashCode();
        if (hashArray[clamp(hashValue)] == null){
            return false;
        }
        return hashArray[clamp(hashValue)].contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted, False otherwise
     */
    @Override
    public boolean delete(String toDelete) {
        if (contains(toDelete)){
            int hashValue = toDelete.hashCode();
            hashArray[clamp(hashValue)].remove(toDelete);
            if (hashArray[clamp(hashValue)].isEmpty()){
                hashArray[clamp(hashValue)] = null;
            }
            sizeCounter--;
            if (checkLowLoadFactor()){
                reSize(hashArray,"/");
            }
            return true;
        }
        return false;
    }

    /**
     * This method return the current size of the array.
     * @return The number of elements currently in the set.
     */
    @Override
    public int size() {
        return sizeCounter;
    }

    /**
     * This method do the resize action while needed.
     * @param array array the array to do the resize on.
     * @param action action multiplication if action = "*", difference if action = "/".
     */
    private void reSize(OpenHashingBucket[] array, String action){
        sizeCounter = 0;
        OpenHashingBucket[] oldHashArray = array;
        if (action.equals("/")) {
            currentCapacity = oldHashArray.length / 2;
            hashArray = new OpenHashingBucket[oldHashArray.length / 2];
        }
        if (action.equals("*")){
            currentCapacity = oldHashArray.length * 2;
            hashArray = new OpenHashingBucket[oldHashArray.length * 2];
        }
        for (OpenHashingBucket bucket: oldHashArray){
            if (bucket != null){
               for (String string:bucket){
                   int hashValue = string.hashCode();
                   if (hashArray[clamp(hashValue)] == null){
                       hashArray[clamp(hashValue)]= new OpenHashingBucket();
                   }
                   sizeCounter++;
                   hashArray[clamp(hashValue)].addFirst(string);
               }
            }
        }
    }
}