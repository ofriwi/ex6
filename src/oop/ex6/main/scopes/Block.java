package oop.ex6.main.scopes;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import oop.ex6.main.buildingUnits.CodeException;

/**
 * Created by Ofri Wienner on 11/06/2017.
 * Block is an if or while block.
 */
public class Block extends Scope {

	private Map<Integer, Block> blocks;
	
    /**
     * Constructor
     * @param parent block's parent
     * @throws CodeException if block's code is invalid.
     */
    public Block(Scope parent,  Map<Integer, Block> blocks) throws CodeException {
        super(parent);
        this.blocks=blocks;
        this.getVariables();
        super.runScope();
    }

    /**
     * Constructor
     * @param parent Block's parent
     * @throws CodeException if block's code is invalid
     */
    public Block(Scope parent) throws CodeException {
        super(parent);
        this.blocks= Collections.emptyMap();
        this.getVariables();
        this.runScope();
    }

    /**
     * Get the type of the scope - a block - as String.
     * @return "Block"
     */
    public String getScopeType()
    {
    	return "Block";
    }
}
