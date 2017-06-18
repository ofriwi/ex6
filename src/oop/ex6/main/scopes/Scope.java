package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.CodeException;
import oop.ex6.main.buildingUnits.Line;
import oop.ex6.main.buildingUnits.Variable;
import sun.applet.Main;

import java.util.*;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Scope is an abstract method that represents a scope in the program. A scope can be the whole file, a
 * method or a block (if or while).
 */
public abstract class Scope {
    Map<String, Variable> variables;

    private Scope parent;
    private String[] lines;

    /**
     * Constructor
     * @param lines scope's lines as strings
     * @param parent scope's parent
     */
    public Scope(String[] lines, Scope parent) {
        this.variables = new HashMap<>();
        this.parent = parent;
        this.lines = lines;
    }

    /**
     * Get all variables available in the scope
     * @return ArrayList of variables
     */
    public Map<String, Variable> getVariables() {
        return mergeVariables(parent.getVariables(), variables);
    }

    /*
    Merge a list of variables from parent with self variables
     */
    private Map<String, Variable> mergeVariables(Map<String, Variable> parentVariables,
                                               Map<String, Variable> selfVariables){
        if (parent != null) {
            // Create a merged list
            Map<String, Variable> merged = new HashMap<>(selfVariables);
            // For each variable of the parent, if it's not overridden, add it to merged
            for (String varName : parentVariables.keySet()) {
                Variable var = parentVariables.get(varName);
                if (!isContainVariable(var.getName())) {
                    addVariable(var);
                }
            }
            return merged;
        }else{
            return selfVariables;
        }
    }

    /**
     * Check if a variable with this name is defined in the scope
     * @param name Variable's name
     * @return True if variable defined
     */
    public boolean isContainVariable(String name){
        return variables.keySet().contains(name);
    }

    /* DEBUG TODO REMOVE
     * Run the method
     * @throws CodeException if one of the lines throws an error
     /
    public void excecute() throws  CodeException{
        for (String lineText : lines){
            Line line = new Line(lineText);
        }
    }  */

    /**
     * Add a variable
     * @param variable variable to add
     */
    public void addVariable(Variable variable) {
        variables.put(variable.getName(), variable);
    }

    /**
     * Get the main scope
     * @return MainScope object
     */
    public MainScope getMainScope(){
        Scope scope = this;
        while(scope.parent != null)
            scope = scope.parent;
        return (MainScope)(scope);
    }

    /**
     * Return a variable with certain name
     * @param name Variable's name
     * @return Variable object
     * @throws CodeException if variable doesn't exists
     */
    public Variable getVariable(String name) throws CodeException{
        Variable variable = getVariables().get(name);
        if (variable == null) {
            throw new CodeException("");
        }else {
            return variable;
        }
    }
}
