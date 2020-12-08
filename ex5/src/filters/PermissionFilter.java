package filters;
import java.io.File;
import java.util.ArrayList;

/**
 * This class filter files according to their permission.
 */
class PermissionFilter {
    private static final String WRITABLE = "writable";
    private static final String EXECUTABLE = "executable";
    private static final String YES = "YES";
    private static final String NO = "NO";
    private String name;
    private String valueOne;
    private String valueTwo;
    private boolean notSuffix;
    public int line;

    /**
     * The constructor. initialize a permissionFilter object.
     * @param filterName the name of the filter.
     * @param val1 the first parameter of the filter.
     * @param val2 the second parameter of the filter.
     * @param suffix suffix if the filter has not suffix.
     * @param l l the line in the command file.
     */
    PermissionFilter(String filterName, String val1, String val2, boolean suffix, int l) {
        name = filterName;
        valueOne = val1;
        valueTwo = val2;
        notSuffix = suffix;
        line=l;
    }

    /**
     * This method filter the files.
     * @param files all the files in source directory..
     * @return Filtered files array.
     */
    ArrayList<File> filtered(ArrayList<File> files) {
        if (name.equals(WRITABLE)){
            return writable(files);
        }
        if (name.equals(EXECUTABLE)){
            return executable(files);
        }
        return hidden(files);
    }

    /**
     * This method filter the files according to their write permissions.
     * @param files all the files in source directory.
     * @return Filtered files array.
     */
    private ArrayList<File> writable(ArrayList<File> files) {
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix){
            for (File file : files) {
                if (valueOne.equals(YES)) {
                    if (!file.canWrite()){
                        filteredFiles.add(file);
                    }
                }
                if (valueOne.equals(NO)) {
                    if (file.canWrite()){
                        filteredFiles.add(file);
                    }
                }
            }
        }
        else {
            for (File file : files) {
                if (valueOne.equals(YES)) {
                    if (file.canWrite()) {
                        filteredFiles.add(file);
                    }
                }
                if (valueOne.equals(NO)) {
                    if (!file.canWrite()) {
                        filteredFiles.add(file);
                    }
                }
             }
        }
        return filteredFiles;
    }

    /**
     * This method filter the files according to their execute permissions.
     * @param files all the files in source directory.
     * @return Filtered files array.
     */
    private ArrayList<File> executable(ArrayList<File> files) {
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix){
            for (File file : files) {
                if (valueOne.equals(YES)) {
                    if (!file.canExecute()){
                        filteredFiles.add(file);
                    }
                }
                if (valueOne.equals(NO)) {
                    if (file.canExecute()){
                        filteredFiles.add(file);
                    }
                }
            }
        }
        else {
            for (File file : files) {
                if (valueOne.equals(YES)) {
                    if (file.canExecute()) {
                        filteredFiles.add(file);
                    }
                }
                if (valueOne.equals(NO)) {
                    if (!file.canExecute()) {
                        filteredFiles.add(file);
                    }
                }
            }
        }
        return filteredFiles;
    }

    /**
     * This method filter the files if hide or not.
     * @param files all the files in source directory.
     * @return Filtered files array.
     */
    private ArrayList<File> hidden(ArrayList<File> files) {
        ArrayList<File> filteredFiles = new ArrayList<>();
        if (notSuffix){
            for (File file : files) {
                if (valueOne.equals(YES)) {
                    if (!file.isHidden()){
                        filteredFiles.add(file);
                    }
                }
                if (valueOne.equals(NO)) {
                    if (file.isHidden()){
                        filteredFiles.add(file);
                    }
                }
            }
        }
        else {
            for (File file : files) {
                if (valueOne.equals(YES)) {
                    if (file.isHidden()) {
                        filteredFiles.add(file);
                    }
                }
                if (valueOne.equals(NO)) {
                    if (!file.isHidden()) {
                        filteredFiles.add(file);
                    }
                }
            }
        }
        return filteredFiles;
    }




}
