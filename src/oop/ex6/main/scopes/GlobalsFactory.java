package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.Line;
import oop.ex6.main.buildingUnits.Variable;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Create all globals (methods and variables.
 */
public class GlobalsFactory {
    private Pattern variableDecleration;
    private Scope mainScope;

    public GlobalsFactory(Scope mainScope) {
        variableDecleration = Pattern.compile("^("+Variable.allVariables()+")");
        this.mainScope = mainScope;
    }

    public Map<String, Variable> createVariables(String[] stringArray){
        for (String stringLine : stringArray){
            Matcher match = variableDecleration.matcher(stringLine);
            if(match.find()){
                Line line = new Line(stringLine, mainScope);
            }
        }
        return null;
    }

    public ArrayList<Method> toMethods(String[] stringArray) {

        return null;
    }
}
