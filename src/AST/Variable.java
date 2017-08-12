package AST;

import enums.NodeType;

/**
 * The AST class Variable.
 * This class is used to represent a variable in the SIMPLE AST.
 * 
 */

public class Variable extends Node
{
	
	/** The name of the variable. */
	private String arg;
	
	/**
	 * Instantiates a new Variable node.
	 *
	 * @param arg the name of the variable
	 */
	public Variable(String arg)
	{
		this.arg = arg;
		setType(NodeType.Variable);
	}
	
	/**
	 * Gets the name of the variable.
	 *
	 * @return the name of the variable
	 */
	public String getArg()
	{
		return arg;
	}
	
	public String toString()
	{
		return "Variable(\"" + arg + "\")";
	}
}
