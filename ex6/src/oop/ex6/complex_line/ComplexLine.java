package oop.ex6.complex_line;

import oop.ex6.Line;
import oop.ex6.main.ErrorSjava;

import java.util.ArrayList;

/**
 * An interface for all the ComplexLine objects (if/while or method) that force them to implements the validbody and
 * valid function that raise error if this line (block actually) is illegal.
 */
public interface ComplexLine extends Line{

    void validBody() throws ErrorSjava;


}
