package AST;

import java.util.ArrayList;

import enums.NodeType;

/**
 * The AST class Program.
 * This class is used to represent the top-most node in the SIMPLE AST.
 * 
 */

public class Program extends Node
{
	
	/** The list of function definitions. */
	private ArrayList<Def> funcs = new ArrayList<Def>();
	
	/**
	 * Instantiates a new Program node.
	 *
	 * @param funcs the list of function definitions
	 */
	public Program(ArrayList<Def> funcs)
	{
		this.funcs = funcs;
		setType(NodeType.Prog);	//Sets the node type
	}
	
	/**
	 * Gets the function definition list.
	 *
	 * @return the function definition list
	 */
	public ArrayList<Def> getFuncs()
	{
		return funcs;
	}
		
	public String toString()
	{
		String rtn = "---Program---";
		for(Def def:funcs)
		{
			rtn = rtn + "\n" + def;
		}
		return rtn;
	}
}
