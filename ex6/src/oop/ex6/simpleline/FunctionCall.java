package oop.ex6.simpleline;

import oop.ex6.complex_line.Var;
import oop.ex6.main.ErrorSjava;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represent a function call, implement SimpleLine interface.
 */
public class FunctionCall implements SimpleLine {
    private final static String EMPTYSTRING = "";
    private final static String SPACE = " ";
    private final static String NOTEXISTFUNCTION = "Error: not exist function";
    private final static String INCOMPATIBLENUMBEROFPARAMETERS = "Error: incompatible number of parameters";
    private final static String NOTDEFIENDARGUMENTS = "Error: can't find argument type";
    private final static String INCOMPATIBLENPARAMETERSTYPES = "Error: incompatible parameters types";
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String CHAR = "char";
    private static final String BOOLEAN = "boolean";
    private static final String STRING = "String";
    private final static String ARGUMENTSREGERX = "[a-zA-Z]+\\w*|_\\w+|true|false|\"[^\"]*\"|'[^']'|-?\\d+\\.?\\d*";
    private final static String INTREGERX ="(\\s)*-?\\d+";
    private final static String DOUBLEREGERX = "(\\s)*-?\\d+.?\\d*(\\s)*";
    private final static String CHARREGERX = "(\\s)*\\\"[^\\\"]?\\\"(\\s)*";
    private final static String STRINGEREGERX = "(\\s)*\\\"[^\\\"]*\\\"(\\s)*";
    private final static String BOOLEANREGERX = "(\\s)*(true|false)(\\s)*";
    ArrayList<String> line;
    ArrayList<Var> localVars;
    ArrayList<Var> globalVars;
    ArrayList<Methods> methodsArray;
    ArrayList<String> methodParams = new ArrayList<>();


    /**
     * The constructor.
     * @param line the function call line.
     * @param localVars list of the variable in the current scoop.
     * @param globalMap list of the global variable.
     * @param methodsArray list of all thr methods in the given file.
     */
    public FunctionCall(ArrayList<String> line, ArrayList<Var> localVars, ArrayList<Var> globalMap,
                        ArrayList<Methods> methodsArray ){
        this.line =line;
        this.localVars = localVars;
        this.globalVars = globalMap;
        this.methodsArray = methodsArray;
        this.methodParams.addAll(getParams(arrayToString(line)));
    }

    /**
     * This method creat a list of the parameters in the function call.
     * @param line the function call line.
     * @return a list of the parameters in the function call.
     */
    private ArrayList<String> getParams(String line) {
        Matcher m = Pattern.compile(ARGUMENTSREGERX).matcher(line);
        ArrayList<String> lst = new ArrayList<String>();
        m.find();                                          // skip the function name
        while (m.find()) {
             lst.add(line.substring(m.start(), m.end()));
        }
        return lst;
        }

    /**
     * This method convert array to string.
     * @param arr a given array.
     * @return the array as one string.
     */
    private String arrayToString(ArrayList<String> arr){
        String str= EMPTYSTRING;
        for (String x : arr){
            str +=SPACE+x;
        }
        return str;
    }

    /**
     * This method check if the method in the function call exist.
     * @return the method if it exist null otherwise.
     */
    private Methods existedMethod(){
        for (Methods method : methodsArray){
            if (method.getName().equals(line.get(0))) return method;
        }
        return null;
    }

    /**
     * This method check all the parameters in the function call are valid.
     * @throws ErrorSjava if the parameters invalid from some reason.
     */
    private void vaildParams() throws ErrorSjava {
        Methods curMethod = existedMethod();
        if (curMethod==null) throw new ErrorSjava(NOTEXISTFUNCTION);
        if (this.methodParams.size() != curMethod.getNamOfParameters()) throw new
                ErrorSjava(INCOMPATIBLENUMBEROFPARAMETERS);
        int index = 0;
        for (String singleParam : this.methodParams){
            String type;
            if (inGlobal(singleParam)){
                type =getType(singleParam, globalVars);
            } else if (inLocal(singleParam)) {
                 type = getType(singleParam, localVars);
            }
            else if (!typeOfConstant(singleParam).equals(getType(singleParam, localVars))){
                type = typeOfConstant(singleParam);
            }
            else throw new ErrorSjava(NOTDEFIENDARGUMENTS);
            if (!curMethod.getParamTypes().get(index).equals(type))throw new ErrorSjava(INCOMPATIBLENPARAMETERSTYPES);
            index++;
            }
        }

    /**
     * This method find if a given variable is a global variable.
     * @param paramName a given variable.
     * @return true if the variable in the global variable list and assigned already, false otherwise.
     */
    private boolean inGlobal(String paramName) {
        for (Var var: globalVars){
            if (var.getVarName().equals(paramName) && var.isWasAssigned()) return true;
        }
        return false;
    }

    /**
     * This method find if a given variable is a local variable.
     * @param paramName a given variable.
     * @return true if the variable in the local variable list and assigned already, false otherwise.
     */
    private boolean inLocal(String paramName) {
        for (Var var: localVars){
            if (var.getVarName().equals(paramName) && var.isWasAssigned()) return true;
        }
        return false;
    }

    /**
     * This method finds the type of one argument the method got.
     * @param name the argument type.
     * @param list of the local variables.
     * @return the argument type, empty string if the variable dose not exist in the local variable list.
     */
    private String getType(String name, ArrayList<Var> list){
        for (Var var : list){
            if (var.getVarName().equals(name)){
                return var.getVarType();
            }
        }
        return EMPTYSTRING;
    }

    /**
     * This method check if a function call is valid.
     * @throws ErrorSjava if its invalid function call.
     */
    @Override
    public void valid() throws ErrorSjava {
        if (!this.line.get(line.size()-1).endsWith(";"))
            throw new ErrorSjava("Error: function call line dont end with ;");
        vaildParams();
    }

    private String typeOfConstant(String constant){
        Pattern p1 = Pattern.compile(INTREGERX);
        Matcher m1 = p1.matcher(constant);
        if (m1.matches()) return INT;
        Pattern p = Pattern.compile(DOUBLEREGERX);
        Matcher m = p.matcher(constant);
        if (m.matches()) return DOUBLE;
        Pattern p2 = Pattern.compile(CHARREGERX);
        Matcher m2 = p2.matcher(constant);
        if (m2.matches()) return CHAR;
        Pattern p3 = Pattern.compile(STRINGEREGERX);
        Matcher m3 = p3.matcher(constant);
        if (m3.matches()) return STRING;
        Pattern p4 = Pattern.compile(BOOLEANREGERX);
        Matcher m4 = p4.matcher(constant);
        if (m4.matches()) return BOOLEAN;
        return EMPTYSTRING;
    }
}
