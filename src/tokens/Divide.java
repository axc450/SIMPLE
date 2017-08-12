package tokens;

import enums.TokenType;

/**
 * The token class Divide.
 * This class is used to represent a '\' token.
 * 
 */

public class Divide extends Token
{
	
	/**
	 * Instantiates a new divide token.
	 */
	public Divide()
	{
		setType(TokenType.Divide);	//Sets the token type
	}
	
	public String toString()
	{
		return "DIVIDE";
	}
}
