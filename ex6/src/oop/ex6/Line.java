package oop.ex6;

import oop.ex6.main.ErrorSjava;

/**
 * An interface for all the line, that force each class that implement it to implement the valid method, that
 * throws error if the line invalid.
 */
public interface Line {

    void valid() throws ErrorSjava;


}


