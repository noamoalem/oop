package filters;
/**
 * This class implement the type one exception.
 */
public class TypeOneExceptionFilter extends Exception{
    public int lineOfError;

    public TypeOneExceptionFilter(int line) {
        lineOfError = line;
    }
}
