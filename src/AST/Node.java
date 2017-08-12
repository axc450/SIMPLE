package AST;

import enums.NodeType;

/**
 * The AST class Node.
 * This abstract class is used to represent a general AST node.
 * 
 */

public abstract class Node 
{
	
	/** The type of this node. */
	NodeType type;
	
	/**
	 * Gets the node type.
	 *
	 * @return the node type
	 */
	public NodeType getType()
	{
		return type;
	}
	
	/**
	 * Sets the node type.
	 *
	 * @param type the node type
	 */
	public void setType(NodeType type)
	{
		this.type = type; 
	}
	
	public String toString()
	{
		return this.getClass().getName();
	}
}
