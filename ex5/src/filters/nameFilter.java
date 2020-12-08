package filters;

import java.io.File;
import java.util.ArrayList;

/**
 * This class implement the filters: ﬁle, contains, prefix, suffix, all.
 */
class nameFilter {
    private static final String SAME_NAME = "file";
    private static final String CONTAINS = "contains";
    private static final String START_WITH = "prefix";
    private static final String END_WITH = "suffix";
    private static final String ALL = "all";
    private String name;
    private String valueOne;
    private String valueTwo;
    private boolean notSuffix;
    public int line;

    /**
     * The constructor. initialize a nameFilter object.
     * @param filterName the name of the filter.
     * @param val1 the first parameter of the filter.
     * @param val2 the second parameter of the filter.
     * @param suffix if the filter has not suffix.
     * @param l the line in the command file.
     */
    nameFilter(String filterName, String val1, String val2, boolean suffix, int l) {
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
        if (name.equals(ALL)){
            return allFilter(files);
        }
        if (name.equals(SAME_NAME)){
            return equalName(files);
        }
        if (name.equals(CONTAINS)){
            return contain(files);
        }
        if (name.equals(START_WITH)){
            return prefix(files);
        }
        if (name.equals(END_WITH)){
            return suffix(files);
        }
        return null;
    }

    /**
     * This method filter the files as if their name equal to given name.
     * @param files all the files in source directory.
     * @return Filtered files array.
     */
    private ArrayList<File> equalName(ArrayList<File> files) {
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix) {
            for (File file : files) {
                if (!file.getName().equals(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        } else {
            for (File file : files) {
                if (file.getName().equals(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        return filteredFiles;
    }

    /**
     * This method filter the files as if their name contain to given string.
     * @param files all the files in source directory.
     * @return Filtered files array.
     */
    private ArrayList<File> contain(ArrayList<File> files) {
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix) {
            for (File file : files) {
                if (!file.getName().contains(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        else {

            for (File file : files) {
                if (file.getName().contains(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        return filteredFiles;
    }

    /**
     * This method filter the files as if their name start with given string.
     * @param files all the files in source directory.
     * @return Filtered files array.
     */
    private ArrayList<File> prefix(ArrayList<File> files) {
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix) {
            for (File file : files) {
                if (!file.getName().startsWith(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        else {
            for (File file : files) {
                if (file.getName().startsWith(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        return filteredFiles;
    }

    /**
     * This method filter the files as if their name end with given string.
     * @param files all the files in source directory.
     * @return Filtered files array.
     */
    private ArrayList<File> suffix(ArrayList<File> files) {
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix) {
            for (File file : files) {
                if (!file.getName().endsWith(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        else {
            for (File file : files) {
                if (file.getName().endsWith(valueOne)) {
                    filteredFiles.add(file);
                }
            }
        }
        return filteredFiles;
    }

    /**
     * This method implements the all filter.
     * @param files all the files in source directory.
     * @return Filtered files array.
     */
    private ArrayList<File> allFilter(ArrayList<File> files) {
        if (notSuffix){
            return null;
        }
        return files;
    }
}
