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
     * @throws CodeException 
     */
    public Block(Scope parent,  Map<Integer, Block> blocks) throws CodeException {
        super(parent);
        this.blocks=blocks;
       // this.mergeVariables(this.parent.getVariables(), this.getVariables());
        this.getVariables();
        super.runScope();
    }
    
    public Block(Scope parent) throws CodeException {
        super(parent);
        this.blocks= Collections.emptyMap();
        this.getVariables();
        this.runScope();
    }
    
    public String getScopeType()
    {
    	return "Block";
    }
}
