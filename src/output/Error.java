package output;
import enums.ErrorType;

/**
 * The output class Error.
 * This class is used to output error messages.
 * 
 */

public class Error 
{
	
	/**
	 * Prints an error message.
	 *
	 * @param str the error message
	 * @param type the error type
	 * @param fatal if true, the program will end after the error has been displayed
	 */
	public static void print(String str, ErrorType type, Boolean fatal)
	{
		switch(type)	//Select based on the error type
		{
			case Lex:
				System.out.println("Lex Error! [" + str + "]");
				break;
			case Parse:
				System.out.println("Parse Error! [" + str + "]");
				break;
			case Program:
				System.out.println("Program Error! [" + str + "]");
				break;
			case Runtime:
				System.out.println("Runtime Error! [" + str + "]");
				break;
			default:
				System.out.println("Unknown Error! [" + str + "]");
				break;
		}
		
		if(fatal)
		{
			Print.print("Exiting...");
			System.exit(1);		//End the program if the error is fatal
		}
	}
}
