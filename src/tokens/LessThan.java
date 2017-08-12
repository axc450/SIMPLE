package tokens;

import enums.TokenType;

/**
 * The token class LessThan.
 * This class is used to represent a '<' token.
 * 
 */

public class LessThan extends Token
{
	
	/**
	 * Instantiates a new less than token.
	 */
	public LessThan()
	{
		setType(TokenType.LessThan);	//Sets the token type
	}
	
	public String toString()
	{
		return "LESSTHAN";
	}
}
