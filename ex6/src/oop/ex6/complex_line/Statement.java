package oop.ex6.complex_line;

import oop.ex6.main.ErrorSjava;
import oop.ex6.simpleline.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represent the valid checker of full scoop of a if/while statement, implements the ComplexLine interface.
 * Raise an error if something went wrong in the statement.
 */
public class Statement implements ComplexLine {
    private final static HashSet<String> scopeType = new HashSet<String>(Arrays.asList("if", "while"));
    private final static HashSet<String> Types = new HashSet<String>(Arrays.asList("final" , "int" , "double"
            ,"boolean","char","String"));
    private final static String FIRSTLINEREGEX = "\\s*(if|while)\\s*\\(\\s*[^\\)]*\\s*\\)\\s*\\{\\s*";
    private final static String SPACESREGEX = "\\s";
    private final static String GETINSIDEOFBRCKETS = "\\([^\\)]*\\)";
    private final static String NOSPACESREGEX =
            "(-?\\d+\\.?\\d*)|(\\b\\w+\\b)|(\"[^\"]*\")|(\'[^\']\')|(\\|\\|)|(\\b&&\\b)|(\\S)";
    private final static String BADFIRSTLINE = "Error: bad first line statement";
    private final static String NOTENOUGHCLOSERS = "Error: Not enough closers } ";
    private final static String BADLOCALNAME = "Error: problem with local var ";
    private final static String NOTEXISTEDFUNCTION = "Error: function call for invalid function";
    private final static String DOUBLELOCALVARIABLENAMES = "Error: already local var in this name";
    private final static String BADCOMMENTLINE = "Error: bad format of comment line";
    private final static String BADCLOSER = "Error: bad closer line";
    private final static String RETURN = "return";
    private final static String CLOSESCOPE = "}";
    private static final String COMMENTLINESTART = "//";
    private ArrayList<Var> localVars;
    private ArrayList<Var> newLocalVars;
    private ArrayList<Methods> existedMethods;
    private ArrayList<String> cur_line;
    private ArrayList<String> fileLines;
    private ArrayList<Var> globalVarArray;
    private int lastLine = -1;
    public int lineNum;
    private int index ;
    public int open;
    public int close;

    /**
     * The constructor. initialize a statement object.
     * @param lineNum the current line.
     * @param local_map list of the variable in the current scoop.
     * @param existedMethods list of all the methods in the given file.
     * @param fileLines the file lines.
     * @param globalVarArray  list of the global variable.
     */
    public Statement(int lineNum, ArrayList<Var> local_map, ArrayList<Methods> existedMethods,
                     ArrayList<String> fileLines, ArrayList<Var> globalVarArray){
        this.cur_line = noSpacesLine(fileLines.get(lineNum));
        this.fileLines = fileLines;
        this.existedMethods = existedMethods;
        this.localVars = new ArrayList<Var>(local_map);
        this.lineNum = lineNum;
        this.globalVarArray = globalVarArray;
        this.index = 2;
        this.newLocalVars =new ArrayList<Var>();
    }

    /**
     * This method check if the first line of statement is valid.
     * @throws ErrorSjava if the first line invalid.
     */
    private void validFirstLine() throws ErrorSjava {
        Pattern p = Pattern.compile(FIRSTLINEREGEX);
        Matcher m = p.matcher(this.fileLines.get(lineNum));
        if (m.matches()) check_conditions();
        else throw new ErrorSjava(BADFIRSTLINE);

    }

    /**
     * This method check if the condition in the brackets is valid.
     * @throws ErrorSjava if the condition invalid.
     */
    private void check_conditions() throws ErrorSjava{
        Pattern p = Pattern.compile(GETINSIDEOFBRCKETS);
        Matcher m = p.matcher(this.fileLines.get(lineNum));
        m.find();
        String conditions = this.fileLines.get(lineNum).substring(m.start()+1, m.end()-1);
        Conditions con = new Conditions(conditions, this.localVars, this.globalVarArray);
        con.valid();
    }

    /**
     * This method split the line according to the spaces.
     * @param line the line to split.
     * @return a split line array.
     */
    static ArrayList<String> noSpacesLine(String line){
        Pattern p = Pattern.compile(NOSPACESREGEX);
        Matcher m = p.matcher(line);
        ArrayList<String> temp = new ArrayList<>();
        while (m.find()){
            temp.add(line.substring(m.start(),m.end()));
        }
        return temp;
    }

    /**
     * This method check if the body of the statement is valid.
     * @throws ErrorSjava if the body invalid.
     */
    public void validBody() throws ErrorSjava {
        this.open =1;
        this.close = 0;
        int i = lineNum;
        ArrayList<String> line = noSpacesLine(this.fileLines.get(i));
        if (line.size()>0) {
            while (!line.get(0).equals(CLOSESCOPE)) {
                if (i >= this.fileLines.size())
                    throw new ErrorSjava(NOTENOUGHCLOSERS);                               // not enough }
                if (scopeType.contains(line.get(0))) {                                     // complex line
                    Statement statement = new Statement(i, localVars,existedMethods,this.fileLines,this.globalVarArray);
                    statement.validFirstLine();
                    statement.lineNum++;
                    statement.validBody();
                    i = statement.getLastLine() + 1;
                    if (i >= fileLines.size()) throw new ErrorSjava(NOTENOUGHCLOSERS);
                    line = noSpacesLine(this.fileLines.get(i));
                    while (line.size() == 0) {
                        if (i == this.fileLines.size() - 1) throw new ErrorSjava(NOTENOUGHCLOSERS);
                        line = noSpacesLine(this.fileLines.get(++i));
                    }
                } else {                                                                         // simple line
                    SimpleLine curLine = null;
                    if (Types.contains(line.get(0))) {                                           // Declare & Assign
                        curLine = new Varibale(line, localVars, deepCopyGlobal(globalVarArray));
                        curLine.valid();
                        if (!alreadyLocalVar(line.get(1))) {
                            this.localVars.add(new Var(line.toArray(new String[0])));
                            this.newLocalVars.add(new Var(line.toArray(new String[0])));
                        }
                        else throw new ErrorSjava(DOUBLELOCALVARIABLENAMES);
                    } else {                                                                      // only Assign
                        curLine = checkAssignVar(curLine, globalVarArray, line);
                        curLine = checkAssignVar(curLine, localVars, line);
                        if (curLine != null) {
                            curLine.valid();
                        } else if (line.get(0).startsWith(RETURN)) {                              // return line
                            Return returnLine = new Return(this.fileLines.get(i));
                            returnLine.valid();
                        } else if (line.get(0).equals(COMMENTLINESTART)) {                        // comment line
                            if (!this.fileLines.get(i).split(SPACESREGEX)[0].equals(COMMENTLINESTART))
                                throw new ErrorSjava(BADCOMMENTLINE);
                        } else {                                                                  // function call
                            exsitedAndValidMethod(line);
                        }
                    }
                        i++;
                        if (i >= fileLines.size()) throw new ErrorSjava(NOTENOUGHCLOSERS);
                        line = noSpacesLine(this.fileLines.get(i));
                        while (line.size() == 0) {
                            if (i == this.fileLines.size() - 1) throw new ErrorSjava(NOTENOUGHCLOSERS);
                            line = noSpacesLine(this.fileLines.get(i++));
                        }
                }
            }
            if (!(line.size() ==1)) throw new ErrorSjava(BADCLOSER);
            close++;
        }
        this.lastLine = i;
        if (open!=close) throw new ErrorSjava(NOTENOUGHCLOSERS);
    }

    /**
     * This method check if the variable assigment is valid.
     * @param curLine1 the object that represent the type of line at thr moment.
     * @param varArray the variable list.
     * @param line the current line.
     * @return
     */
    private SimpleLine checkAssignVar(SimpleLine curLine1, ArrayList<Var> varArray, ArrayList<String> line){
        SimpleLine curLine = curLine1;
        for (Var var : varArray) {
            if ((var.varName.equals(line.get(0)) || var.varName.equals(line.get(1))) && !var.isFinal())
                curLine = new SimpleVar(new ArrayList<String>(line.subList(0, line.size() - 1)),
                        var.getVarType(), localVars, this.globalVarArray, var.isFinal);
        }
        return curLine;
    }

    /**
     * This method check if a method exist and valid.
     * @param line the method split.
     * @throws ErrorSjava if the method in the line doesnt or valid.
     */
    private void exsitedAndValidMethod(ArrayList<String> line) throws ErrorSjava{
        for (Methods method : existedMethods) {
            if (method.getName().equals(line.get(0))) {
                FunctionCall funcCall = new FunctionCall(line, localVars, this.globalVarArray, existedMethods);
                funcCall.valid();
                return;
            }
        }
        throw new ErrorSjava(NOTEXISTEDFUNCTION);
    }

    /**
     * This method action a deep copy to a given var objects array.
     * @param varList a given var objects array.
     * @return a new list with the var objects.
     */
    private ArrayList<Var> deepCopyGlobal(ArrayList<Var> varList){
        ArrayList<Var> temp = new ArrayList<Var>();
        for (Var var : varList){
            temp.add(new Var(var));
        }
    return temp;
    }

    /**
     * This method check if the statement is valid.
     * @throws ErrorSjava if the statement invalid.
     */
    @Override
    public void valid() throws ErrorSjava {
        validFirstLine();
        this.lineNum++;
        validBody();
    }

    /**
     *
     * @return the last line of a statement.
     */
    public int getLastLine() {
        return lastLine;
    }

    /**
     * This method find if a given variable name is a local variable.
     * @param name given variable name.
     * @return true if it dose false otherwise.
     */
    private boolean alreadyLocalVar(String name){
        for ( Var var : this.localVars){
            if (name.equals(var.getVarName())) return true;
        }
        return false;
    }


}
