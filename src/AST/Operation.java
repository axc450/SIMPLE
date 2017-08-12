package AST;

import enums.NodeType;
import enums.OperationType;

/**
 * The AST class Operation.
 * This class is used to represent an arithmetic operation in the SIMPLE AST.
 * 
 */

public class Operation extends Node 
{
	
	/** The operation type. */
	private OperationType optype;
	
	/** The first operand. */
	private Node arg1 = null;
	
	/** The second operand. */
	private Node arg2 = null;
	
	/**
	 * Instantiates a new Operation node.
	 *
	 * @param type the operation type
	 */
	public Operation(OperationType type)
	{
		this.optype = type;
		setType(NodeType.Operation);	//Sets the node type
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
	 * @param arg the second operand
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
	 * Gets the operation type.
	 *
	 * @return the operation type
	 */
	public OperationType getOpType()
	{
		return optype;
	}
	
	public String toString()
	{
		String str = null;
		switch(optype)		//Select based on the operator
		{
			case Minus:
				str = "Operation(-, " + arg1 + ", " + arg2 + ")";
				break;
			case Plus:
				str = "Operation(+, " + arg1 + ", " + arg2 + ")";
				break;
			case Times:
				str = "Operation(*, " + arg1 + ", " + arg2 + ")";
				break;
			case Divide:
				str = "Operation(/, " + arg1 + ", " + arg2 + ")";
				break;
			default:
				str = "Unknown("+ arg1 + ", " + arg2 + ")";
				break;
		}
		return str;
	}
}
