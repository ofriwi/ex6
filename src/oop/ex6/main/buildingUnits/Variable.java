package oop.ex6.main.buildingUnits;

/**
 * Created by t8307673 on 11/06/2017.
 */
public class Variable {
    // ****************************************************************
    // * STATICS                                                      *
    // ****************************************************************
    public static final String INT = "int";
    public static final String DOUBLE = "double";
    public static final String STRING = "String";
    public static final String BOOLEAN = "boolean";
    public static final String CHAR = "char";

    /**
     * All variables as regex. (int or double or String ...)
     * @return regex of all variables
     */
    public static String allVariables(){
        final String OR = "|";
        return INT+OR+DOUBLE+OR+STRING+OR+BOOLEAN+OR+CHAR;
    }

    // ****************************************************************
    // * NON-STATICS                                                  *
    // ****************************************************************

    private String type;
    private String name;
    private boolean isFinal;
    private boolean isAssigned;

    public Variable(String name, String type, boolean isFinal, boolean isAssigned) {
        this.type = type;
        this.name = name;
        this.isFinal = isFinal;
        this.isAssigned=isAssigned;
    }

    /**
     * Getter
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Getter
     * @return name
     */
    public String getName() {
        return name;
    }
    
    public boolean isFinal()
    {
    	return this.isFinal;
    }
    public boolean isAssigned()
    {
    	return this.isAssigned;
    }
}
