package oop.ex6.main;

import java.io.File;
import java.io.IOException;

/**
 * This class responsible for the reading from the source file.
 */
public class Sjavac {
    private static final String NAMEREGEX = "([a-zA-Z]+\\w*|_\\w+)";
    private static final int INVALIDCOD = 1;
    private static final int VALID = 0;
    private static final int PROBLAMWITHFILE = 2;

    /**
     * The driver of the program.
     * @param args a given file to check.
     */
    public static void main(String[] args) {
        try {
            Reader reader = new Reader(new File(args[0]));
            reader.javasVerifier();
            System.out.println(VALID);
        }catch (StackOverflowError exception){
            System.out.println(INVALIDCOD);
        } catch (IOException exception) {
            System.out.println(PROBLAMWITHFILE);
        } catch (ErrorSjava errorSjava) {
            errorSjava.printStackTrace();
            System.out.println(INVALIDCOD);
        }
    }
}