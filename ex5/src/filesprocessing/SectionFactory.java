package filesprocessing;
import filters.FilterFactory;
import orders.OrderFactory;
import java.io.*;
import java.util.ArrayList;

/**
 * This class creat section objects.
 */
public class SectionFactory {
    private static final int MINIMAL_COMMAND_SIZE = 3;
    private static final String FILTER = "FILTER";
    private static final String ORDER = "ORDER";
    private static final String MISSING_SUB_SECTION = " sub section missing";
    private static final String INVALID_SUB_SECTION = " sub section name invalid";
    private static final String BAD_SUB_SECTION = "bad sub section";
    private File file = null;
    private ArrayList<String> fileLiness;
    private int currentLine = 0;

    /**
     * The constructor.
     * @param lines the command file lines.
     */
    public SectionFactory(ArrayList<String> lines) {
        fileLiness = lines;
    }

    /**
     * This method creat all the section objects required according to the command file lines.
     * @return array with all the section objects.
     * @throws TypeTwoException if there was a problem with some section.
     */
    ArrayList<SectionCreatAndPlay> creatAllSections() throws TypeTwoException {
        FilterFactory filterFactory = null;
        OrderFactory order = null;
        ArrayList<SectionCreatAndPlay> sectionsArray = new ArrayList<>();
        if (fileLiness.size()<MINIMAL_COMMAND_SIZE){
            throw new TypeTwoException(BAD_SUB_SECTION);
        }
        while (currentLine+1<fileLiness.size()) {
            filterFactory = checkFilter();
            if(currentLine+1<fileLiness.size()){
                order = checkOrder();
            }
            sectionsArray.add(new SectionCreatAndPlay(filterFactory, order));
        }
        return sectionsArray;
    }

    /**
     * This method check if the filter part in a sub section is valid.
     * @return FilterFactory object.
     * @throws TypeTwoException it there was a problem in the filter part of the sub section.
     */
    private FilterFactory checkFilter() throws TypeTwoException{
        if (currentLine!=0){
            currentLine++;
        }
        if ((fileLiness.get(currentLine).equals(ORDER))){
            throw new TypeTwoException(FILTER+MISSING_SUB_SECTION);
        }
        if (fileLiness.get(fileLiness.size()-1).equals(FILTER)||fileLiness.get(fileLiness.size()-2).
                equals(FILTER)){
            throw new TypeTwoException(BAD_SUB_SECTION);
        }
        if (fileLiness.get(currentLine).equals(FILTER)){
            currentLine++;
            return new FilterFactory(fileLiness.get(currentLine),currentLine+1);
        }
        throw new TypeTwoException(FILTER+INVALID_SUB_SECTION);
    }

    /**
     * This method check if the order part in a sub section is valid.
     * @return OrderFactory object.
     * @throws TypeTwoException it there was a problem in the order part of the sub section.
     */
    private OrderFactory checkOrder() throws TypeTwoException{
        currentLine++;
        if (fileLiness.get(currentLine).equals(FILTER)){
            throw new TypeTwoException(ORDER+MISSING_SUB_SECTION);
        }
        if (fileLiness.get(currentLine).equals(ORDER)){
            if (fileLiness.size() - 1 == currentLine) {
                currentLine++;
                return new OrderFactory(currentLine);
            }
            else if (fileLiness.get(currentLine + 1).equals(FILTER)) {
                return new OrderFactory(currentLine);
            }
            currentLine++;
            return new OrderFactory(fileLiness.get(currentLine),currentLine+1);
        }
        throw new TypeTwoException(ORDER+INVALID_SUB_SECTION);
    }


}
