package enums;

/**
 * The enumeration TokenType.
 * This enumeration contains all possible lexical token types.
 * 
 */

public enum TokenType 
{
	/** Alphabetic. */	Word, 
	/** Numeric. */		Number, 
 	/** + */			Plus, 
 	/** - */			Minus, 
 	/** ( */			OpenBracket, 
 	/** ) */			CloseBracket, 
 	/** * */			Times, 
 	/** \ */			Divide, 
 	/** ; */			Semicolon, 
 	/** =. */			Equals, 
 	/** < */			LessThan, 
 	/** > */			GreaterThan, 
 	/** { */			OpenBrace, 
 	/** } */			CloseBrace, 
 	/** , */			Comma
}