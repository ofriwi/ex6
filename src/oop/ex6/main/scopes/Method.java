package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.Variable;

import java.io.ObjectInputStream.GetField;
import java.util.Map;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Method is the scope of one method in the file.
 */
public class Method extends Scope {

    private String name;
    private Variable[] input;

    /**
     * Constructor
     * @param mainScope the MainScope
     * @param name Method's name
     * @param variables Method input's variables
     */
    public Method(Scope mainScope, String name, Variable[] variables, Variable[] input) {
        super(mainScope);
        this.name = name;
        this.input=input;
        if (variables != null) {
            for (Variable variable : variables) {
                this.variables.put(variable.getName(), variable);
            }
        }
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
