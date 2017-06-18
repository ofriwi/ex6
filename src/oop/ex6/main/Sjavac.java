package oop.ex6.main;

import oop.ex6.main.buildingUnits.CodeException;
import oop.ex6.main.scopes.GlobalsFactory;
import oop.ex6.main.scopes.MainScope;

/**
 * Created by t8307673 on 11/06/2017.
 */
public class Sjavac {
    public void main(String[] args) {
        try {
            String path = args[0];
            String[] linesText = SjavaToStringArr.toStringArray(path);
            MainScope mainScope = new MainScope();
            GlobalsFactory.createGlobals(linesText, mainScope);
        } catch (CodeException e){
            System.err.println(e.getMessage());
        }
    }
}
