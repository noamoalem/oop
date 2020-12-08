package oop.ex6.simpleline;

import oop.ex6.complex_line.Var;
import oop.ex6.main.ErrorSjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represent the valid checker of a condition (in while/if statement) that should start with '(' end with ')'
 * and have a boolean value (boolean, int or double), and throw error otherwise.
 */
public class Conditions implements SimpleLine {
    private final ArrayList<String> BOOLEANABLE = new ArrayList<String> (Arrays.asList("boolean","int","double"));
    private final static String TRUE = "(\\s)*true(\\s)*";
    private final static String FALSE = "(\\s)*false(\\s)*";
    private final static String CONDITIONREGEX = "(&&)|(\\|\\|)";
    private final static String DOUBLEREGEX = "(\\s)*-?\\d+.?\\d*(\\s)*";
    private final static String EMPTYSTRING = "";
    private final static String SPACE = " ";
    private final static String INVALIDCONDITION = "Error: bad condition of if/while";
    private ArrayList<Var> globalVars;
    private ArrayList<Var> localVars;
    private String[] con_arr;


    /**
     * The constructor. initialize a condition object.
     * @param conditions the condition.
     * @param map list of the variable in the current scoop.
     * @param globalVars  list of the global variable.
     */
    public Conditions(String conditions, ArrayList<Var> map, ArrayList<Var> globalVars) {
        this.localVars = map;
        this.globalVars = globalVars;
        this.con_arr = conditions.split(CONDITIONREGEX);
    }

    /**
     * This method check if a condition line is valid.
     * @throws ErrorSjava if the condition is invalid.
     */
    @Override
    public void valid() throws ErrorSjava {
        for (String con : this.con_arr) {
            boolean localOrGlobalVar = false;
            Pattern p = Pattern.compile(DOUBLEREGEX);
            Matcher m = p.matcher(con);
            Pattern p1 = Pattern.compile(TRUE);
            Matcher m1 = p1.matcher(con);
            Pattern p2 = Pattern.compile(FALSE);
            Matcher m2 = p2.matcher(con);
            for (Var var: localVars) {
                if (var.getVarName().equals(removeSpaces(con))){
                    if (!this.BOOLEANABLE.contains(var.getVarType()) || !var.isWasAssigned()) throw new
                            ErrorSjava(INVALIDCONDITION);
                    localOrGlobalVar = true;
                }
            }
            for (Var var: globalVars) {
                if (var.getVarName().equals(removeSpaces(con))){
                    if (!this.BOOLEANABLE.contains(var.getVarType()) || !var.isWasAssigned()) throw new
                            ErrorSjava(INVALIDCONDITION);
                    localOrGlobalVar = true;
                }
            }
            if (!m.matches()&& !localOrGlobalVar && !m1.matches() && !m2.matches()) throw new
                    ErrorSjava(INVALIDCONDITION);
        }
    }

    /**
     * This method remove the spaces before and after one word.
     * @param name the string to remove spaces from.
     * @return the word without the spaces.
     */
    private String removeSpaces(String name){
        String[] temp = name.split(SPACE);
        for (String part : temp){
            if (!part.equals(EMPTYSTRING)) return part;
        }
        return EMPTYSTRING;
    }

}
