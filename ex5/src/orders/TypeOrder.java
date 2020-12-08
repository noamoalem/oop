package orders;

import java.io.File;
import java.util.Comparator;

/**
 * This class compare between two files according to their type.
 */
public class TypeOrder implements Comparator<File> {
    private final static int NEGATIVE = -1;
    private final static int POSITIVE = 1;
    private final static int ZERO = 0;
    private final String EMPTY_STR = "";

    /**
     * This method compare between two files according to their type by alphabet order.
     * @param file1 first file.
     * @param file2 second file.
     * @return 0 if the file name equal, 1 if the first smaller then the second, -1 if the first bigger
     * then the second.
     */
    @Override
    public int compare(File file1, File file2) {
        if (getSuffix(file1).compareTo(getSuffix(file2))<ZERO){
            return POSITIVE;
        }
        if (getSuffix(file1).compareTo(getSuffix(file2))>ZERO){
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

    /**
     * This method find the type of given file.
     * @param file given file.
     * @return string of the file type.
     */
    private String getSuffix(File file){
        String name = file.getName();
        int suffix = name.lastIndexOf(".");
        if (suffix!=NEGATIVE && suffix!=ZERO) {
            return name.substring(suffix + 1);
        }
        return EMPTY_STR;
    }
}

