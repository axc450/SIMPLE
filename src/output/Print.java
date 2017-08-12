package output;
import enums.NodeType;

/**
 * The output class Print.
 * This class is used to output non-error messages.
 * 
 */

public class Print 
{
	
	/** Verbose mode. */
	public static boolean verbose;
	
	/** Amount of indent for displayed messages. */
	private static String indent = "";
	
	/**
	 * Prints a message if verbose mode is on.
	 *
	 * @param str the message to print
	 */
	public static void print(String str)
	{
		if (verbose)
		{
			System.out.println(str);
		}
	}
	
	/**
	 * Prints a program debug message if verbose mode is on.
	 * Each message here will contain indentation and a prefix based on the node type
	 *
	 * @param str the message to print
	 * @param type the node type of the message
	 */
	public static void progPrint(String str, NodeType type)
	{
		if (verbose)
		{
			String prefix;
			switch(type)	//Select based on the node type
			{
			case Assign:
				prefix = "[A]";
				break;
			case Number:
				prefix = "[N]";
				break;
			case Operation:
				prefix = "[O]";
				break;
			case Variable:
				prefix = "[V]";
				break;
			case Check:
				prefix = "[C]";
				break;
			case Repeat:
				prefix = "[R]";
				break;
			case Function:
				prefix = "[F]";
				break;
			default:
				prefix = "[?]";
				break;
			
			}
			System.out.println(prefix + " " + indent + str); //Display the message with indentation and the prefix
		}
	}
	
	/**
	 * Adds an indentation.
	 */
	public static void indent()
	{
		indent = indent += "\t";
	}
	
	/**
	 * Removes an indentation.
	 */
	public static void outdent()
	{
		indent = indent.substring(0, indent.length()-1);
	}
	
	/**
	 * Formats a double to always display the correct number of decimal places.
	 *
	 * @param number the number to format
	 * @return the number in string form after being formatted
	 */
	public static String format(double number)
	{
	    if(number == (long)number)
	    {
	        return String.format("%d", (long)number);
	    }
	    else
	    {
	        return String.format("%s", number);
	    }
	}
}
