package tokens;

import enums.TokenType;

/**
 * The token class CloseBracket.
 * This class is used to represent a ')' token.
 * 
 */

public class CloseBracket extends Token 
{
	
	/**
	 * Instantiates a new close bracket token.
	 */
	public CloseBracket()
	{
		setType(TokenType.CloseBracket);	//Sets the token type
	}
	
	public String toString()
	{
		return "CLOSEBRACKET";
	}
}
