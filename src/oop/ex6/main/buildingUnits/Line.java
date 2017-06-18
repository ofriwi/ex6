package oop.ex6.main.buildingUnits;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oop.ex6.main.scopes.Scope;

/**
 * Created by t8307673 on 11/06/2017.
 */
public class Line {
    private Scope parent;
    private String line;
    private int lineNumber;

	/**
	 * Constructor
	 * @param line line's text
	 * @param parent the scope contains this line
	 * @param lineNumber current line's number
	 */
	public Line(String line, Scope parent, int lineNumber) {
		this.parent = parent;
		this.line = line;
		this.lineNumber = lineNumber;
		this.execute();
	}

	/**
	 * Constructor
	 * @param line line's text
	 * @param parent the scope contains this line
	 */
	public Line(String line, Scope parent) {
		this.parent = parent;
		this.line = line;
		this.lineNumber = parent.getMainScope().getLineNumber();
		this.execute();
	}

    private void validate() throws CodeException 
    {
    	String type = roughSort(this.line);
    	switch (type) {
		case "INVALID":
			throw new CodeException("line "+this.lineNumber +" did not pass the first rough sort");
		case "OPEN":
			if (!this.validateOpener())
			{
				throw new CodeException("line "+this.lineNumber +" was"
						+ " iterperted as block opener but wasn't in the right template");
			}
			break;
/*		case "CLOSE":
			if (this.parent.getOpenedScopes()<1)
			{
				throw new CodeException("line "+this.lineNumber+" was interperted as a block closer,"
						+ " but there were no open blocks");
			}
			break;*/
		case "CODELINE":
			if (!this.validateCodeLine())
			{
				throw new CodeException(" line "+ this.lineNumber + " was"
						+ "interperted as a Standart codeline but wasn't in the right template");
			}
			break;
		default:
			break;
		}
    }
    
    private static String roughSort(String str)
    {
    	Pattern empty = Pattern.compile("[\\s]*");
    	Pattern comment = Pattern.compile("^\\/{2}.*$");
    	Pattern blockOpen = Pattern.compile("^[^\\/].*\\{$|\\{");
    	Pattern blockClose = Pattern.compile("^[\\s]*}[\\s]*$");
    	Pattern codeLine = Pattern.compile("^[^\\/].*;$|;");
    	
    	Matcher m=empty.matcher(str);
    	if (m.matches())
    	{ return "EMPTY"; }
    	m=comment.matcher(str);
    	if (m.matches())
    	{ return "COMMENT"; }
    	m=blockOpen.matcher(str);
    	if (m.matches())
    	{ return "OPEN"; }
    	m=blockClose.matcher(str);
    	if (m.matches())
    	{ return "CLOSE"; }
    	m=codeLine.matcher(str);
    	if (m.matches())
    	{ return "CODELINE"; }
    	else
    	{
    		return "INVALID";
    	} 	
    }
  
    public static int updateDepth(String str)
    {
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
    
    private boolean validateOpener()
    {
    	Pattern ifLine = Pattern.compile("^\\s*if\\s+\\((.*)\\)\\s*\\{\\s*");
    	Pattern whileLine = Pattern.compile("^\\s*while\\s+\\((.*)\\)\\s*\\{\\s*");
    	Pattern methodLine = Pattern.compile
    			("^\\s*void\\s+([a-zA-Z]{1}[a-zA-Z0-9_]*)\\s*\\((\\.*)\\)\\s*\\{\\s*");
    	Matcher ifMatch = ifLine.matcher(this.line);
    	Matcher whileMatch = whileLine.matcher(this.line);
    	Matcher methodMatch = methodLine.matcher(this.line);
    	if (ifMatch.matches())
    	{
    		ifMatch.find();
    		String cond = ifMatch.group(1);
    		return isCondition(cond);
    	}
    	if (whileMatch.matches())
    	{
    		whileMatch.find();
    		String cond = whileMatch.group(1);
    		return isCondition(cond);
    	}
    	if (methodMatch.matches() && this.parent.getMainScope()==this.parent)
    	{
    		methodMatch.find();
    		String declare = methodMatch.group(2);
    		return isParameterList(declare);
    	}
    	return false;
    }
    
    private boolean validateCodeLine()
    {
    	//complete this.
    	return (this.isVarAssign(this.line) || this.isVarValueChange(this.line) 
    			|| this.isMethodCall(this.line) || this.isReturn(this.line));
    }    
    
    private void execute()
    {
    	
    }

    private void createVariable(String name, String type, boolean isFinal) 
    {
    	this.parent.addVariable(new Variable(name, type, isFinal));
    }   
    private static boolean isValidVarName(String name)
    {
    	Pattern varName = Pattern.compile("([a-zA-Z]{1}[a-zA-Z0-9_]*|[_][a-zA-Z0-9_]+)");
    	Matcher vaMatch = varName.matcher(name);
    	return (vaMatch.matches());
    }

    private boolean isValidVarValue(String type, String value)
    {
    	Pattern intVal = Pattern.compile("-?[0-9]+");
    	Pattern doubleVal = Pattern.compile("-?[0-9]+\\.?[0-9]*");
    	Pattern StringVal = Pattern.compile("^\".*\"$");
    	Pattern booleanVal = Pattern.compile("true|false|-?[0-9]+\\.?[0-9]*");
    	Pattern charVal = Pattern.compile("^\\'.\\'$");
    	Matcher m;
    	switch (type) {
		case "int":
			m=intVal.matcher(value);
			break;
		case "double":
			m=doubleVal.matcher(value);
			break;
		case "String":
			m=StringVal.matcher(value);
			break;
		case "boolean":
			m=booleanVal.matcher(value);
			break;
		case "char":
			m=charVal.matcher(value);
			break;
		default:
			return false;
		}
    	boolean goodVar;
    	try {
    	goodVar = (this.parent.isContainVariable(value) && 
    			this.parent.getVariable(value).getType().equals(type));
    	}
    	catch ( CodeException e)
    	{
    		goodVar=false;
    	}
    	return (m.matches() || goodVar);
    }
    
    private boolean isVarAssign(String str)
    {
    	boolean isAssign = true;
    	Pattern varDeclare = Pattern.compile("^[\\s]*(final[\\s]+)?(int|double|String|char|boolean)"
    			+ "[\\s]+([^\\s]*)[\\s]*(|=[\\s]*[^\\s]+.*[\\s]*);[\\s]*");
    	Matcher decMatch = varDeclare.matcher(str);
    	if (!decMatch.matches())
    	{
    		return false;
    	}
    	boolean isFinal = (decMatch.group(1)!=null);
    	String type = decMatch.group(2);
    	String name = decMatch.group(3);
    	String value = "";
    	if (!decMatch.group(4).equals(""))
    	{
    		Pattern getVal = Pattern.compile("=[\\s]*([^\\s]+.*[^\\s]+)[\\s]*");
    		Matcher valMatch = getVal.matcher(decMatch.group(4));
    		valMatch.find();
    		value = valMatch.group(2);
    	}
    	else
    	{
    		isAssign=false;
    	}
    	if (!(type.equals("int")||type.equals("double")||type.equals("String")
    			||type.equals("boolean")||type.equals("char")))
    	{
    		return false;
    	}
    	if (!isValidVarName(name))
    	{
    		return false;
    	}
    	if (isAssign && !this.isValidVarValue(type, value))
    	{
    		return false;
    	}
    	if (!isAssign && isFinal)
    	{
    		return false;
    	}
    	return true;
    }
    
    private static boolean isParameterList(String str)
    {
    	String[] paramList = str.split(",");
    	Pattern varDeclare = Pattern.compile(
    			"\\s*(int|double|String|boolean|char)\\s+([a-zA-Z]{1}[a-zA-Z0-9_]*|[_][a-zA-Z0-9_]+)\\s*");
    	for (String i : paramList)
    	{
    		Matcher declareMatch = varDeclare.matcher(i);
    		if (declareMatch.matches())
    		{
    			break;
    		}
    		return false;
    	}
    	return true;
    }
 
    private boolean isCondition(String str)
    {
    	String[] sepCond = str.split("\\|\\||&&"); 
    	Pattern literalCond= Pattern.compile("\\s*(true|false)\\s*");
    	Pattern numCond = Pattern.compile("\\s*[\\d]+[\\.]?[\\d]*\\s*");
    	Pattern varCond = Pattern.compile("^\\s*([a-zA-Z]{1}[a-zA-Z0-9_]*|[_][a-zA-Z0-9_]+)\\s*$");
    	
    	for (String i : sepCond)
    	{
    		Matcher lit = literalCond.matcher(i);
    		Matcher num = numCond.matcher(i);
    		Matcher var = varCond.matcher(i);
    		if (lit.matches())
    		{ break; }
    		if (num.matches())
    		{ break; }
    		if (var.matches())
    		{
    			String varName = var.group(1);
    			if (this.parent.isContainVariable(varName))
    			{
    				break;
    			}
    		}
    		return false;
    	}
    	return true;
    	
    }

    private boolean isVarValueChange(String str)
    {
    	Pattern varChange = Pattern.compile("[\\s]*([^\\s]+)[\\s]*=[\\s]*([^\\s].*[^\\s])[\\s]*;[\\s]*");
    	Matcher match = varChange.matcher(str);
    	if (!match.matches())
    	{
    		return false;
    	}
    	String name = match.group(1);
    	String value = match.group(2);
    	if (!this.parent.isContainVariable(name))
    	{
    		return false;
    	}
    	try // never throws exception 
    	{ 
    		if (!this.isValidVarValue(this.parent.getVariable(name).getType(), value))
    		{
    			return false;
    		}
    		if (this.parent.getVariable(name).isFinal())
    		{
    			return false;
    		}
    	}
    	catch (CodeException e)
    	{
    		return false;
    	}
    	return true;
    	
    }
    
    private boolean isMethodCall(String str)
    {
    	Pattern methodCall = Pattern.compile("[\\s]*([^\\s]*)[\\s]*\\((.*)\\)[\\s]*;[\\s]*");
    	Matcher callMatcher = methodCall.matcher(str);
    	if (!callMatcher.matches())
    	{
    		return false;
    	}
    	String name = callMatcher.group(1);
    	String vars = callMatcher.group(2);
    	//check if vars is compatable with the method
    	String[] varList = vars.split(",");
    	try
    	{
    	Variable[] input = this.parent.getMainScope().getMethod(name).getinput();
    	if (input.length!=varList.length)
    	{
    		return false;
    	}
    	for (int i=0; i<input.length; i++)
    	{
    		if (!this.isValidVarValue(input[i].getType(), varList[i]))
    		{
    			return false;
    		}
    	}
    	return true;
    	} // never throws exceptions
    	catch (CodeException e)
    	{
    		return false;
    	}
    	
    	
    }
    
    private boolean isReturn(String str)
    {
    	Pattern ret = Pattern.compile("^[\\s]*return[\\s]*;[\\s]*$");
    	Matcher retMatch = ret.matcher(str);
    	return retMatch.matches();
    }
    
}
