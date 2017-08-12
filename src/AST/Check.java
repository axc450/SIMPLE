package AST;

import enums.CheckType;
import enums.NodeType;

/**
 * The AST class Check.
 * This class is used to represent a logical comparison in the SIMPLE AST.
 * 
 */

public class Check extends Node 
{
	
	/** The comparison operator. */
	private CheckType checktype;
	
	/** The first operand. */
	private Node arg1 = null;
	
	/** The second operand. */
	private Node arg2 = null;
	
	/**
	 * Instantiates a new check node.
	 */
	public Check()
	{
		setType(NodeType.Check);	//Sets the node type
	}
	
	/**
	 * Gets the first operand.
	 *
	 * @return the first operand
	 */
	public Node getArg1()
	{
		return arg1;
	}
	
	/**
	 * Gets the second operand.
	 *
	 * @return the second operand
	 */
	public Node getArg2()
	{
		return arg2;
	}
	
	/**
	 * Sets the first operand.
	 *
	 * @param arg the first operand
	 */
	public void setArg1(Node arg)
	{
		arg1 = arg;
	}
	
	/**
	 * Sets the second operand.
	 *
	 * @param arg the second operand
	 */
	public void setArg2(Node arg)
	{
		arg2 = arg;
	}
	
	/**
	 * Sets the comparison operator.
	 *
	 * @param type the comparison operator
	 */
	public void setType(CheckType type)
	{
		this.checktype = type;
	}
	
	/**
	 * Gets the comparison operator.
	 *
	 * @return the comparison operator
	 */
	public CheckType getCheckType()
	{
		return checktype;
	}

	public String toString()
	{
		String str = null;
		switch(checktype)	//Select based on the comparison operator
		{
			case LessThan:
				str = "Check(<, " + arg1 + ", " + arg2 + ")";
				break;
			case GreaterThan:
				str = "Check(>, " + arg1 + ", " + arg2 + ")";
				break;
			case Equals:
				str = "Check(=, " + arg1 + ", " + arg2 + ")";
				break;
			default:
				str = "Unknown("+ arg1 + ", " + arg2 + ")";
				break;
		}
		return str;
	}
}
