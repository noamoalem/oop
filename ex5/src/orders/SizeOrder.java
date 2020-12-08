package orders;

import java.io.File;
import java.util.Comparator;

/**
 * This class compare between two files according to their size.
 */
public class SizeOrder implements Comparator<File> {

    private final static int NEGATIVE = -1;
    private final static int POSITIVE = 1;
    private final static int ZERO = 0;
    /**
     * This method compare between two files according to their size.
     * @param file1 first file.
     * @param file2 second file.
     * @return 0 if the file size equal, 1 if the first size smaller then the second, -1 if the first size is
     * bigger then the second
     */
    @Override
    public int compare(File file1, File file2) {
        if (file1.length()<file2.length()){
            return POSITIVE;
        }
        if (file1.length()>file2.length()){
            return NEGATIVE;
        }
        if (file1.getAbsolutePath().compareTo(file2.getAbsolutePath())<ZERO){
            return POSITIVE;
        }
        if (file1.getAbsolutePath().compareTo(file2.getAbsolutePath())>ZERO){
            return NEGATIVE;
        }
        return ZERO;
    }
}
