package orders;

import java.io.File;
import java.util.Comparator;

/**
 * This class compare between two files according to their name.
 */
public class AlphabetOrder implements Comparator<File> {
    private final static int NEGATIVE = -1;
    private final static int POSITIVE = 1;
    private final static int ZERO = 0;

    /**
     * This method compare between two files according to their name by alphabet order.
     * @param file1 first file.
     * @param file2 second file.
     * @return 0 if the file name equal, 1 if the first smaller then the second, -1 if the first bigger
     * then the second.
     */
    @Override
    public int compare(File file1, File file2) {
        if (file1.getAbsolutePath().compareTo(file2.getAbsolutePath())<ZERO){
            return POSITIVE;
        }
        if (file1.getAbsolutePath().compareTo(file2.getAbsolutePath())>ZERO){
            return NEGATIVE;
        }
        return 0;
    }
}

