package filters;
import java.io.File;
import java.util.ArrayList;

/**
 * This class filter files according to their size.
 */
class SizeFilter {
    private static final String GREATER_THEN = "greater_than";
    private static final String BETWEEN = "between";
    private static final String SMALLER_THEN = "smaller_than";
    private static final int bytes = 1024;
    private String name;
    private String valueOne;
    private String valueTwo;
    private boolean notSuffix;
    public int line;

    /**
     * The constructor. initialize a sizeFilter object.
     * @param filterName the name of the filter.
     * @param val1 the first parameter of the filter.
     * @param val2 the second parameter of the filter.
     * @param suffix if the filter has not suffi
     * @param l the line in the command file.
     */
    SizeFilter(String filterName, String val1, String val2, boolean suffix, int l) {
        name = filterName;
        valueOne = val1;
        valueTwo = val2;
        notSuffix = suffix;
        line=l;
    }

    /**
     * This method filter the files.
     * @param files all the files in source directory.
     * @return Filtered files array.
     */
    ArrayList<File> filtered(ArrayList<File> files) {
        if (name.equals(GREATER_THEN)){
            return greaterThen(files);
        }
        if (name.equals(SMALLER_THEN)){
            return smallerThen(files);
        }
        return between(files);
    }

    /**
     * This method filter the files as if their size is bigger then some value.
     * @param files all the files
     * @return Filtered files array.
     */
    private ArrayList<File> greaterThen(ArrayList<File> files){
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix){
            for (File file : files) {
                if ((double) file.length() / bytes <= Double.parseDouble(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        else {
            for (File file : files) {
                if ((double) file.length() / bytes > Double.parseDouble(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        return filteredFiles;
    }

    /**
     * This method filter the files as if their size is smaller then some value.
     * @param files all the files
     * @return Filtered files array.
     */
    private ArrayList<File> smallerThen(ArrayList<File> files){
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix){
            for (File file : files) {
                if ((double) file.length() / bytes >=  Double.parseDouble(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        else {
            for (File file : files) {
                if ((double) file.length() / bytes <  Double.parseDouble(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        return filteredFiles;
    }

    /**
     * This method filter the files as if their size is between some values.
     * @param files all the files.
     * @return Filtered files array.
     */
    private ArrayList<File> between(ArrayList<File> files){
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix){
            for (File file : files) {
                if ((double) file.length() / bytes <  Double.parseDouble(valueOne) || (double) file.length()
                        / bytes >  Double.parseDouble(valueTwo)){
                    filteredFiles.add(file);
                }
            }

        }
        else {
            for (File file : files) {
                if (((double) file.length() / bytes) >=  Double.parseDouble(valueOne) && (double)file.length()
                        / bytes <=  Double.parseDouble(valueTwo)){
                    filteredFiles.add(file);
                }
            }
        }
        return filteredFiles;
    }
}

