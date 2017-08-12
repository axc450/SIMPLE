package tokens;

import enums.TokenType;

/**
 * The token class Minus.
 * This class is used to represent a '-' token.
 * 
 */

public class Minus extends Token 
{
	
	/**
	 * Instantiates a new minus token.
	 */
	public Minus()
	{
		setType(TokenType.Minus);	//Sets the token type
	}
	
	public String toString()
	{
		return "MINUS";
	}
}
