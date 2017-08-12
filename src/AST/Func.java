package AST;

import java.util.ArrayList;

import enums.NodeType;

/**
 * The AST class Func.
 * This class is used to represent a function call in the SIMPLE AST.
 * 
 */

public class Func extends Node
{
	
	/** The name of the function. */
	private String name;
	
	/** The arguments passed to the function. */
	private ArrayList<Node> args = new ArrayList<Node>();
	
	/**
	 * Instantiates a new Func node.
	 *
	 * @param name the name of the function
	 */
	public Func(String name)
	{
		this.name = name;
		setType(NodeType.Function);	//Sets the node type
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
	 * Gets the arguments of the function.
	 *
	 * @return the arguments of the function
	 */
	public ArrayList<Node> getArgs() 
	{
		return args;
	}

	/**
	 * Sets the arguments.
	 *
	 * @param args the arguments
	 */
	public void setArgs(ArrayList<Node> args) 
	{
		this.args = args;
	}

	public String toString()
	{
		return "Function(\"" + name + "\", " + args + ")";
	}
}
