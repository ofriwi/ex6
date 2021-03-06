package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.CodeException;
import oop.ex6.main.buildingUnits.Line;
import oop.ex6.main.buildingUnits.Variable;

import java.util.*;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Scope is an abstract method that represents a scope in the program. A scope can be the whole file, a
 * method or a block (if or while).
 */
public abstract class Scope {
    private Map<String, Variable> variables;

    private Scope parent;

    /**
     * Constructor
     *
     * @param parent scope's parent
     * @throws CodeException if scope is invalid
     */
    public Scope(Scope parent) throws CodeException {
        this.variables = new HashMap<>();
        this.parent = parent;
    }

    /**
     * Run the scope.
     * @throws CodeException if the scope's code is invalid.
     */
    protected void runScope() throws CodeException {
        try {
            Line line = new Line(this.getMainScope().next(), this);
            while (Line.updateDepth(line.toString()) != -1) {
                line = new Line(this.getMainScope().next(), this);
            }
        } catch (NoSuchElementException e) {
            throw new CodeException("a scope is not being closed");
        }
    }

    /**
     * Get all variables available in the scope
     *
     * @return ArrayList of variables
     */
    public Map<String, Variable> getVariables() {
        if (this.parent != null) {
            return mergeVariables(parent.getVariables(), variables);
        }
        return this.variables;
    }

    /*
    Merge a list of variables from parent with self variables
     */
    private Map<String, Variable> mergeVariables(Map<String, Variable> parentVariables,
                                                 Map<String, Variable> selfVariables) {
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
        } else {
            return selfVariables;
        }
    }

    /**
     * Check if a variable with this name is defined in the scope
     *
     * @param name Variable's name
     * @return True if variable defined
     */
    public boolean isContainVariable(String name) {
        return variables.keySet().contains(name);
    }

    /**
     * Add a variable
     *
     * @param variable variable to add
     */
    public void addVariable(Variable variable) {
        variables.put(variable.getName(), variable);
    }

    /**
     * Get the main scope
     *
     * @return MainScope object
     */
    public MainScope getMainScope() {
        Scope scope = this;
        while (scope.parent != null)
            scope = scope.parent;
        return (MainScope) (scope);
    }

    /**
     * Return a variable with certain name
     *
     * @param name Variable's name
     * @return Variable object
     * @throws CodeException if variable doesn't exists
     */
    public Variable getVariable(String name) throws CodeException {
        Variable variable = getVariables().get(name);
        if (variable == null) {
            throw new CodeException("");
        } else {
            return variable;
        }
    }
}
