package tokens;

import output.Print;
import enums.TokenType;

/**
 * The token class Number.
 * This class is used to represent a numeric token.
 * 
 */

public class Number extends Token 
{
	
	/** The numeric value of this token. */
	private double arg;
	
	/**
	 * Instantiates a new number token.
	 *
	 * @param numb the value
	 */
	public Number(double numb)
	{
		this.arg = numb;
		setType(TokenType.Number);	//Sets the token type
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getArg()
	{
		return this.arg;
	}

	public String toString()
	{
		return "NUMBER=" + Print.format(arg);
	}
}