package oop.ex6.simpleline;


import oop.ex6.complex_line.Var;
import oop.ex6.main.ErrorSjava;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represent a simple var object (simple var means variable name = value).
 * implement SimpleLine interface.
 */
public class SimpleVar implements SimpleLine {
    private static final String FINALCANTCHANGE = "Error: bad final variable assigment";
    private static final String BADVARIABLE = "Error: bad variable";
    private static final String ILLEGALVARIABLENAME = "Error: bad variable name";
    private static final String BADASSIGMENTFORMAT = "Error: bad variable assigment (not =)";
    private static final String INCOMPATIBLETYPEASSIGMENT = "Error: incompatible assigment for the variable type";
    private static final String NOTASSIGNEDVARIABLE = "Error: variable can't be equal to none assignment var ";
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String CHAR = "char";
    private static final String BOOLEAN = "boolean";
    private static final String STRING = "String";
    private static final String TRUE = "true";
    private static final String FALSE = "false";
    private static final String EQUAL = "=";
    private static final String QUOTATIONMARK = "\"";
    private static final String NAMEREGEX = "[a-zA-Z]+\\w*|_\\w+";
    private static final String DOUBLEREGEX = "-?\\d+\\.?\\d*";
    private static final String INTEREGEX = "-?\\d+";
    private static final int ASSIGMENTLENGTH = 3;
    private static final int DECLERATIONLENGTH = 1;
    private ArrayList<Var> globalVars;
    private ArrayList<Var> localVars;
    private ArrayList<String> line;
    private String type;
    private boolean isFinal;

    /**
     * The constructor. initialize a simpleVar object.
     * @param line line the line the variable appears in.
     * @param type the type of the variable.
     * @param map list of the variable in the current scoop.
     * @param globalMap list of the global variable.
     * @param isFinal weather the variable is final or not.
     */
    public SimpleVar(ArrayList<String> line, String type, ArrayList<Var> map, ArrayList<Var> globalMap,
                     boolean isFinal){
        this.line = line;
        this.type = type;
        this.globalVars = deepCopyGlobal(globalMap);
        this.localVars = deepCopyGlobal(map);           // deep copy
        if (globalVars !=null) localVars.addAll(this.globalVars);
        this.isFinal = isFinal;

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
     * This method check if the simple var is valid.
     * @throws ErrorSjava if its not legal.
     */
    @Override
    public void valid() throws ErrorSjava {
        if (line.size() != DECLERATIONLENGTH && line.size() != ASSIGMENTLENGTH) throw new ErrorSjava(BADVARIABLE);
        if (this.isFinal && line.size()!=3) throw new ErrorSjava(FINALCANTCHANGE);
        Pattern p = Pattern.compile(NAMEREGEX);
        Matcher m = p.matcher(line.get(0));
        if (!m.matches()) throw new ErrorSjava(ILLEGALVARIABLENAME);
        if (line.size() == ASSIGMENTLENGTH) {
            if (!line.get(1).equals(EQUAL)) throw new ErrorSjava(BADASSIGMENTFORMAT);
            if (!validAssignment(line.get(2))) throw new ErrorSjava(INCOMPATIBLETYPEASSIGMENT);
            if (!notAssigned(line.get(2))) throw new ErrorSjava(NOTASSIGNEDVARIABLE);
        }
    }

    /**
     * This method check if a global variable with the given name was assigned.
     * @param name given variable name.
     * @return false if the variable wasn't assigned true otherwise.
     */
    private boolean notAssigned(String name){
        for (Var var : globalVars){
            if (var.getVarName().equals(name)){
                if (!var.isWasAssigned()) return false;
            }
        }
        return true;
    }

    /**
     * This method checks if a int type variable got legal value.
     * @param value a given value.
     * @return true if the value legal false otherwise.
     */
    private boolean validInt(String value){
        for (Var var: localVars) {
            if (var.getVarName().equals(value)) return var.getVarType().equals(this.type);
        }
        Pattern p = Pattern.compile(INTEREGEX);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * This method checks if a Double type variable got legal value.
     * @param value a given value.
     * @return true if the value legal false otherwise.
     */
    private boolean validDouble(String value){
        for (Var var: localVars) {
            if (var.getVarName().equals(value)) return (var.getVarType().equals(this.type) ||
                    var.getVarType().equals(INT));
        }
        Pattern p = Pattern.compile(DOUBLEREGEX);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    /**
     * This method checks if a String type variable got legal value.
     * @param value a given value.
     * @return true if the value legal false otherwise.
     */
    private boolean validString(String value){
        for (Var var: localVars) {
            if (var.getVarName().equals(value)) return var.getVarType().equals(this.type);
        }
        return value.startsWith(QUOTATIONMARK) && value.endsWith(QUOTATIONMARK) && value.length()>1;
    }

    /**
     * This method checks if a char type variable got legal value.
     * @param value a given value.
     * @return true if the value legal false otherwise.
     */
    private boolean validChar(String value){
        for (Var var: localVars) {
            if (var.getVarName().equals(value)) return var.getVarType().equals(this.type);
        }
        return value.length() == 3 && value.startsWith("\'") && value.endsWith("\'");
    }

    /**
     * This method checks if a boolean type variable got legal value.
     * @param value a given value.
     * @return true if the value legal false otherwise.
     */
    private boolean validBoolean(String value){
        for (Var var: localVars) {
            if (var.getVarName().equals(value)){
                if (var.getVarType().equals(this.type)) return true;
                else if (var.getVarType().equals(INT)) return true;
                else if (var.getVarType().equals(DOUBLE)) return true;
            }
        }
        if (validInt(value) || validDouble(value)) return true;
        else return  value.equals(TRUE) || value.equals(FALSE);
    }

    /**
     * This method checks ig the assigment for this simpleVar is valid
     * @param value a given value.
     * @return true if the assigment is valid false otherwise.
     */
    private boolean validAssignment(String value) {
        switch (this.type) {
            case INT:
                return validInt(value);
            case DOUBLE:
                return validDouble(value);
            case STRING:
                return validString(value);
            case CHAR:
                return validChar(value);
            case BOOLEAN:
                return validBoolean(value);
            default:
                return false;
        }
    }

    /**
     * getter to the variable name.
     * @return the variable name.
     */
    public String getVarName(){
        return line.get(0);
    }

    /**
     * getter to the field isFinal.
     * @return the value of isFinal.
     */
    public boolean isFinal() {
        return isFinal;
    }
}
