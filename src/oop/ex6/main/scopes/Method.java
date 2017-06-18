package oop.ex6.main.scopes;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Method is the scope of one method in the file.
 */
public class Method extends Scope {

    private String name;

    /**
     * Constructor
     * @param lines method's lines as strings
     * @param mainScope the MainScope
     * @param decleration methods's decleration
     */
    public Method(String[] lines, Scope mainScope, String decleration) {
        super(lines, mainScope);
        // TODO: analyze the decleration
        // this.name = decleration;
    }

    /**
     * Getter
     * @return method's name
     */
    public String getName() {
        return name;
    }

}
