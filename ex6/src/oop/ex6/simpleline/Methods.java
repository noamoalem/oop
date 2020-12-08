package oop.ex6.simpleline;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represent a method and its attributes.
 */
public class Methods {
    private final static String EMPTYSTRING = "";
    private final static String GETPARAMSREGEX = "\\([^\\)]*\\)";
    private final static String GETONEPARAMSREGEX = ",?\\s+,?";
    private final static HashSet<String> TYPES = new HashSet<>(Arrays.asList("int", "double",
            "boolean", "String", "char"));
    private String name;
    public int numOfParametrs;
    public ArrayList<String> paramTypes = new ArrayList<String>();
    public ArrayList<String> paramNames = new ArrayList<String>();
    public ArrayList<String> namesOfParams = new ArrayList<String>();

    /**
     * The constructor. initialize a method object.
     * @param line
     */
    public Methods(String[] line){
        this.name = line[1];
        this.numOfParametrs = countTypesAndNames(line);
    }

    /**
     * This method counts the number of parameters of a method, and saves their type.
     * @param line the method first line.
     * @return the number of parameters.
     */
    private int countTypesAndNames(String[] line){
        int counter = 0;
        int index = 0;
        for (String word : line){
            if (TYPES.contains(word)) {
                this.paramTypes.add(word);
                this.namesOfParams.add(line[index+1]);
                counter++;
            }
            index++;
        }
        return counter;
    }

    /**
     * This method check if a method has more than one parameter withe the same name.
     * @param line the first line of a method.
     * @return true if there is a duplicate parameters name, false otherwise.
     */
    public boolean duplicateParamNames(String line){
        Pattern p = Pattern.compile(GETPARAMSREGEX);
        Matcher m = p.matcher(line);
        if (m.find()){
        String conditions = line.substring(m.start()+1, m.end()-1);
        String[] paramsLine = conditions.split(GETONEPARAMSREGEX);
            for (String word : paramsLine) {
                if (!TYPES.contains(word)){
                    if (this.paramNames.contains(word)) return true;
                    if (!word.equals(EMPTYSTRING)) this.paramNames.add(word);
                }
            }
        }
        return false;
    }

    /**
     * getter to the method number of parameters.
     * @return the method number of parameters.
     */
    public int getNamOfParameters() {
        return numOfParametrs;
    }

    /**
     * getter to the method name.
     * @return the method name.
     */
    public String getName() {
        return name;
    }

    /**
     * getter to the method parameters types.
     * @return a list of the method parameters types.
     */
    public ArrayList<String> getParamTypes() {
        return paramTypes;
    }
}
