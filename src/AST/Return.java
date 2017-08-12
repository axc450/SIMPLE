package AST;

import enums.NodeType;

/**
 * The AST class Return.
 * This class is used to represent function return values in the SIMPLE AST.
 * 
 */

public class Return extends Node 
{
	
	/** The return value. */
	private Node arg;
	
	/**
	 * Instantiates a new Return node.
	 *
	 * @param value the return value
	 */
	public Return(Node value)
	{
		arg = value;
		setType(NodeType.Return);	//Sets the node type
	}
	
	/**
	 * Gets the return value.
	 *
	 * @return the return value
	 */
	public Node getArg()
	{
		return arg;
	}

	public String toString()
	{
		return "Return(" + arg + ")";
	}
}
