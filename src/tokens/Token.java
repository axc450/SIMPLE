package tokens;

import enums.TokenType;

/**
 * The token class Token.
 * This abstract class is used to represent a general token.
 * 
 */

public abstract class Token 
{
	
	/** The type of this token. */
	private TokenType type;
	
	/**
	 * Gets the token type.
	 *
	 * @return the token type
	 */
	public TokenType getType()
	{
		return type;
	}
	
	/**
	 * Sets the token type.
	 *
	 * @param type the token type
	 */
	public void setType(TokenType type)
	{
		this.type = type;
	}
}