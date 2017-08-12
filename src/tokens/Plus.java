package tokens;

import enums.TokenType;

/**
 * The token class Plus.
 * This class is used to represent a '+' token.
 * 
 */

public class Plus extends Token
{
	
	/**
	 * Instantiates a new plus token.
	 */
	public Plus()
	{
		setType(TokenType.Plus);	//Sets the token type
	}

	public String toString()
	{
		return "PLUS";
	}
}
