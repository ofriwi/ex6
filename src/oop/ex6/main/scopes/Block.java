package oop.ex6.main.scopes;

import java.util.Map;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Block is an if or while block.
 */
public class Block extends Scope {

	private Map<Integer, Block> blocks;
	
    /**
     * Constructor
     * @param lines block's lines as strings
     * @param parent block's parent
     */
    public Block(String[] lines, Scope parent,  Map<Integer, Block> blocks) {
        super(lines, parent);
        this.blocks=blocks;
    }
    
    public String getScopeType()
    {
    	return "Block";
    }
}
