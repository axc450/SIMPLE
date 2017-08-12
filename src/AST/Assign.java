package AST;

import enums.NodeType;

/**
 * The AST class Assign.
 * This class is used to represent a variable assignment in the SIMPLE AST.
 * 
 */

public class Assign extends Node 
{
	
	/** Name of the variable. */
	private String arg1;
	
	/** The value of the variable. */
	private Node arg2 = null;
	
	/**
	 * Instantiates a new assign node.
	 *
	 * @param name the name of the variable
	 * @param value the value of the variable
	 */
	public Assign(String name, Node value)
	{
		arg1 = name;
		arg2 = value;
		setType(NodeType.Assign);	//Sets the node type
	}
	
	/**
	 * Gets the name of the variable.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return arg1;
	}
	
	/**
	 * Gets the value of the variable.
	 *
	 * @return the value of the variable
	 */
	public Node getValue()
	{
		return arg2;
	}

	public String toString()
	{
		return "Assign(\"" + arg1 + "\", " + arg2 + ")";
	}
}
