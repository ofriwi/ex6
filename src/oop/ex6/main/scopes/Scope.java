package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.Line;
import oop.ex6.main.buildingUnits.Variable;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by t8307673 on 11/06/2017.
 */
public abstract class Scope implements Iterator<Line> {
    private ArrayList<Variable> variables;
    private Scope parent;

    public ArrayList<Variable> getVariables() {
        return null;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return false;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public Line next() {
        return null;
    }

    public void addVariable(Variable variable) {
    }
}
