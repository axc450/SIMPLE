package tokens;

import enums.TokenType;

/**
 * The token class CloseBrace.
 * This class is used to represent a '{' token.
 * 
 */

public class CloseBrace extends Token 
{
	
	/**
	 * Instantiates a new close brace token.
	 */
	public CloseBrace()
	{
		setType(TokenType.CloseBrace);	//Sets the token type
	}

	public String toString()
	{
		return "CLOSEBRACE";
	}
}
