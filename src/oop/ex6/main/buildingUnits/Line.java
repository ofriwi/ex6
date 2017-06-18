package oop.ex6.main.buildingUnits;

import oop.ex6.main.scopes.Scope;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * TODO write description
 */
public class Line {
    private Scope parent;

    /**
     * Constructor
     * @param line a line to initialize
     * @param parent
     */
    public Line(String line, Scope parent) {
        this.parent = parent;
    }

    private void validate() throws CodeException {
    }

    private void execute() {
    }

    private void createVariable() {
    }

    private void createScope() {
    }

}
