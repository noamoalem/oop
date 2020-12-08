package oop.ex6.simpleline;
import oop.ex6.main.ErrorSjava;
import java.util.ArrayList;

/**
 * This class represent comment line, implement SimpleLine interface.
 */
public class CommentLine implements SimpleLine {
    private static final String COMMENTLINESTART = "//";
    private final static String BADCOMMENTLINE = "Error: bad format of comment line";
    private ArrayList<String> line;

    /**
     * The constructor.
     * @param line a given line.
     */
    public CommentLine(ArrayList<String> line){
        this.line = line;
    }

    /**
     * This method check if the line is a comment line.
     * @throws ErrorSjava if its invalid comment line.
     */
    @Override
    public void valid() throws ErrorSjava {
        if (!this.line.get(0).startsWith(COMMENTLINESTART)) throw new ErrorSjava(BADCOMMENTLINE);
    }
}
