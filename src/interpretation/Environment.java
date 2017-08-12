package interpretation;

import java.util.ArrayList;

import output.Error;
import enums.ErrorType;

/**
 * The interpretation class Environment.
 * This class is used to represent the current program environment.
 * 
 */

public class Environment 
{
	
	/** List of variables and their values. */
	ArrayList<Variable> data;
	
	/**
	 * Instantiates a new environment.
	 */
	public Environment()
	{
		data = new ArrayList<Variable>(); 
	}
	
	/**
	 * Sets a variable in the environment.
	 * If the variable doesn't exist, create it.
	 * If the variable does exist, replace its value
	 *
	 * @param name the name of the variable
	 * @param value the value of the variable
	 */
	public void set(String name, double value)
	{
		for(Variable var:data)
		{
			if (var.getName().equals(name))	//If a variable already exists
			{
				var.setValue(value);	//Change its value
				return;
			}
		}
		data.add(new Variable(name, value));	//Else add a new variable
	}
	
	/**
	 * Lookup a variables value in the environment.
	 * If a variable cannot be found, throw a fatal error
	 *
	 * @param name the name of the variable to lookup
	 * @return the value of the variable
	 */
	public double lookup(String name)
	{
		for(Variable var:data)
		{
			if (var.getName().equals(name))
			{
				return var.getValue();
			}
		}
		//If the variable cannot be found
		Error.print("Failed trying to find the value of '" + name + "'! Has it been declared?", ErrorType.Runtime, true);
		return -1;
	}
}
