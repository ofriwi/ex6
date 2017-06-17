package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.Variable;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Method is the scope of one method in the file.
 */
public class Method extends Scope {

    private String name;
    private Variable[] input;

    /**
     * Constructor
     * @param lines method's lines as strings
     * @param parent the MainScope
     * @param name methods's name
     */
    public Method(String[] lines, Scope parent, String name, Variable[] input) {
        super(lines, parent);
        this.name = name;
        this.input=input;
    }

    /**
     * Getter
     * @return method's name
     */
    public String getName() {
        return name;
    }
    
    public Variable[] getinput()
    {
    	return this.input;
    }

}
