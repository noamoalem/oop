import java.util.LinkedList;

/**
 * This class used for the buckets for the open hash array.
 */
public class OpenHashingBucket extends LinkedList<String> {
    public LinkedList bucket;

    /**
     * Constructs a new, empty linked list to use as a bucket in the open hash array.
     */
    public OpenHashingBucket(){
        bucket = new LinkedList();
    }
}
