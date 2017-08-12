package AST;

import enums.NodeType;

/**
 * The AST class If.
 * This class is used to represent the control statement 'if' in the SIMPLE AST.
 * 
 */

public class If extends Node
{
	
	/** The check. */
	private Check check;
	
	/** The 'if' code block. */
	private Block block1;
	
	/** The 'else' code block. */
	private Block block2 = null;
	
	/**
	 * Instantiates a new If node.
	 *
	 * @param arg the comparison check
	 */
	public If(Check arg)
	{
		check = arg;
		setType(NodeType.If);	//Sets the node type
	}
	
	/**
	 * Gets the check.
	 *
	 * @return the check
	 */
	public Check getCheck()
	{
		return check;
	}
	
	/**
	 * Gets the 'if' block.
	 *
	 * @return the 'if' block
	 */
	public Block getBlock1() 
	{
		return block1;
	}

	/**
	 * Sets the 'if block.
	 *
	 * @param block the 'if' block
	 */
	public void setBlock1(Block block) 
	{
		block1 = block;
	}
	
	/**
	 * Gets the 'else' block.
	 *
	 * @return the 'else' block
	 */
	public Block getBlock2() 
	{
		return block2;
	}

	/**
	 * Sets the 'else' block.
	 *
	 * @param block the 'else' block
	 */
	public void setBlock2(Block block) 
	{
		block2 = block;
	}

	public String toString()
	{
		return "If(" + check + ", " + block1 + ", " + block2 + ")";
	}
}
