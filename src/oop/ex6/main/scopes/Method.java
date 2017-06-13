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
     * @param parent the MainScope
     * @param name methods's name
     */
    public Method(String[] lines, Scope parent, String name) {
        super(lines, parent);
        this.name = name;
    }

    /**
     * Getter
     * @return method's name
     */
    public String getName() {
        return name;
    }

}
