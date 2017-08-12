package AST;

import java.util.ArrayList;

import enums.NodeType;

/**
 * The AST class Def.
 * This class is used to represent a function definition in the SIMPLE AST.
 * 
 */

public class Def extends Node
{
	
	/** The name of the function. */
	private String name;
	
	/** The parameter list for the function. */
	private ArrayList<String> params = new ArrayList<String>();
	
	/** The block of code statements the function contains. */
	private Block block;
	
	/**
	 * Instantiates a new function definition node.
	 *
	 * @param name the name of the function
	 */
	public Def(String name)
	{
		this.name = name;
		setType(NodeType.Def);	//Sets the node type
	}
	
	/**
	 * Gets the name of the function.
	 *
	 * @return the name of the function
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the parameter list.
	 *
	 * @return the parameter lust
	 */
	public ArrayList<String> getParams() 
	{
		return params;
	}

	/**
	 * Sets the parameter list.
	 *
	 * @param params the parameter list
	 */
	public void setParams(ArrayList<String> params) 
	{
		this.params = params;
	}
	
	/**
	 * Gets the code block of the function.
	 *
	 * @return the code block of the function
	 */
	public Block getBlock() 
	{
		return block;
	}

	/**
	 * Sets the code block of the function.
	 *
	 * @param block the code block of the function
	 */
	public void setBlock(Block block) 
	{
		this.block = block;
	}
	
	public String toString()
	{
		return "Def(\"" + name + "\", " + params + ", " + block + ")";
	}
}
