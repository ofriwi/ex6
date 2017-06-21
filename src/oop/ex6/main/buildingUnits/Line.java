package oop.ex6.main.buildingUnits;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.scopes.Block;
import oop.ex6.main.scopes.MainScope;
import oop.ex6.main.scopes.Method;
import oop.ex6.main.scopes.Scope;

/**
 * Created by Tomer Shani on 11/06/2017.
 *
 */
public class Line {
    private Scope parent;
    private String line;
    private int lineNumber;

    /**
     * Constructor
     *
     * @param line       line's text
     * @param parent     the scope contains this line
     * @param lineNumber current line's number
     */
    public Line(String line, Scope parent, int lineNumber) throws CodeException {
        this.parent = parent;
        this.line = line;
        this.lineNumber = lineNumber-1;
        this.validate();
    }

    /**
     * Constructor
     *
     * @param line   line's text
     * @param parent the scope contains this line
     * @throws CodeException 
     */
    public Line(String line, Scope parent) throws CodeException {
        this.parent = parent;
        this.line = line;
        this.lineNumber = parent.getMainScope().getLineNumber()-1;
        this.validate();
    }

    /**
     * a private method which reads the line, classifies it, updates the variables according to it
     * and throws CodeException if the Line is illegal (either in it's syntax or attempted actions) 
     * @throws CodeException if the line is illegal
     */
    private void validate() throws CodeException {
        String type = roughSort(this.line);
        switch (type) {
            case "INVALID":
                throw new CodeException("line " + this.lineNumber + " did not pass the first rough sort");
            case "OPEN":
                if (!this.validateOpener()) {
                    throw new CodeException("line " + this.lineNumber + " was"
                            + " iterperted as block opener but wasn't in the right template");
                }
                String openType = openerType(this.line);
                if (openType.equals("IF") || openType.equals("WHILE"))
                {
                	new Block(this.parent);
                }
                else if (openType.equals("METHOD"))
                {
                	new Method(this.parent.getMainScope(),
                			getMethodName(this.line), getMethodInput(this.line), getMethodInput(this.line), true);
                }
                break;
            case "CODELINE":
                if (!this.validateCodeLine()) {
                    throw new CodeException(" line " + this.lineNumber + " was"
                            + " interperted as a Standart codeline but wasn't in the right template");
                }
                if (this.isVarAssign(this.line))
                {
                	 Pattern varDeclare = Pattern.compile("^[\\s]*(final[\\s]+)?(int|double|String|char|boolean)"
                             + "[\\s]+([^\\s]*)[\\s]*(|=[\\s]*[^\\s]+.*[\\s]*);[\\s]*");
                     Matcher decMatch = varDeclare.matcher(this.line);
                     decMatch.matches();
                     boolean isFinal = (decMatch.group(1) != null);
                     String varType = decMatch.group(2);
                     String name = decMatch.group(3);
                     boolean isAssigned = false;
                     if (!decMatch.group(4).equals(""))
                     {
                    	 isAssigned=true;
                     }
                     this.createVariable(name, varType, isFinal, isAssigned);
                }
                break;
            default:
                break;
        }
    }
    
    /**
     * static method returns a method's name of a method decleration line using regex
     * @param str - the given line
     * @return the declared method's name
     * @throws CodeException if the syntax is wrong.
     */
    public static String getMethodName(String str) throws CodeException
    {
    	Pattern methodLine = Pattern.compile
                ("^\\s*void\\s+([a-zA-Z]{1}[a-zA-Z0-9_]*)\\s*\\((.*)\\)\\s*\\{\\s*");
    	Matcher methodMatch = methodLine.matcher(str);
    	methodMatch.matches();
    	try{
    	return methodMatch.group(1);
    	}
    	catch (Exception e)
    	{
    		throw new CodeException("invalid method declaration");
    	}
    }

    /**
     * static method returns a method's needed input of a method decleration line using regex
     * @param str - the given line
     * @return the declared method's needed input
     * @throws CodeException if the syntax is wrong.
     */
    public static Variable[] getMethodInput(String str) throws CodeException
    {
    	Pattern methodLine = Pattern.compile
                ("^\\s*void\\s+([a-zA-Z]{1}[a-zA-Z0-9_]*)\\s*\\((.*)\\)\\s*\\{\\s*");
    	Matcher methodMatch = methodLine.matcher(str);
    	methodMatch.matches();
    	String[] paramList = methodMatch.group(2).split(",");
    	Variable ret[] = new Variable[paramList.length];
    	if (paramList.length==1 && paramList[0].equals(""))
    	{
    		return ret;
    	}
    	for (int i=0; i<ret.length; i++)
    	{
    		Pattern varDec = Pattern.compile("[\\s]*(int|double|String|boolean|char)[\\s]*([a-zA-Z0-9_]*)[\\s]*");
    		Matcher varMatch = varDec.matcher(paramList[i]);
    		varMatch.matches();
    		try{
    		ret[i]=new Variable(varMatch.group(2), varMatch.group(1), false, true/*probably change this*/);
    		}
    		catch (Exception e)
    		{
    			throw new CodeException("invalid method decleration");
    		}
    	}
    	return ret;
    }
    
    /**
     * static method roughly sorting a given String using it's prefix and suffix
     * @param str - the given line
     * @return 'EMPTY' if the line matches empty line, 'COMMENT' if the line matches comment line
     * 'OPEN' if the line matches scope opener, 'CLOSE' if the line matches scope closer
     * 'CODELINE' if the line matches normal codeline (if it ends with ';'), 'INVALID' otherwise
     */
    private static String roughSort(String str) {
        Pattern empty = Pattern.compile("[\\s]*");
        Pattern comment = Pattern.compile("^\\/{2}.*$");
        Pattern blockOpen = Pattern.compile("^[^\\/].*\\{[\\s]*");
        Pattern blockClose = Pattern.compile("^[\\s]*}[\\s]*$");
        Pattern codeLine = Pattern.compile("^[^\\/].*;[\\s]*$|;[\\s]*");
//^[^\\/].*\\{$|\\{
        Matcher m = empty.matcher(str);
        if (m.matches()) {
            return "EMPTY";
        }
        m = comment.matcher(str);
        if (m.matches()) {
            return "COMMENT";
        }
        m = blockOpen.matcher(str);
        if (m.matches()) {
            return "OPEN";
        }
        m = blockClose.matcher(str);
        if (m.matches()) {
            return "CLOSE";
        }
        m = codeLine.matcher(str);
        if (m.matches()) {
            return "CODELINE";
        } else {
            return "INVALID";
        }
    }
    
    /**
     * help-function returning an indicator for scope openers and closers
     * @param str - the given line
     * @return 1 if the pattern matches line-opener, -1 if the pattern matches line-closer, 0 otherwise
     */
    public static int updateDepth(String str) {
        String type = roughSort(str);
        switch (type) {
            case "OPEN":
                return 1;
            case "CLOSE":
                return -1;

            default:
                return 0;
        }
    }

    /**
     * static method which classifies a scope opener type using regex
     * @param str - the given line
     * @return 'IF' if pattern matches if declaration, 'WHILE' if pattern matches while declaration
     * 'METHOD' if pattern matches method declaration, 'NONE' otherwise.
     */
    private static String openerType(String str)
    {
    	 Pattern ifLine = Pattern.compile("^\\s*if\\s+\\((.*)\\)\\s*\\{\\s*");
         Pattern whileLine = Pattern.compile("^\\s*while\\s+\\((.*)\\)\\s*\\{\\s*");
         Pattern methodLine = Pattern.compile
                 ("^\\s*void\\s+([a-zA-Z]{1}[a-zA-Z0-9_]*)\\s*\\((.*)\\)\\s*\\{\\s*");
         Matcher ifMatch = ifLine.matcher(str);
         Matcher whileMatch = whileLine.matcher(str);
         Matcher methodMatch = methodLine.matcher(str);
         if (ifMatch.matches())
         {return "IF";}
         if (whileMatch.matches())
         {return "WHILE";}
         if (methodMatch.matches())
         {return "METHOD";}
         return "NONE";
    }
    
    /**
     * checks if an opener type's syntax is valid
     * @return true if valid, false otherwise
     */
    private boolean validateOpener() {
        Pattern ifLine = Pattern.compile("^\\s*if\\s*\\((.*)\\)\\s*\\{\\s*");
        Pattern whileLine = Pattern.compile("^\\s*while\\s*\\((.*)\\)\\s*\\{\\s*");
        Pattern methodLine = Pattern.compile
                ("^\\s*void\\s+([a-zA-Z]{1}[a-zA-Z0-9_]*)\\s*\\((.*)\\)\\s*\\{\\s*");
        Matcher ifMatch = ifLine.matcher(this.line);
        Matcher whileMatch = whileLine.matcher(this.line);
        Matcher methodMatch = methodLine.matcher(this.line);
        if (ifMatch.matches()) {
            String cond = ifMatch.group(1);
            return isCondition(cond);
        }
        if (whileMatch.matches()) {
            String cond = whileMatch.group(1);
            return isCondition(cond);
        }
        if (methodMatch.matches() && this.parent.getMainScope() == this.parent) {
            String declare = methodMatch.group(2);
            return isParameterList(declare);
        }
        return false;
    }

    /**
     * checks if a standard codeline type's syntax is valid
     * @return true if valid, false otherwise
     */
    private boolean validateCodeLine() {
        return (this.isVarAssign(this.line) || this.isVarValueChange(this.line)
                || this.isMethodCall(this.line) || this.isReturn(this.line) ||
                this.isMultipleVarAssign(this.line));
    }

    /**
     * creates a new variable using the given parameters
     * @param name - the variable's name
     * @param type - the variable's type
     * @param isFinal - a boolean representing weither the variable is final
     * @param isAssigned - a boolean representing weither the variable is assigned
     */
    private void createVariable(String name, String type, boolean isFinal, boolean isAssigned) {
        this.parent.addVariable(new Variable(name, type, isFinal, isAssigned));
    }

    /**
     * a method checking if a given String is a valid variable name
     * @param name-the given String
     * @return true if name is a valid variable name, false otherwise
     */
    private static boolean isValidVarName(String name) {
        Pattern varName = Pattern.compile("([a-zA-Z]{1}[a-zA-Z0-9_]*|[_][a-zA-Z0-9_]+)");
        Matcher vaMatch = varName.matcher(name);
        return (vaMatch.matches());
    }

    /**
     * a method varifing if a given variable's value is valid and compatable with it's type
     * @param type - the variable's type
     * @param value - the variable value
     * @return true if the value is legal, false otherwise.
     */
    private boolean isValidVarValue(String type, String value) {
        Pattern intVal = Pattern.compile("[\\s]*-?[0-9]+[\\s]*");
        Pattern doubleVal = Pattern.compile("[\\s]*-?[0-9]+\\.?[0-9]*[\\s]*");
        Pattern StringVal = Pattern.compile("^\".*\"$");
        Pattern booleanVal = Pattern.compile("[\\s]*(true|false|-?[0-9]+\\.?[0-9]*)[\\s]*");
        Pattern charVal = Pattern.compile("^\\'.\\'$");
        Matcher m;
        switch (type) {
            case "int":
                m = intVal.matcher(value);
                break;
            case "double":
                m = doubleVal.matcher(value);
                break;
            case "String":
                m = StringVal.matcher(value);
                break;
            case "boolean":
                m = booleanVal.matcher(value);
                break;
            case "char":
                m = charVal.matcher(value);
                break;
            default:
                return false;
        }
        boolean goodVar;
        try {
            goodVar = (this.parent.isContainVariable(value) &&
                    this.parent.getVariable(value).getType().equals(type) &&
                    this.parent.getVariable(value).isAssigned());
        } catch (CodeException e) {
            goodVar = false;
        }
        return (m.matches() || goodVar);
    }

    /**
     * a method checking if a given line is a legal variable assignment
     * @param str - the given line
     * @return - true if the line is legal variable assignment, false otherwise
     */
    private boolean isVarAssign(String str) {
        boolean isAssign = true;
        Pattern varDeclare = Pattern.compile("^[\\s]*(final[\\s]+)?(int|double|String|char|boolean)"
                + "[\\s]+([^\\s=]+)[\\s]*(|=[\\s]*[^\\s]+.*[\\s]*);[\\s]*");
        Matcher decMatch = varDeclare.matcher(str);
        if (!decMatch.matches()) {
            return false;
        }
        boolean isFinal = (decMatch.group(1) != null);
        String type = decMatch.group(2);
        String name = decMatch.group(3);
        String value = "";
        if (!decMatch.group(4).equals("")) {
            Pattern getVal = Pattern.compile("=[\\s]*([^\\s]*.*[^\\s]+)[\\s]*");
            Matcher valMatch = getVal.matcher(decMatch.group(4));
            valMatch.find();
            value = valMatch.group(1);
        } else {
            isAssign = false;
        }
        if (!(type.equals("int") || type.equals("double") || type.equals("String")
                || type.equals("boolean") || type.equals("char"))) {
            return false;
        }
        if (!isValidVarName(name)) {
            return false;
        }
        if (isAssign && !this.isValidVarValue(type, value)) {
            return false;
        }
        if (!isAssign && isFinal) {
            return false;
        }
        if (this.parent.isContainVariable(name) && !this.parent.getMainScope().isContainVariable(name))
        {
        	return false;
        }
        return true;
    }

    /**
     * a static method checking if a given String is a valid list of parameters
     * @param str - the given String
     * @return true if the String is a valid list of parameters, false otherwise 
     */
    private static boolean isParameterList(String str) {
        String[] paramList = str.split(",");
        String[] nameList = new String[paramList.length];
        int count=0;
        Pattern varDeclare = Pattern.compile(
                "\\s*(int|double|String|boolean|char)\\s+([a-zA-Z]{1}[a-zA-Z0-9_]*|[_][a-zA-Z0-9_]+)\\s*");
        for (String i : paramList) {
            Matcher declareMatch = varDeclare.matcher(i);
            if (declareMatch.matches()) {
            	for (String j : nameList)
            	{
            		if (j!=null && j.equals(declareMatch.group(2)))
            		{return false;}
            	}
                nameList[count]=declareMatch.group(2);
                count++;
         //   	break;
            }
            else if(i.equals(""))
            {break;}
            else
            {return false;}
        }
        return true;
    }

    /**
     * a static method checking if a given String is a valid condition expression
     * @param str - the given String
     * @return true if the String is a valid condition expression, false otherwise 
     */
    private boolean isCondition(String str) {
        String[] sepCond = str.split("\\|\\||&&");
        Pattern literalCond = Pattern.compile("\\s*(true|false)\\s*");
        Pattern numCond = Pattern.compile("\\s*[\\d]+[\\.]?[\\d]*\\s*");
        Pattern varCond = Pattern.compile("^\\s*([a-zA-Z]{1}[a-zA-Z0-9_]*|[_][a-zA-Z0-9_]+)\\s*$");

        for (String i : sepCond) {
            Matcher lit = literalCond.matcher(i);
            Matcher num = numCond.matcher(i);
            Matcher var = varCond.matcher(i);
            if (lit.matches()) {
                continue;
            }
            if (num.matches()) {
                continue;
            }
            if (var.matches()) {
                String varName = var.group(1);
                if (this.parent.isContainVariable(varName)) {
                	String type;
                	boolean isAssigned;
                	try {
						type = this.parent.getVariable(varName).getType();
						isAssigned=this.parent.getVariable(varName).isAssigned();
					} catch (CodeException e) {
						return false;
					}
                	if ((type.equals("int")||type.equals("double")||type.equals("boolean"))&&isAssigned)
                    {continue;}
                }
            }
            return false;
        }
        return true;

    }

    /**
     * a method checking if a given line is a legal variable value change
     * @param str - the given line
     * @return - true if the line is legal value change, false otherwise
     */
    private boolean isVarValueChange(String str) {
        Pattern varChange = Pattern.compile("[\\s]*([^\\s]+)[\\s]*=[\\s]*([^\\s].*[^\\s]*)[\\s]*;[\\s]*");
        Matcher match = varChange.matcher(str);
        if (!match.matches()) {
            return false;
        }
        String name = match.group(1);
        String value = match.group(2);
        if (!this.parent.isContainVariable(name)) {
            return false;
        }
        try
        {
            if (!this.isValidVarValue(this.parent.getVariable(name).getType(), value)) {
                return false;
            }
            if (this.parent.getVariable(name).isFinal()) {
                return false;
            }
        } catch (CodeException e) {
            return false;
        }
        return true;

    }

    /**
     * a method checking if a given line is a legal method call
     * @param str - the given line
     * @return - true if the line is legal method call, false otherwise
     */
    private boolean isMethodCall(String str) {
        Pattern methodCall = Pattern.compile("[\\s]*([^\\s]*)[\\s]*\\((.*)\\)[\\s]*;[\\s]*");
        Matcher callMatcher = methodCall.matcher(str);
        if (!callMatcher.matches()) {
            return false;
        }
        String name = callMatcher.group(1);
        String vars = callMatcher.group(2);
        //check if vars is compatable with the method
        String[] varList = vars.split(",");
        try {
            Variable[] input = this.parent.getMainScope().getMethod(name).getinput();
            if (input.length != varList.length) {
                return false;
            }
            for (int i = 0; i < input.length; i++) {
                if (input[i]==null)
                {break;}
            	if (!this.isValidVarValue(input[i].getType(), varList[i])) {
                    return false;
                }
            }
            return true;
        }
        catch (CodeException e) {
            return false;
        }


    }

    /**
     * a method checking if a given line is a legal return statement
     * @param str - the given line
     * @return - true if the line is legal return statement, false otherwise
     */
    private boolean isReturn(String str) {
        Pattern ret = Pattern.compile("^[\\s]*return[\\s]*;[\\s]*$");
        Matcher retMatch = ret.matcher(str);
        return retMatch.matches();
    }

    /**
     * a method checking if a given line is a legal assignment of several variables
     * @param str - the given line
     * @return - true if the line is legal assignment of several variables, false otherwise
     */
    private boolean isMultipleVarAssign(String str)
    {
    	Pattern varsGetter = Pattern.compile
    			("[\\s]*(int|double|String|boolean|char)[\\s]+(.*)[\\s]*;[\\s]*");
    	Matcher getVars = varsGetter.matcher(str);
    	if (!getVars.matches())
    	{
    		return false;
    	}
    	String type = getVars.group(1);
    	String vars = getVars.group(2);
    	String[] varsArr = vars.split(",");
    	for (int i=0; i<varsArr.length; i++)
    	{
    		if (!this.isVarAssign(type+" "+varsArr[i]+";"))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * toString method
     * @return - the line's text string.
     */
    @Override
    public String toString()
    {
    	return this.line;
    }
    
}
