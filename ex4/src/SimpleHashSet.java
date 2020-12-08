/**
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 */
public abstract class SimpleHashSet implements SimpleSet {

    private static final float DEFAULT_HIGHER_CAPACITY = 0.75f;
    private static final float DEFAULT_LOWER_CAPACITY = 0.25f;
    private static final int LOWEST_CAPACITY = 1;
    protected static final int INITIAL_CAPACITY = 16;
    protected float upLoadFactor;
    protected float lowLoadFactor;
    protected int startCapacity;
    protected int currentCapacity;
    protected int sizeCounter = 0;

    /**
     * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and
     * DEFAULT_HIGHER_CAPACITY.
     */
    protected SimpleHashSet(){
        upLoadFactor = DEFAULT_HIGHER_CAPACITY;
        lowLoadFactor = DEFAULT_LOWER_CAPACITY;
        startCapacity = INITIAL_CAPACITY;
        currentCapacity = INITIAL_CAPACITY;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY.
     * @param upperLoadFactor The upper load factor before rehashing.
     * @param lowerLoadFactor The lower load factor before rehashing.
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.upLoadFactor = upperLoadFactor;
        this.lowLoadFactor = lowerLoadFactor;
        startCapacity = INITIAL_CAPACITY;
        currentCapacity = INITIAL_CAPACITY;
    }

    /**
     * This method return the current size of the array.
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity(){
        return currentCapacity;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity.
     * @param index The index before clamping.
     * @return an index properly clamped.
     */
    protected int clamp(int index){
        return index & (capacity()-1);
    }

    /**
     *
     * @return The lower load factor of the table
     */
    protected float getLowerLoadFactor() {
        return lowLoadFactor;
    }

    /**
     *
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor(){
        return upLoadFactor;
    }

    /**
     * This method check if the hash table need increase.
     * @return true if it doesnt need , false otherwise.
     */
    protected boolean checkUpLoadFactor(){
        return ((float)this.size()+1)/this.capacity() <= getUpperLoadFactor();
        }

    /**
     * This method check if the hash table need decrease.
     * @return true if it needs , false otherwise.
     */
    protected boolean checkLowLoadFactor(){
        if (capacity() == LOWEST_CAPACITY){
            return false;
        }
        return ((float)this.size()/this.capacity()) < getLowerLoadFactor();

    }



}
