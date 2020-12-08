package oop.ex6.complex_line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represent a variable, that can be global/local, assigned or not and final or not, save the
 * variable name and type.
 */
public class Var {
    private final static String FINAL = "final";
    private final static int MINIMALASSIGMENTLENGTH = 4;
    public boolean isFinal;
    public boolean wasAssigned;
    String varName;
    String varType;

    /**
     * The constructor.
     * @param line the line the variable appears in.
     */
    public Var(String[] line){
        this.isFinal = line[0].equals(FINAL);
        this.wasAssigned = line.length > MINIMALASSIGMENTLENGTH;
        if (isFinal) {varName=line[2];varType=line[1];}
        else {varName=line[1];varType=line[0];}
    }

    /**
     * copy constructor.
     * @param var object to copy.
     */
    public Var(Var var){
        this.isFinal = var.isFinal;
        this.wasAssigned = var.wasAssigned;
        this.varName = var.varName;
        this.varType = var.varType;
    }

    /**
     * getter to the variable name.
     * @return the variable name.
     */
    public String getVarName() {
        return varName;
    }

    /**
     * getter to the variable type.
     * @return the variable type.
     */
    public String getVarType() {
        return varType;
    }

    /**
     *
     * @return true if the variable was assigned, false otherwise.
     */
    public boolean isWasAssigned() {
        return wasAssigned;
    }

    /**
     *
     * @return true if the variable is final, false otherwise.
     */
    public boolean isFinal() {
        return isFinal;
    }
}
