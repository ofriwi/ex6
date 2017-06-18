package oop.ex6.main.scopes;

import java.util.Map;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * MainScope is the whole file. It contains methods and global variables.
 */
public class MainScope extends Scope {
    private Map<String, Method> methods;

    /**
     * Constructor
     *
     * @param lines   program's lines as strings
     * @param methods program's methods
     */
    public MainScope(String[] lines, Map<String, Method> methods) {
        super(lines, null);
        this.methods = methods;
    }

    /**
     * Getter
     * @return all the methods
     */
    public Map<String, Method> getMethods() {
        return methods;
    }

    /**
     * Add a method to the methods' list.
     * @param method a method to add
     */
    public void addMethod(Method method) {
        methods.put(method.getName(), method);
    }

    /**
     * Find all the methods in the MainScope.
     * @return an array containing the lines of the beginning and the end of the function.
     * The array structure is: methodsLines[n, 0] is the line of method #n's deceleration,
     * and methodLines[n, 1] is line of method #n's end ('}').
     */
    public int[][] findMethods() {
        return null;
    }
}
