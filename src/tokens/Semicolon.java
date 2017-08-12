package tokens;

import enums.TokenType;

/**
 * The token class Semicolon.
 * This class is used to represent a ';' token.
 * 
 */

public class Semicolon extends Token
{
	
	/**
	 * Instantiates a new semicolon token.
	 */
	public Semicolon()
	{
		setType(TokenType.Semicolon);	//Sets the token type
	}

	public String toString()
	{
		return "SEMICOLON";
	}
}
