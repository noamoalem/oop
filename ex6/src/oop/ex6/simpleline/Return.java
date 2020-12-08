package oop.ex6.simpleline;

import oop.ex6.main.ErrorSjava;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represent a return line, implement SimpleLine interface.
 */
public class Return implements SimpleLine {
    private static final String INVALIDRETURNLINE = "Error: invalid return line";
    private String line;
    private String RETUENREGEX= "(\\s)*(return)(\\s)*(;)(\\s)*";


    /**
     * The constructor.
     * @param line a given line.
     */
    public Return(String line){
        this.line =line;
    }

    /**
     * This method checks if the line is a valid return line.
     * @throws ErrorSjava if its invalid return line.
     */
    @Override
    public void valid() throws ErrorSjava {
        Pattern p = Pattern.compile(RETUENREGEX);
        Matcher m = p.matcher(line);
        if (!m.matches())throw new ErrorSjava(INVALIDRETURNLINE);
    }
}
