package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.Line;
import oop.ex6.main.buildingUnits.Variable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Create all globals (methods and variables).
 */
public class GlobalsFactory {
    private Pattern variableDeceleration;
    private Scope mainScope;

    /**
     * Constructor
     * @param mainScope pointer to the main scope (the factory will add the variables and methods to the
     *                  MainScope).
     */
    public GlobalsFactory(Scope mainScope) {
        variableDeceleration = Pattern.compile("^("+Variable.allVariables()+")");
        this.mainScope = mainScope;
    }

    /**
     * Create all global variables
     * @param stringArray MainScope's lines
     * @return Map of variables
     */
    public Map<String, Variable> createVariables(String[] stringArray){
        for (String stringLine : stringArray){
            Matcher match = variableDeceleration.matcher(stringLine);
            if(match.find()){
                new Line(stringLine, mainScope);
            }
        }
        return null;
    }

    /**
     * Create all methods
     * @param allLines MainScope's lines
     * @return Map of methods
     */
    public Map<String, Method> createMethods(String[] allLines, MainScope mainScope) {
        Map<String, Method> methods = new HashMap<>();
        int[][] methodsLinesNum = mainScope.findMethods();
        for (int i = 0; i < methodsLinesNum.length; i++) {
            int startLine = methodsLinesNum[i][0];
            int endLine = methodsLinesNum[i][1];
            String[] methodLines = Arrays.copyOfRange(allLines, startLine + 1, endLine - 1);
            Method method = new Method(methodLines, mainScope, allLines[startLine]);
            methods.put(method.getName(), method);
        }
        return methods;
    }
}
