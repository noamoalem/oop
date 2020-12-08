/**
 This class extend SimpleHashSet, implement close hash set.
 */
public class ClosedHashSet extends SimpleHashSet {
    private Object[] hashArray;
    private static final int NOT_FOUND = -1;
    private static final Object FLAG = new Object();

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet(){
        super();
        hashArray = new Object[INITIAL_CAPACITY];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor,lowerLoadFactor);
        hashArray = new Object[INITIAL_CAPACITY];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
     * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75),
     * and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data){
        super();
        hashArray = new Object[INITIAL_CAPACITY];
        for (String string: data){
            add(string);
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set.
     * @return False iff newValue already exists in the set, True otherwise.
     */
    @Override
    public boolean add(java.lang.String newValue) {
        if (contains(newValue)){
            return false;
        }
        if (!checkUpLoadFactor()){ // need increase capacity
            reSize(hashArray,"*");
        }
        int hashValue = newValue.hashCode();
        for (int i = 0; i<capacity(); i++){
            int number = (int)(i + Math.pow(i,2))/2;
            int indexToCheck = clamp(hashValue + number);
            if (hashArray[indexToCheck]  == null || hashArray[indexToCheck] == FLAG) {
                hashArray[indexToCheck] = newValue;
                sizeCounter++;
                return true;
            }
        }
        return true;
    }

    /**
     * Look for a specified value in the set
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set, False otherwise.
     */
    @Override
    public boolean contains(String searchVal) {
        if (searchVal == null || searchVal == FLAG){
            return false;
        }
        int hashValue = searchVal.hashCode();
        for (int i = 0; i<capacity(); i++) {
            int number = (int) (i + Math.pow(i, 2)) / 2;
            int indexToCheck = clamp(hashValue + number);
            if (hashArray[indexToCheck] == null) { //this place was never occupied
                return false;
            }
            if (hashArray[indexToCheck].equals(searchVal)) {
                return true;
            }

        }
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted, False otherwise.
     */
    @Override
    public boolean delete(String toDelete) {
        int toDeleteIndex = searchVal(toDelete);
        if (toDeleteIndex == NOT_FOUND){
            return false;
        }
        hashArray[toDeleteIndex] = FLAG; //delete the element
        sizeCounter--;
        if (checkLowLoadFactor()){
            reSize(hashArray,"/");
        }
        return true;
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
     * This method search if some string is in the hash and return the index.
     * @param val the string to search.
     * @return -1 if the string not in the hash, the string index otherwise.
     */
    private int searchVal(String val){
        int hashValue = val.hashCode();
        for (int i = 0; i<capacity(); i++){
            int number = (int)(i + Math.pow(i,2))/2;
            int indexToCheck = clamp(hashValue + number);
            if (contains(val)){
                if (hashArray[indexToCheck].equals(val)) {
                    return indexToCheck;
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * This method do the resize action while needed.
     * @param array the array to do the resize on.
     * @param action multiplication if action = "*", difference if action = "/".
     */
    private void reSize(Object[] array, String action){
        sizeCounter = 0;
        Object[] oldHashArray = array;
        if (action.equals("/")) {
            currentCapacity = oldHashArray.length/2;
            hashArray = new Object[oldHashArray.length/2];
        }
        if (action.equals("*")) {
            currentCapacity = oldHashArray.length * 2;
            hashArray = new Object[oldHashArray.length * 2];
            }
        for (Object object : oldHashArray) {
            if (object != null && object != FLAG){
                add((String)object);
            }
        }
    }
}

