package filters;

import java.io.File;
import java.util.ArrayList;

/**
 * This class creat the required filter according to a given filter.
 */
public class FilterFactory {

    private static final String SAME_NAME = "file";
    private static final String CONTAINS = "contains";
    private static final String START_WITH = "prefix";
    private static final String END_WITH = "suffix";
    private static final String ALL = "all";
    private static final String WRITABLE = "writable";
    private static final String EXECUTABLE = "executable";
    private static final String HIDDEN = "hidden";
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final String GREATER_THEN = "greater_than";
    private static final String BETWEEN = "between";
    private static final String SMALLER_THEN = "smaller_than";
    private static final String NOT = "NOT";
    private String name;
    private String valueOne;
    private String valueTwo;
    private String valueThree;
    private boolean notSuffix;
    public int line;

    /**
     * The constructor.
     * separate the given filter.
     * @param filterName given filter.
     * @param l the line of the filter in the command file.
     */
     public FilterFactory(String filterName, int l){
        line=l;
        String[] filter = filterName.split("#");
        name = filter[0];
        if (filter.length>1){
            if (name.equals(ALL) && filter[1].equals(NOT)){
                notSuffix = true;
            }
            valueOne = filter[1];
            if (filter.length>2){
                if (filter[2].equals(NOT)) {
                    notSuffix = true;
                }
                valueTwo = filter[2];
                if (filter.length>3){
                    if (filter[3].equals(NOT)) {
                        notSuffix = true;
                    }
                    valueThree = filter[3];
                }
            }
        }
    }

    /**
     * This method play a filter on a given files array.
     * @param files given files array.
     * @return filtered files array.
     * @throws TypeOneExceptionFilter if the file name is not valid.
     */
    public ArrayList<File> play(ArrayList<File> files) throws TypeOneExceptionFilter {
        if (checkLegalNot()){
            switch (name){
                case SMALLER_THEN: case GREATER_THEN: case BETWEEN:
                    if (checkLegalValues()){
                        SizeFilter sizeFilter = new SizeFilter(name,valueOne,valueTwo,notSuffix,line);
                        return sizeFilter.filtered(files);
                    }
                    else throw new TypeOneExceptionFilter(line);
                case HIDDEN: case WRITABLE: case EXECUTABLE:
                    if (checkLegalYesNo()){
                        PermissionFilter permissionFilter = new PermissionFilter(name,valueOne,valueTwo,
                                notSuffix,line);
                        return permissionFilter.filtered(files);
                    }
                    else throw new TypeOneExceptionFilter(line);
                case END_WITH: case START_WITH: case SAME_NAME: case CONTAINS: case ALL:
                    nameFilter nameFilter = new nameFilter(name,valueOne,valueTwo,notSuffix,line);
                    return nameFilter.filtered(files);
                default:
                    throw new TypeOneExceptionFilter(line);
            }
        }
        throw new TypeOneExceptionFilter(line);
    }

    /**
     * This method check if the file check size given in are valid.
     * @return true if its legal exception otherwise.
     * @throws TypeOneExceptionFilter if we got wrong size check value.
     */
    private Boolean checkLegalValues() throws TypeOneExceptionFilter {
        if (Double.parseDouble(valueOne)>=0){
            if (name.equals(BETWEEN)){
                if (Double.parseDouble(valueTwo)>=0){
                    if (Double.parseDouble(valueOne)<=Double.parseDouble(valueTwo)){
                        return true;
                    }
                    else throw new TypeOneExceptionFilter(line);
                }
                else throw new TypeOneExceptionFilter(line);
            }
            return true;
        }
        else throw new TypeOneExceptionFilter(line);
    }

    /**
     * This method check if yes or no given in filter are valid.
     * @return true if its legal exception otherwise.
     * @throws TypeOneExceptionFilter if we got wrong filter value.
     */
    private Boolean checkLegalYesNo() throws TypeOneExceptionFilter {
        if (valueOne!= null){
            if (valueOne.equals(YES)||valueOne.equals(NO)){
                return true;
            }
        }
        throw new TypeOneExceptionFilter(line);
    }

    /**
     * This method check if the not suffix given in filter are valid.
     * @return true if its legal exception otherwise.
     * @throws TypeOneExceptionFilter if we got wrong not value.
     */
    private Boolean checkLegalNot() throws TypeOneExceptionFilter {
        switch (name){
            case GREATER_THEN: case SMALLER_THEN: case SAME_NAME: case CONTAINS: case END_WITH:
                case START_WITH: case WRITABLE: case EXECUTABLE: case HIDDEN:
                if (valueTwo!=null){
                    if (valueTwo.equals(NOT)){
                        return true;
                    }
                    throw new TypeOneExceptionFilter(line);
                }
                break;

            case BETWEEN:
                if (valueThree!=null){
                    if (valueThree.equals(NOT)) {
                        return true;
                    }
                    throw new TypeOneExceptionFilter(line);
                }
                break;
            case ALL:
                if (valueOne!=null){
                    if (valueOne.equals(NOT)) {
                        return true;
                    }
                    throw new TypeOneExceptionFilter(line);
                }
        }
        return true;
    }
}
