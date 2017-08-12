package AST;

import enums.NodeType;

/**
 * The AST class Repeat.
 * This class is used to represent the control statement 'repeat' in the SIMPLE AST.
 * 
 */

public class Repeat extends Node
{
	
	/** The amount of times or condition to repeat. */
	private Node n;
	
	/** The code block. */
	private Block block;
	
	/**
	 * Instantiates a new Repeat node.
	 *
	 * @param n the amount of times or condition to repeat
	 */
	public Repeat(Node n)
	{
		this.n = n;
		setType(NodeType.Repeat);	//Sets the node type
	}
	
	/**
	 * Gets the amount of times or condition to repeat.
	 *
	 * @return the amount of times or condition to repeat
	 */
	public Node getn()
	{
		return n;
	}
	
	/**
	 * Gets the code block.
	 *
	 * @return the code block
	 */
	public Block getBlock() 
	{
		return block;
	}

	/**
	 * Sets the code block.
	 *
	 * @param block the code block
	 */
	public void setBlock(Block block) 
	{
		this.block = block;
	}

	public String toString()
	{
		return "Repeat(" + n + ", " + block + ")";
	}
}
