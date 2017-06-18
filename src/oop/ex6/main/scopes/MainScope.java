package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.CodeException;
import oop.ex6.main.buildingUnits.Variable;

import java.util.Map;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * MainScope is the whole file. It contains methods and global variables.
 */
public class MainScope extends Scope {
    private Map<String, Method> methods;

    /**
     * Constructor
     * @param methods program's methods
     */
    public MainScope(Map<String, Method> methods) {
        super(null);
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


    public Method getMethod(String name) throws CodeException {
        Method method = methods.get(name);
        if (method == null) {
            throw new CodeException("");
        }else {
            return method;
        }
    }
}
