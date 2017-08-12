package tokens;

import enums.TokenType;

/**
 * The token class Times.
 * This class is used to represent a '*' token.
 * 
 */

public class Times extends Token
{
	
	/**
	 * Instantiates a new times token.
	 */
	public Times()
	{
		setType(TokenType.Times);		//Sets the token type
	}
	
	public String toString()
	{
		return "TIMES";
	}
}
