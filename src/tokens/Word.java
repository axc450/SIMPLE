package tokens;

import enums.TokenType;

/**
 * The token class Word.
 * This class is used to represent an alphabetic token.
 * 
 */

public class Word extends Token 
{
	
	/** The alphabetic value of this token. */
	private String arg;
	
	/**
	 * Instantiates a new word token.
	 *
	 * @param str the value
	 */
	public Word(String str)
	{
		this.arg = str;
		setType(TokenType.Word);	//Sets the token type
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getArg()
	{
		return this.arg;
	}

	public String toString()
	{
		return "WORD=" + arg;
	}
}
