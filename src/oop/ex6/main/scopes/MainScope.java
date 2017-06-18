package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.CodeException;
import oop.ex6.main.buildingUnits.Variable;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * MainScope is the whole file. It contains methods and global variables.
 */
public class MainScope extends Scope {
    private Map<String, Method> methods;
    private String[] linesText;
    private int lineCounter = 0;

    /**
     * Constructor
     */
    public MainScope(String[] linesText) {
        super(null);
        methods = new HashMap<>();
        this.linesText = linesText;
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
     * Get method by name
     * @param name method's name
     * @return Method object
     * @throws CodeException if no such method exists
     */
    public Method getMethod(String name) throws CodeException {
        Method method = methods.get(name);
        if (method == null) {
            throw new CodeException("");
        }else {
            return method;
        }
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    public boolean hasNext() {
        return lineCounter <= linesText.length;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     * @throws CodeException if the code is invalid
     */
    public String next() throws NoSuchElementException, CodeException {
        if (hasNext())
            return linesText[lineCounter++];
        else
            throw new NoSuchElementException("End of file");
    }

    /**
     * Getter
     * @return number of current line
     */
    public int getLineNumber() {
        return lineCounter + 1;
    }
}
