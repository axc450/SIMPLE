package tokens;

import enums.TokenType;

/**
 * The token class Comma.
 * This class is used to represent a ',' token.
 * 
 */

public class Comma extends Token
{
	
	/**
	 * Instantiates a new comma token.
	 */
	public Comma()
	{
		setType(TokenType.Comma);	//Sets the token type
	}

	public String toString()
	{
		return "COMMA";
	}
}
