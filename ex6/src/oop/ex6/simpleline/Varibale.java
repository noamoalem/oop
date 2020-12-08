package oop.ex6.simpleline;

import oop.ex6.complex_line.Var;
import oop.ex6.main.ErrorSjava;

import java.util.*;

/**
 * This class represent valid Variable and save its attributes, implement SimpleLine interface.
 */
public class Varibale implements SimpleLine {

    private final static HashSet<String> TYPES = new HashSet<>(Arrays.asList("int", "double",
            "boolean", "String", "char"));
    private final static String PREFIX = "final";
    private ArrayList<String> line;
    private int index =1;
    private ArrayList<Var> localVars;
    private ArrayList<Var> globalVars;
    private boolean isFinal;
    private static final String ENDOFLINE = ";";
    private static final String SEPARATOR = ",";
    private static final String INVALIDLINELENGTH ="Error: bad variable line length";
    private static final String INVALIDFINALTYPE = "Error: bad final variable type";
    private static final String INVALIDTYPE = "Error: bad variable type";
    private static final String INVALIDENDOFLINE = "Error: variable line don't end with ;";
    private static final String INVALIDASSIGMENT = "Error: problem with the assigment , but nothing after it";
    private static final String DOUBLENAMING = "Error: bad variable 2 with the same name";
    /**
     * The constructor. initialize a variable object.
     * @param line the line the variable appears in.
     * @param localVars list of the variable in the current scoop.
     * @param globalMap list of the global variable.
     */
    public Varibale(ArrayList<String> line, ArrayList<Var> localVars, ArrayList<Var> globalMap ){
        this.line = line;
        this.localVars = localVars;
        this.globalVars =globalMap;
        this.isFinal = line.get(0).equals(PREFIX);
    }

    /**
     * This method check if the variable is valid according to the Sjava syntax.
     * @throws ErrorSjava if the variable not legal.
     */
    @Override
    public void valid() throws ErrorSjava {
        if (line.size()==0) throw new ErrorSjava(INVALIDLINELENGTH);
        if (this.isFinal) {
            this.index++;
            if (line.size() < 6) throw new ErrorSjava(INVALIDLINELENGTH);
            if (!TYPES.contains(line.get(1))) throw new ErrorSjava(INVALIDFINALTYPE);
        }
        else if (!TYPES.contains(line.get(0))) throw new ErrorSjava(INVALIDTYPE);
        checkSimpleVars();
        if (!line.get(line.size() - 1).equals(ENDOFLINE)) throw new ErrorSjava(INVALIDENDOFLINE);
    }

    /**
     * This method check if the assigment is valid according to the Sjava syntax.
     * @throws ErrorSjava if its illegal assigment.
     */
    private void checkSimpleVars() throws ErrorSjava {
        String type = line.get(index-1);
        ArrayList<String> simpleVarsNames = new ArrayList<String>();
        while (index < line.size()-1){
            ArrayList<String> temp = new ArrayList<String>();
            while (!line.get(index).equals(SEPARATOR)){
                temp.add(line.get(index));
                index++;
                if (index >= line.size()-1) break;
            }
            index++;                                    // ignore the , move to next loop
            if (index == line.size()-1) throw new ErrorSjava(INVALIDASSIGMENT);
            SimpleVar simpleVar = new SimpleVar(temp, type, this.localVars, this.globalVars,this.isFinal );
            simpleVar.valid();
            if (simpleVarsNames.contains(simpleVar.getVarName()))throw new ErrorSjava(DOUBLENAMING);
            simpleVarsNames.add(simpleVar.getVarName());
        }
    }


}
