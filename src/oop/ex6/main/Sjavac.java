package oop.ex6.main;

import oop.ex6.main.buildingUnits.CodeException;
import oop.ex6.main.buildingUnits.Line;
import oop.ex6.main.scopes.GlobalsFactory;
import oop.ex6.main.scopes.MainScope;

import java.io.IOException;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * The main file of s-java validator.
 */
public class Sjavac {
    /**
     * Main flow.
     * This program validates s-java file, and prints:
     *      0 if code is valid
     *      1 if code is invalid
     *      2 if an IO exception occurred
     * @param args command line args. args[0] contains the code's path.
     */
    public static void main(String[] args) {
        final int LEGAL = 0, ILLEGAL = 1, IOERROR = 2;
        int outputCode = -1;

        try {
            // Split file to lines
            String path = args[0];
            String[] linesText = SjavaToStringArr.toStringArray(path);

            // Create MainScope with global methods and variables
            MainScope mainScope = new MainScope(linesText);
            GlobalsFactory.createGlobals(linesText, mainScope);

            // Validate all code lines
            validateAllLines(mainScope);
            outputCode = LEGAL;
        } catch (CodeException e) {
            System.err.println(e.getMessage());
            outputCode = ILLEGAL;
        } catch (IOException e) {
            System.err.println("IO Error");
            outputCode = IOERROR;
        } finally {
            System.out.println(Integer.toString(outputCode));
        }

    }

    /*
    * Validate all lines in the code. Throw an exception if code is invalid.
     */
    private static void validateAllLines(MainScope mainScope) throws CodeException{
        while (mainScope.hasNext()){
            new Line(mainScope.next(), mainScope);
        }
    }
}
