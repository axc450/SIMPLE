package tokens;

import enums.TokenType;

/**
 * The token class OpenBrace.
 * This class is used to represent a '{' token.
 * 
 */

public class OpenBrace extends Token 
{
	
	/**
	 * Instantiates a new open brace token.
	 */
	public OpenBrace()
	{
		setType(TokenType.OpenBrace);	//Sets the token type
	}
	
	public String toString()
	{
		return "OPENBRACE";
	}
}
