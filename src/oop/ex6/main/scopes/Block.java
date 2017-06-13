package oop.ex6.main.scopes;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Block is an if or while block.
 */
public class Block extends Scope {

    /**
     * Constructor
     * @param lines block's lines as strings
     * @param parent block's parent
     */
    public Block(String[] lines, Scope parent) {
        super(lines, parent);
    }
}
