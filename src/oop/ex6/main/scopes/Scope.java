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
    Map<String, Variable> variables;

    private Scope parent;
    private String[] lines;
    private int lineCounter;
    private int openScopes;
    private int subScopeStart;

    /**
     * Constructor
     * @param lines scope's lines as strings
     * @param parent scope's parent
     */
    public Scope(String[] lines, Scope parent) {
        this.variables = new HashMap<>();
        this.parent = parent;
        this.lines = lines;
        lineCounter = 0;
        openScopes=0;
        this.subScopeStart=-1;
    }

    /**
     * Get all variables available in the scope
     * @return ArrayList of variables
     */
    public Map<String, Variable> getVariables() {
        return mergeVariables(parent.getVariables(), variables);
    }
    
    public int getOpenedScopes()
    {
    	return this.openScopes;
    }
    public void setOpenedScopes(int num)
    {
    	this.openScopes=num;
    }

    /**
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
    
    /**
     * returns a variable with given name
     * @param name the Variable's name
     * @return the variable
     * @throws CodeException if the given name is a non-existing variable's name
     */
    public Variable getVariable(String name) throws CodeException
    {
    	if (!isContainVariable(name))
    	{
    		throw new CodeException("attemption to access non-existing variable");
    	}
    	return variables.get(name);
    }

    /**
     * Run the method
     * @throws CodeException if one of the lines throws an error
     */
    public void excecute() throws  CodeException{
        for (int i=1; i<=this.lines.length; i++){
            Line line = new Line(lines[i],this,i);
        }
    }

    /**
     * Add a variable
     * @param variable variable to add
     */
    public void addVariable(Variable variable) {
        variables.put(variable.getName(), variable);
    }
    
    public MainScope getMainScope()
    {
    	if (this.parent==null)
    	{
    		return (MainScope)this;
    	}
    	return this.parent.getMainScope();
    }
    
    public void setSubScopeStart(int start)
    {
    	this.subScopeStart=start;
    }
    
    public int getSubScopeStart()
    {
    	return this.subScopeStart;
    }
    
    public abstract String getScopeType();
}
