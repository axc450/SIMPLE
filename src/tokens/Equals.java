package tokens;

import enums.TokenType;

/**
 * The token class Equals.
 * This class is used to represent a '=' token.
 * 
 */

public class Equals extends Token
{
	
	/**
	 * Instantiates a new equals token.
	 */
	public Equals()
	{
		setType(TokenType.Equals);	//Sets the token type
	}
	
	public String toString()
	{
		return "EQUALS";
	}
}
