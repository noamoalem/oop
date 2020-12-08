package filesprocessing;

/**
 * This class implement the type two exception.
 */
public class  TypeTwoException extends Throwable {
    String msg;
    TypeTwoException(String error){
        msg = error;
    }
}
