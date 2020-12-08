package filesprocessing;
import filters.FilterFactory;
import filters.TypeOneExceptionFilter;
import orders.OrderFactory;
import orders.TypeOneExceptionOrder;

import java.io.File;
import java.util.ArrayList;

/**
 * This class implement one section from the command file.
 */
public class SectionCreatAndPlay {
    private static final String error = "Warning in line ";
    private FilterFactory filterFactory;
    private OrderFactory order;
    private ArrayList<String> sectionResult = new ArrayList<>();

    /**
     * The constructor.
     * @param filt given filterFactory object.
     * @param ord given orderFactory object.
     */
    public SectionCreatAndPlay(FilterFactory filt, OrderFactory ord){
        filterFactory = filt;
        order = ord;
    }

    /**
     * This method play a section on a given file array.
     * @param filesArray given file array.
     * @return the result of this section.
     */
    ArrayList<String> play(ArrayList<File> filesArray){
        ArrayList<File> filteredArray;
        try {
            filteredArray = filterFactory.play(filesArray);
        }
        catch (TypeOneExceptionFilter exception){
            filteredArray = filesArray;
            System.err.println(error+exception.lineOfError);
        }
        if (filteredArray!=null){
            try {
                order.play(filteredArray);
            }
            catch (TypeOneExceptionOrder exception){
                System.err.println(error+exception.lineOfError);
            }
            for (int i =0; i<filteredArray.size();i++){
                sectionResult.add(filteredArray.get(i).getName());
            }
        }
        return sectionResult;
    }


}
