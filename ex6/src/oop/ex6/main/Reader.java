package oop.ex6.main;

import oop.ex6.Line;
import oop.ex6.complex_line.Method;
import oop.ex6.complex_line.Var;
import oop.ex6.main.ErrorSjava;
import oop.ex6.simpleline.CommentLine;
import oop.ex6.simpleline.Methods;
import oop.ex6.simpleline.SimpleVar;
import oop.ex6.simpleline.Varibale;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  This is the class the responsible for the reading from the source file, using the buffer system, by first scan that
 *  update the methods and the global vars. This class also responsible for raising the IO reading Error.
 */
public class Reader {
    private static final String INVALIDFINALASSIGMENT = "Error: trying to change final var";
    private static final String NOTEXISTVARIABLE = "Error: cant find this variable";
    private static final String PROBLAMMETHOD = "Error: problem with method";
    private static final String DOUBLENAMING = "Error: duplicateParamNames";
    private static final String INT = "int";
    private static final String DOUBLE = "double";
    private static final String CHAR = "char";
    private static final String BOOLEAN = "boolean";
    private static final String STRING = "String";
    private final static HashSet<String> TYPES = new HashSet<>(Arrays.asList("int", "double",
            "boolean", "String", "char", "final"));
    private final static String OPENSCOOP = "{";
    private final static String CLOSESCOOP = "}";
    private final static String VOID = "void";
    private final static String FINAL = "final";
    private final static String EMPTYSTRING = "";
    private static final String COMMENTLINESTART = "//";
    private static final String NOSPACESVARREGEX =
            "(-?\\d+\\.?\\d*)|(\\b\\w+\\b)|(\"[^\"]*\")|(\'[^\']\')|(\\|\\|)|(\\b&&\\b)|(\\S)";
    private final static String SPACESREGEX = "\\s";
    private File file;
    ArrayList<String> fileLines = new ArrayList<>();
    ArrayList<Methods> methodsList = new ArrayList<>();
    ArrayList<Var> globalVarArray = new ArrayList<>();

    /**
     * The constructor. initialize a Reader object.
     * @param file a given file.
     * @throws IOException if there was a problem with reading the file.
     * @throws ErrorSjava if an invalid line found in the first scan.
     */
    public Reader (File file) throws IOException, ErrorSjava {
        this.file = file;
        readFile();
        firstScan();
    }

    /**
     * This method reads the file into a array.
     * @throws IOException if a problem with the given file occurred.
     */
    private void readFile() throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String currentLine = bufferedReader.readLine();
        while ((currentLine != null)) {
            fileLines.add(currentLine);
            currentLine = bufferedReader.readLine();
        }
        bufferedReader.close();
    }

    /**
     * This method preform the first reading of the file in order to find the global variables and all the method.
     * @throws ErrorSjava if there is invalid variable line or invalid first line of method.
     */
    private void firstScan() throws ErrorSjava {
        int open = 0;
        int close = 0;
        int lineIndex = 0;
        for (String line : fileLines) {
            ArrayList<String> temp = noSpacesVar(line);
            if (temp.size() > 0) {
                String[] words = new String[temp.size() - 1];
                words = temp.toArray(words);
                if (words[words.length - 1].endsWith(OPENSCOOP)) open++;
                if (words[words.length - 1].equals(CLOSESCOOP)) close++;
                if (words[0].equals(VOID)) {
                    Method method = new Method(lineIndex, globalVarArray, methodsList, fileLines, globalVarArray);
                    method.validFirstLine();
                    Methods methods = new Methods(words);
                    this.methodsList.add(methods);
                    }
                else if (TYPES.contains(words[0]) && open == close) { // Global Var
                    Varibale varibale = new Varibale(new ArrayList<String>(Arrays.asList(words)),
                            new ArrayList<Var>(), globalVarArray);
                    varibale.valid();
                    Var var = new Var(words);
                    if (!alreadyExistedGlobalVar(var.getVarName())) globalVarArray.add(var);
                    else throw new StackOverflowError();/// לבדוק זריקת שגיאה לגבי ריבוי שמות גלובליים
                }
                else if (alreadyExistedGlobalVar(words[0])) {
                    SimpleVar simpleVar = new SimpleVar(new ArrayList<String>(temp.subList(0, temp.size() - 1)),
                            alreadyExistedGlobalVarGetType(words[0]), new ArrayList<Var>(),
                            globalVarArray, temp.get(0).equals(FINAL));
                    simpleVar.valid();
                        for (Var var : globalVarArray) {
                            if (var.getVarName().equals(words[0]) && open==close) var.wasAssigned = true;
                        }
                    }
                }
        lineIndex++;
        }
    }

    /**
     * This method split the line according to the spaces.
     * @param line the line to split.
     * @return a split line array.
     */
    private ArrayList<String> noSpacesLine(String line){
        String[] words = line.split("\\s+|(?!^)\\b");
        ArrayList<String> temp = new ArrayList<>();
        for (String word: words){
            if (!word.equals(EMPTYSTRING)) temp.add(word);
        }
        return temp;
    }

    /**
     * This method check if a given variable name is a global variable.
     * @param varName given variable name.
     * @return true if its a global variable, false otherwise.
     */
    public boolean alreadyExistedGlobalVar(String varName){
        for (Var var: globalVarArray){
            if (var.getVarName().equals(varName)) return true;
        }
        return false;
    }

    /**
     * This method find the type of given variable name if its in the global variable.
     * @param varName given variable name.
     * @return the variable type if its a global variable.
     */
    public String alreadyExistedGlobalVarGetType (String varName){
        for (Var var: globalVarArray){
            if (var.getVarName().equals(varName)) return var.getVarType();
        }
        return EMPTYSTRING;
    }

    /**
     * This method split a variable line.
     * @param line given line.
     * @return a split line.
     */
    private ArrayList<String> noSpacesVar(String line){
        Matcher m = Pattern.compile(NOSPACESVARREGEX).matcher(line);
        ArrayList<String> lst = new ArrayList<String>();
        while (m.find()) {
            lst.add(line.substring(m.start(), m.end()));
        }
        return lst;
    }

    /**
     * This method preform the second read and check all the lines in the given file.
     * @throws ErrorSjava if something is illegal in in the code.
     */
    public void javasVerifier() throws ErrorSjava {
        int lineIndex = 0;
        while (lineIndex < fileLines.size()){
            boolean isMethod = false;
            ArrayList<String> noSpacesLine= noSpacesLine(fileLines.get(lineIndex));
            Line curLine;
            if (noSpacesLine.size()>0) {
            if (noSpacesLine.get(0).startsWith(COMMENTLINESTART)){
                curLine = new CommentLine(new ArrayList<>(Arrays.asList(fileLines.get(lineIndex).split(SPACESREGEX))));
            }
            else {
                switch (noSpacesLine.get(0)) {
                    case VOID:
                        curLine = new Method(lineIndex, new ArrayList<Var>(), methodsList,this.fileLines,
                                this.globalVarArray);
                        isMethod = true;
                        Methods met = new Methods(noSpacesLine.toArray(new String[0]));
                        if (met.duplicateParamNames(fileLines.get(lineIndex))) throw new ErrorSjava(DOUBLENAMING);
                        break;
                    case INT:
                    case DOUBLE:
                    case STRING:
                    case CHAR:
                    case BOOLEAN:
                    case FINAL:
                        curLine = new Varibale(noSpacesVar(fileLines.get(lineIndex)), new ArrayList<Var>(),
                                globalVarArray);
                        break;
                    default:
                        if (alreadyExistedGlobalVar(noSpacesLine.get(0))) {
                            String type = EMPTYSTRING;
                            for (Var var : globalVarArray) {
                                if (var.getVarName().equals(noSpacesLine.get(0))){
                                    if (var.isFinal) throw new ErrorSjava(INVALIDFINALASSIGMENT);
                                    type = var.getVarType();}
                            }
                            ArrayList<String> temp = noSpacesVar(fileLines.get(lineIndex));
                            curLine = new SimpleVar(new ArrayList<String>(temp.subList(0,temp.size()-1)), type,
                                    new ArrayList<Var>(), globalVarArray, temp.get(0).equals(FINAL));
                        } else throw new ErrorSjava(NOTEXISTVARIABLE);
                }
            }
            curLine.valid();
            if (isMethod) lineIndex = ((Method) curLine).getLastLine();
            if (lineIndex == -1) throw new ErrorSjava(PROBLAMMETHOD);
            }
            lineIndex ++;
        }
    }

}
