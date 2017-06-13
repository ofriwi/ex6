package oop.ex6.main.scopes;

import java.util.Map;

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
}
