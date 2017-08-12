package AST;

import output.Print;
import enums.NodeType;

/**
 * The AST class Number.
 * This class is used to represent a value in the SIMPLE AST.
 * 
 */

public class Number extends Node
{
	
	/** The value. */
	private double arg;
	
	/**
	 * Instantiates a new Number node.
	 *
	 * @param arg the value
	 */
	public Number(double arg)
	{
		this.arg = arg;
		setType(NodeType.Number);	//Sets the node type
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getArg()
	{
		return arg;
	}
	
	public String toString()
	{
		return "Number(" + Print.format(arg) + ")";
	}
}
