package oop.ex6.main.buildingUnits;

/**
 * Created by t8307673 on 11/06/2017.
 */
public class Variable {
    public static final String INT = "int";
    public static final String DOUBLE = "double";
    public static final String STRING = "String";
    public static final String BOOLEAN = "boolean";
    public static final String CHAR = "char";

    public static String allVariables(){
        final String OR = "|";
        return INT+OR+DOUBLE+OR+STRING+OR+BOOLEAN+OR+CHAR;
    }


    private String type;
    private String name;

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
}
