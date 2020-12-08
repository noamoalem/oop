/**
 * Wraps an underlying Collection and serves to both simplify its API and give it a common type with the
 * implemented SimpleHashSets.
 */
public class CollectionFacadeSet implements SimpleSet {

    protected java.util.Collection<java.lang.String> collection;

    /**
     * Creates a new facade wrapping the specified collection.
     * @param collection The Collection to wrap
     */
    public CollectionFacadeSet(java.util.Collection<java.lang.String> collection){
        this.collection = collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set, True otherwise.
     */
    @Override
    public boolean add(String newValue) {
        if (collection.contains(newValue)) {
            return false;
        }
        collection.add(newValue);
        return true;
        //return collection.add(newValue);
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set, False otherwise.
     */
    @Override
    public boolean contains(String searchVal) {
        if (collection.contains(searchVal)){
            return true;
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
        return collection.remove(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return collection.size();
    }
}
