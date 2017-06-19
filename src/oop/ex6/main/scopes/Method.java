package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.CodeException;
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
     * @throws CodeException 
     */
    public Method(Scope mainScope, String name, Variable[] variables, Variable[] input, boolean toRun) throws CodeException {
        super(mainScope);
        this.name = name;
        this.input=input;
        if (variables[0] != null) {
            for (Variable variable : variables) {
                if (variable!= null)
                {
                	this.addVariable(variable);;
                }
            }
        }
        this.getVariables();
        if (toRun)
        {this.runScope();}
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
