package tokens;

import enums.TokenType;

/**
 * The token class OpenBracket.
 * This class is used to represent a '(' token.
 * 
 */

public class OpenBracket extends Token 
{
	
	/**
	 * Instantiates a new open bracket token.
	 */
	public OpenBracket()
	{
		setType(TokenType.OpenBracket);	//Sets the token type
	}

	public String toString()
	{
		return "OPENBRACKET";
	}
}
