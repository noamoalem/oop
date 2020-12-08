package oop.ex6.complex_line;

import oop.ex6.main.ErrorSjava;
import oop.ex6.simpleline.Methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represent the valid checker of full scoop of a method, implements the
 * ComplexLine interface. Raise an error if something went wrong in the method.
 */
public class Method implements ComplexLine {

    private final String NAMEREGEX = "([a-zA-Z]+\\w*|_\\w+)";
    private final String TYPEREGEX = "(final ?)(int|String|char|double|boolean)( )"+NAMEREGEX;
    private final String FIRSTLINEMETHOD = "(\\s)*(void)(\\s)+([a-zA-Z]+\\w*)(\\s)*" +
            "(\\()(\\s*(int|String|boolean|char|double)" +
            "(\\s+)([a-zA-Z]+\\w*|_\\w+)(\\s)*(\\,?))*(\\s)*(\\))(\\s)*(\\{)(\\s)*";
    private final String RETURNLINEMETHOD = "(\\s)*return(\\s)*;(\\s)*";
    private final String BADFIRSTLINE = "Error : illegal first line of method";
    private final String BADRETURNLINE = "Error : illegal return line of method";
    private ArrayList<String> line;
    private ArrayList<Var> localVars;
    private ArrayList<Methods> methodsArray;
    private ArrayList<String> fileLines;
    private ArrayList<Var> globalVarArray;
    private int lineNum;
    private int lastLine =-1;

    /**
     * The constructor.
     * @param lineNum the current line number.
     * @param localVars list of the variable in the current scoop.
     * @param methodsArray list of all the methods in the given file.
     * @param fileLines the file lines.
     * @param globalVarArray list of the global variable.
     */
    public Method(int lineNum, ArrayList<Var> localVars, ArrayList<Methods> methodsArray, ArrayList<String> fileLines,
           ArrayList<Var> globalVarArray){
        this.line = Statement.noSpacesLine(fileLines.get(lineNum));
        this.fileLines = fileLines;
        this.lineNum = lineNum;
        this.localVars = localVars;
        this.methodsArray = methodsArray;
        this.globalVarArray = globalVarArray;
}

    /**
     * This method check if the method has valid first line.
     * @throws ErrorSjava if the first line of the method invalid.
     */
    public void validFirstLine() throws ErrorSjava {
        Pattern p = Pattern.compile(FIRSTLINEMETHOD);
        Matcher m = p.matcher(this.fileLines.get(lineNum));
        if (m.matches()){
            updateArgumentsToLocal();
        }
        else throw new ErrorSjava(BADFIRSTLINE);
    }

    /**
     * This method adds the parameters in the definition of the method to the local variable list.
     */
    private void updateArgumentsToLocal(){
        for (Methods method : this.methodsArray){
            if (method.getName().equals(line.get(1))){
                for (int i=0;i<method.namesOfParams.size();i++){
                    localVars.add(new Var(new String[]{method.paramTypes.get(i), method.namesOfParams.get(i)}));
                }
            }
        }
    }

    /**
     * This method check if a method has a valid body.
     * @throws ErrorSjava if the method body invalid.
     */
    public void validBody() throws ErrorSjava {
        Statement demoStatement = new Statement(this.lineNum, this.localVars, this.methodsArray,
                this.fileLines, this.globalVarArray);
        demoStatement.validBody();
        this.lastLine = demoStatement.getLastLine();
        validReturnLine(this.lastLine-1);
        }

    /**
     * This method check if the method has valid return line.
     * @param index the line index
     * @throws ErrorSjava if the return line invalid.
     */
    private void validReturnLine(int index) throws ErrorSjava{
        Pattern p = Pattern.compile(RETURNLINEMETHOD);
        Matcher m = p.matcher(this.fileLines.get(index));
        if (!m.matches()) throw new ErrorSjava(BADRETURNLINE);
    }

    /**
     * This method check if method in the given file is valid.
     * @throws ErrorSjava if the method invalid.
     */
    @Override
    public void valid() throws ErrorSjava {
        validFirstLine();
        this.lineNum++;
        validBody();
    }

    /**
     * getter to the last line of the method.
     * @return the last line of the method.
     */
    public int getLastLine() {
        return lastLine;
    }
}
