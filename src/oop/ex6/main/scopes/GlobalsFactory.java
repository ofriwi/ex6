package oop.ex6.main.scopes;

import oop.ex6.main.buildingUnits.CodeException;
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
    /**
     * Create all globals - methods and variables
     * @param linesText all code as lines
     * @param mainScope MainScope object
     * @throws CodeException if too many ]'s
     */
    public static void createGlobals(String[] linesText, MainScope mainScope) throws CodeException {
        int scopeDepth = 0;
        int lineIndex = 0;
        for (String lineText : linesText){
            lineIndex++;
            scopeDepth += Line.updateDepth(lineText);

            if(scopeDepth == 0){
                new Line(lineText, mainScope, lineIndex);
            }
            else if (Line.updateDepth(lineText)==1 && scopeDepth==1)
            {
            	Method method = new Method(mainScope, Line.getMethodName(lineText),
            			Line.getMethodInput(lineText), Line.getMethodInput(lineText), false);
            	mainScope.addMethod(method);
            }
            else if(scopeDepth < 0){
                throw new CodeException("Code structure invalid - too many \'}\'s");
            }
        }
    }
}
