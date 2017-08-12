package interpretation;

/**
 * The interpretation class Variable.
 * This class is used to represent a variable
 * 
 */

public class Variable 
{
	
	/** The name of the variable. */
	private String name;
	
	/** The current value of the variable. */
	private double value;
	
	/**
	 * Instantiates a new variable.
	 *
	 * @param name the name of the variable
	 * @param value the current value of this variable
	 */
	public Variable(String name, double value)
	{
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Gets the name of the variable.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the value of the variable.
	 *
	 * @return the value
	 */
	public double getValue()
	{
		return value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @param value the value
	 */
	public void setValue(double value)
	{
		this.value = value;
	}
}
