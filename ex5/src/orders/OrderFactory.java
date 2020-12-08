package orders;
import java.io.File;
import java.util.ArrayList;

/**
 * This class creat the required order according to a given order name.
 */
public class OrderFactory {
    private static final String ALPHABET  = "abs";
    private static final String SIZE = "size";
    private static final String TYPE = "type";
    private static final String REVERS = "REVERSE";
    private String name;
    private boolean reversSuffix;
    private MergeSort mergeSore;
    private String[] orderNeeded;
    public int line;
    /**
     * The constructor.
     * separate the given order name.
     * @param orderName the given order.
     * @param l the line of the filter in the command file.
     */
    public OrderFactory(String orderName, int l){
        line = l;
        orderNeeded = orderName.split("#");
        name = orderNeeded[0];
        if (orderNeeded.length>1){
            if (orderNeeded[1].equals(REVERS)) {
                reversSuffix = true;
            }
        }
    }

    /**
     * Constructor with one parameter.
     * @param l the line of the filter in the command file.
     */
    public OrderFactory(int l){
        name = ALPHABET;
        line = l;
    }

    /**
     * This method a order on a given files array.
     * @param files given files array.
     * @return ordered files array.
     * @throws TypeOneExceptionOrder if the order name is not valid.
     */
    public ArrayList<File> play(ArrayList<File> files) throws TypeOneExceptionOrder {
        if(!checkLegalRevers()){ mergeSore = new MergeSort(new AlphabetOrder(),reversSuffix);
            mergeSore.mergeSort(files,0,files.size()-1);
            throw new TypeOneExceptionOrder(line);
        }
        switch (name){
            case ALPHABET:
                mergeSore = new MergeSort(new AlphabetOrder(),reversSuffix);
                break;
            case SIZE:
                mergeSore = new MergeSort(new SizeOrder(),reversSuffix);
                break;
            case TYPE:
                mergeSore = new MergeSort(new TypeOrder(),reversSuffix);
                break;
            default:
                mergeSore = new MergeSort(new AlphabetOrder(),reversSuffix);
                mergeSore.mergeSort(files,0,files.size()-1);
                throw new TypeOneExceptionOrder(line);
        }
        mergeSore.mergeSort(files,0,files.size()-1);
        return files;
        }

    /**
     * This method check if the revers suffix given in the order name are valid.
     * @return true if its valid false otherwise.
     */
    private boolean checkLegalRevers(){
        if (orderNeeded !=null){
            if (orderNeeded.length>1){
                if (!orderNeeded[1].equals(REVERS)){
                    return false;
                }
            }
        }
        return true;
    }
}



