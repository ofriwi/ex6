package oop.ex6.main.scopes;

import java.util.Map;

import oop.ex6.main.buildingUnits.CodeException;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * MainScope is the whole file. It contains methods and global variables.
 */
public class MainScope extends Scope {
    private Map<String, Method> methods;

    /**
     * /**
     * Constructor
     * @param lines program's lines as strings
     * @param methods program's methods
     */
    public MainScope(String[] lines, Map<String, Method> methods) {
        super(lines, null);
        this.methods = methods;
    }

    public Map<String, Method> getMethods() {
        return methods;
    }

    public void addMethod(Method method) {
        methods.put(method.getName(), method);
    }
    
    public boolean isMethodExist(String name)
    {
    	return this.methods.containsKey(name);
    }
    
    public Method getMethod(String name) throws CodeException
    {
    	if (!isMethodExist(name))
    	{
    		throw new CodeException("attemption to access a non-existing method");
    	}
    	return this.methods.get(name);
    }
    
    public String getScopeType()
    {
    	return "MainScope";
    }
}
