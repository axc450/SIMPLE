package tokens;

import enums.TokenType;

/**
 * The token class GreaterThan.
 * This class is used to represent a '>' token.
 * 
 */

public class GreaterThan extends Token
{
	
	/**
	 * Instantiates a new greater than token.
	 */
	public GreaterThan()
	{
		setType(TokenType.GreaterThan);	//Sets the token type
	}
	
	public String toString()
	{
		return "GREATERTHAN";
	}
}
