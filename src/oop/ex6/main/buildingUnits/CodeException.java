package oop.ex6.main.buildingUnits;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * An error in code exception.
 */
public class CodeException extends Exception {

    /**
     * Constructor
     * @param msg message to print
     */
    public CodeException(String msg){
        super(msg);
    }
}
