package orders;
/**
 * This class implement the type one exception.
 */
public class TypeOneExceptionOrder extends Exception {
    public int lineOfError;

    public TypeOneExceptionOrder(int line){
        lineOfError = line;
    }
}
