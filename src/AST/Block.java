package AST;

import java.util.ArrayList;
import enums.NodeType;

/**
 * The AST class Block.
 * This class is used to represent a code block in the SIMPLE AST.
 * 
 */

public class Block extends Node
{
	
	/** The list of code statements. */
	private ArrayList<Node> arg;
	
	/**
	 * Instantiates a new block node.
	 */
	public Block()
	{
		arg = new ArrayList<Node>();
		setType(NodeType.Block);	//Sets the node type
	}
	
	/**
	 * Gets the list of code statements.
	 *
	 * @return the list of code statements
	 */
	public ArrayList<Node> getArg()
	{
		return arg;
	}
	
	/**
	 * Adds another code line.
	 *
	 * @param node the AST node to add
	 */
	public void addLine(Node node)
	{
		arg.add(node);
	}

	public String toString()
	{
		return "Block(" + arg.toString().substring(1, arg.toString().length()-1) + ")";
	}
}
