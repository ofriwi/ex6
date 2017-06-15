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
    private int num;

    public Line(String line, Scope parent, int num) {
        this.parent = parent;
        this.line = line;
        this.num=num;
        this.execute();
    }

    private void validate() throws CodeException 
    {
    	String type = this.roughSort();
    	switch (type) {
		case "INVALID":
			throw new CodeException("line "+this.num+" did not pass the first rough sort");
		case "OPEN":
			if (!this.validateOpener())
			{
				throw new CodeException("line "+this.num+" was"
						+ " iterperted as block opener but wasn't in the right template");
			}
			break;
		case "CLOSE":
			//SOME CODE....................(maybe do nothing).............................
			break;
		case "CODELINE":
			//SOME CODE...................................................................
			break;
		default:
			break;
		}
    }
    
    private String roughSort()
    {
    	Pattern empty = Pattern.compile("[\\s]*");
    	Pattern comment = Pattern.compile("^\\/{2}.*$");
    	Pattern blockOpen = Pattern.compile("^[^\\/].*\\{$|\\{");
    	Pattern blockClose = Pattern.compile("^[\\s]*}[\\s]*$");
    	Pattern codeLine = Pattern.compile("^[^\\/].*;$|;");
    	
    	Matcher m=empty.matcher(this.line);
    	if (m.matches())
    	{ return "EMPTY"; }
    	m=comment.matcher(this.line);
    	if (m.matches())
    	{ return "COMMENT"; }
    	m=blockOpen.matcher(this.line);
    	if (m.matches())
    	{ return "OPEN"; }
    	m=blockClose.matcher(this.line);
    	if (m.matches())
    	{ return "CLOSE"; }
    	m=codeLine.matcher(this.line);
    	if (m.matches())
    	{ return "CODELINE"; }
    	else
    	{
    		return "INVALID";
    	}
    	
    	
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
    			var.find();
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
    
    private boolean isParameterList(String str)
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
    	if (methodMatch.matches())
    	{
    		methodMatch.find();
    		String declare = methodMatch.group(2);
    		return isParameterList(declare);
    	}
    	return false;
    	
    	
  //  	String[] opener = this.line.split("\\(|\\)");
    //	String[] declaration=(" "+opener[0]).split("\\s+");//starts from 1
    	
    	
   /* 	Pattern first = Pattern.compile("[^\\s][^\\s\\(]");
    	Matcher m = first.matcher(this.line);
    	m.find();
    	String firstWord=m.group();
    	if (!firstWord.equals("if") && !firstWord.equals("while") && !firstWord.equals("void"))
    	{
    		return false;
    	}
    	if (firstWord.equals("void"))
    	{
    		
    	}*/
    }
    
    private boolean validateCodeLine()
    {
    	//complete this.
    }
    
    private void execute()
    {
    	
    }

    private void createVariable() {
    }

    private void createScope() {
    }

}
