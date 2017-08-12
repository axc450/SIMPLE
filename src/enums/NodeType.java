package enums;

/**
 * The enumeration NodeType.
 * This enumeration contains all possible AST node types.
 * 
 */

public enum NodeType 
{
	/** Value node. */					Number, 
	/** Operation node. */				Operation, 
	/** Variable node. */				Variable, 
	/** Block node. */					Block, 
	/** Assignment node. */				Assign, 
	/** Comparison node. */				Check, 
	/** 'If' node. */					If, 
	/** 'Repeat' node. */				Repeat, 
	/** Function call node. */			Function, 
	/** Function definition node. */	Def, 
	/** Program node */					Prog, 
	/** 'Return' node. */				Return
}
