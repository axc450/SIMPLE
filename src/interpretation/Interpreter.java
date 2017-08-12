package interpretation;

import enums.ErrorType;
import enums.NodeType;
import AST.*;
import AST.Number;
import AST.Variable;
import output.Error;
import output.Print;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Interpreter class.
 * Traverses an AST, converting each node into a java instruction and processing that instruction 
 * Third stage of Compilation/Interpretation
 * 
 */

public class Interpreter 
{	
	
	/** User input scanner used for the built-in function read(). */
	static Scanner scanIn = new Scanner(System.in);
	
	/** List of function definitions used for function calls. */
	static ArrayList<Def> funcs = new ArrayList<Def>();
	
	/** The return value used for recursive function calls. */
	static double returnValue;
	
	/**
	 * Begin to interpret an AST.
	 *
	 * @param program the AST
	 */
	public static void startInterpret(Program program)
	{	
		for(Def def:program.getFuncs())	//Loop through each function definition in the AST
		{
			addFunc(def);	//Add the function to the function list
		}
		
		Def mainFunc = findFunc("main");	//Find the main function
		interpret(mainFunc.getBlock(), new Environment());	//Interpret the main function
	}
	
	/**
	 * Adds a function definition to the global list of functions if it is a valid definition.
	 *
	 * @param funcToAdd the function definition to add
	 */
	private static void addFunc(Def funcToAdd)
	{
		for(Def func:funcs)	//Loop through each function already in the list
		{
			if(func.getName().equals(funcToAdd.getName()))	//If the function to add is already in the list, throw an error
			{
				Error.print("There was more then one function found called: \"" + func.getName() + "\"", ErrorType.Runtime, true);
			}
		}
		
		boolean rtn = false;	//Represents if the function to add has a return value
		for(Node node:funcToAdd.getBlock().getArg())	//For each toplevel node within the function definition
		{
			if(node.getType() == NodeType.Return)	//If a return if found as a toplevel node
			{
				rtn = true;
			}
		}
		
		if(funcToAdd.getName().equals("main"))	//If this function is the main function
		{
			if(rtn)	//If it contains a return, throw an error
			{
				Error.print("A return call was found in the \"main\" function! This function cannot contain a return statement", ErrorType.Runtime, true);
			}
			
			if(!funcToAdd.getParams().isEmpty())	//If it contains parameters, throw an error
			{
				Error.print("The \"main\" function was given parameters! This function has to contain no parameters", ErrorType.Runtime, true);
			}
			
			funcs.add(funcToAdd);	//Add the main function to the list
			return;
		}
		
		if(rtn)	//If the function contains a return statement as its toplevel node
		{
			funcs.add(funcToAdd);	//Add the function to the list	
		}
		else	//Else throw an error
		{
			Error.print("A return call was not found in the main body of the function: \"" + funcToAdd.getName() + "\"", ErrorType.Runtime, true);
		}
	}
	
	/**
	 * Find a function definition in the global list of functions.
	 *
	 * @param name the name of the function
	 * @return the function definition
	 */
	private static Def findFunc(String name)
	{
		for(Def def:funcs)	//Loop through each function already in the list
		{
			if(def.getName().equals(name))	//If the function is in the list, return the function
			{
				return def;
			}
		}
		//Else throw an error
		Error.print("Attempted to call \"" + name + "\" but this function does not exist!", ErrorType.Runtime, true);
		return null;
	}
	
	/**
	 * Interprets an AST node
	 *
	 * @param node the node to interpret
	 * @param env the environment variable
	 */
	private static void interpret(Node node, Environment env)
	{
		switch(node.getType())	//Select based on the type of the node
		{
			case Number:
			case Operation:
			case Variable:
			case Function:
				calculate(node, env);	//These nodes are evaluable, so calculate their value
				break;
			case Block:
				for(Node line:((Block)node).getArg())	//A block just contains more nodes, so interpret each sub-node
				{
					interpret(line, env);
					if(line.getType() == NodeType.Return)
					{
						break;
					}
				}
				break;
			case Assign:
				assign((Assign)node, env);	//Assign a variable a value in the environment
				break;
			case If:
				if(check(((If)node).getCheck(), env) == true)	//Evaluate an If node using Java's If
				{
					interpret(((If)node).getBlock1(), env);
				}
				else
				{
					if (((If)node).getBlock2() != null)
					{
						interpret(((If)node).getBlock2(), env);
					}
				}
				break;
			case Repeat:
				if(((Repeat)node).getn().getType() == NodeType.Check)	//If a check is found, should perform like a while loop
				{
					Print.progPrint("Repeating block until " + (Check)((Repeat)node).getn() + " is false", NodeType.Repeat);
					Print.indent();
					Boolean n = check((Check)((Repeat)node).getn(), env);	//Check the condition
					while(n)
					{
						interpret(((Repeat)node).getBlock(), env);	//Interpret the block
						n = check((Check)((Repeat)node).getn(), env);	//Re-check the condition
					}
				}
				else	//Repeat a certain number of times
				{
					double n = calculate(((Repeat)node).getn(), env);	//Calculate the number of times to repeat
					Print.progPrint("Repeating block " + Print.format(n) + " time/s...", NodeType.Repeat);
					Print.indent();
					while(n>0)	//Loop n times
					{
						interpret(((Repeat)node).getBlock(), env);	//Interpret the block
						n--;	//Decrement n
					}
				}
				Print.outdent();
				Print.progPrint("Done!", NodeType.Repeat);
				break;
			case Return:
				returnValue = calculate(((Return)node).getArg(), env);	//Set the return value
				break;
			default:
				Error.print("The interpreter found something it does know know how to process: " + node, ErrorType.Runtime, true);
		}
	}
	
	/**
	 * Interprets an evaluable AST node and returns the result
	 *
	 * @param node the node to interpret
	 * @param env the environment variable
	 * @return the result
	 */
	private static double calculate(Node node, Environment env)
	{
		if(node.getType() == NodeType.Number)	//If the node is a number
		{
			Print.progPrint("Number = " + Print.format(((Number)node).getArg()), NodeType.Number);
			return ((Number)node).getArg();	//Return the numbers value
		}
		if(node.getType() == NodeType.Variable)	//If the node is a variable
		{
			Print.progPrint("Variable " + "\"" + ((Variable)node).getArg() + "\" = " + Print.format(env.lookup(((Variable)node).getArg())), NodeType.Variable);
			return env.lookup(((Variable)node).getArg());	//Lookup the variable and return the variable's value
		}
		if(node.getType() == NodeType.Function)	//If the node is a function call
		{
			return func((Func)node, env);	//Interpret the function and return its return value
		}
		
		//Else the node must be an operation
		
		Print.progPrint("Performing \"" + ((Operation)node).getOpType() + "\" on " + ((Operation)node).getArg1() + " and " + ((Operation)node).getArg2() + "...", NodeType.Operation);
		Print.indent();
		double value;
		switch(((Operation)node).getOpType())	//Select based on the operation type
		{
			case Minus:	// '-'
				value = calculate(((Operation)node).getArg1(), env) - calculate(((Operation)node).getArg2(), env);	//Calculate
				Print.outdent();
				Print.progPrint("= " + Print.format(value), NodeType.Operation);
				return value;	//Return the value
			case Plus:	// '+'
				value = calculate(((Operation)node).getArg1(), env) + calculate(((Operation)node).getArg2(), env);	//Calculate
				Print.outdent();
				Print.progPrint("= " + Print.format(value), NodeType.Operation);
				return value; 	//Return the value
			case Times:	// '*'
				value = calculate(((Operation)node).getArg1(), env) * calculate(((Operation)node).getArg2(), env);	//Calculate
				Print.outdent();
				Print.progPrint("= " + Print.format(value), NodeType.Operation);
				return value; 	//Return the value
			case Divide:	// '\'
				try
				{
					value = calculate(((Operation)node).getArg1(), env) / calculate(((Operation)node).getArg2(), env);	//Calculate
					Print.outdent();
					Print.progPrint("= " + Print.format(value), NodeType.Operation);
					return value;	//Return the value
				}
				catch(ArithmeticException e)	//Catches a divide by 0 error
				{
					Error.print("The interpreter attempted to divide by 0!", ErrorType.Runtime, true);
				}
			default:
				Error.print("The interpreter found something it does know know how to process: " + node, ErrorType.Runtime, true);
				return 0;
		}
	}
	
	/**
	 * Assigns a variable a value in the environment
	 *
	 * @param node the node to interpret
	 * @param env the environment variable
	 */
	private static void assign(Assign node, Environment env)
	{
		Print.progPrint("Assigning " + "\"" + node.getName() + "\"...", NodeType.Assign);
		Print.indent();
		double value = calculate(node.getValue(),env);	//Calculate the value of the variable
		Print.outdent();
		Print.progPrint("Assigning " + "\"" + node.getName() + "\" to " + Print.format(value), NodeType.Assign);
		env.set(node.getName(), value);	//Set the variable its value
	}
	
	/**
	 * Performs a logical comparison and returns the result
	 *
	 * @param node the node to interpret
	 * @param env the environment variable
	 * @return if the comparison returned true
	 */
	private static Boolean check(Check node, Environment env)
	{
		Print.progPrint("Checking " + node + "...", NodeType.Check);
		Print.indent();
		Boolean rtn = false;	//Create the return value
		switch(node.getCheckType())
		{
		case Equals:	// '='
			rtn = calculate(node.getArg1(), env) == calculate(node.getArg2(), env);	//Perform the comparison
			break;
		case GreaterThan:	// '>'
			rtn = calculate(node.getArg1(), env) > calculate(node.getArg2(), env);	//Perform the comparison
			break;
		case LessThan:	// '<'
			rtn = calculate(node.getArg1(), env) < calculate(node.getArg2(), env);	//Perform the comparison
			break;
		default:
			Error.print("The interpreter found something it does know know how to process: " + node, ErrorType.Runtime, true);
			return false;
		}
		Print.outdent();
		Print.progPrint(rtn.toString().substring(0, 1).toUpperCase() + rtn.toString().substring(1) + "!", NodeType.Check);
		return rtn;
	}
	
	/**
	 * Performs a function call and returns the result
	 *
	 * @param node the node to interpret
	 * @param env the environment variable
	 * @return the funcations return value
	 */
	private static double func(Func func, Environment env)
	{
		Print.progPrint("Executing function: " + func + "....", NodeType.Function);
		Print.indent();
		double rtn;	//Create the return value
		switch(func.getName())	//Check to see if the function being called is a built in function
		{
			case "print":	//Built in function to print a value
				Print.progPrint("Printing arguments...", NodeType.Function);
				for(Node arg:func.getArgs())
				{
					System.out.println(Print.format(calculate(arg, env)));	//Print the arguments to the user (bypassing verbose mode)
				}
				rtn = func.getArgs().size();	//Every function must return a value, print returns how many arguments it was passed
				break;
			case "read":	//Built in function to read a value
				checkargs(func, 0);	//Check there was no arguments given
				Print.progPrint("Reading user input...", NodeType.Function);
				try
				{
					System.out.print("Please enter a number: ");
					double value = scanIn.nextDouble();	//Parse the input using Java
					Print.progPrint("= " + Print.format(value), NodeType.Function);
					rtn = value;
				}
				catch(Exception e)	//If the user entered something that wasn't a number
				{
					Error.print("Could not read user input! Did you enter a number?", ErrorType.Runtime, true);
					rtn = 0;
				}
				break;
			default:	//User defined function
				Def def = findFunc(func.getName());	//Find the user defined function in the global functions list
				checkargs(func, def.getParams().size());	//Check the number of arguments given matches the number of parameters expected
				Environment newEnv = new Environment();	//Create a new Environment variable for this function call
				
				//CALL BY VALUE
				
				for(int i = 0; i<def.getParams().size(); i++)	//Loop through each argument and calculate it before passing it into the function
				{
					double value = calculate(func.getArgs().get(i),env);	//Calculate the argument
					newEnv.set(def.getParams().get(i), value);	//Set the argument as a variable in the environment
				}
				interpret(def.getBlock(), newEnv);	//Interpret the function
				rtn = returnValue;
				break;
		}
		Print.outdent();
		Print.progPrint("Function call complete! Returning " + Print.format(rtn), NodeType.Function);
		return rtn;
	}

	/**
	 * Check the number of arguments in a function matches the number of parameters expected
	 *
	 * @param func the function to check
	 * @param n the amount of parameters expected
	 */
	private static void checkargs(Func func, int n) 
	{
		if(func.getArgs().size() != n)
		{
			Error.print("The function \"" + func.getName() + "\" expected " + n + " argument(s), but " + func.getArgs().size() + " were given!", ErrorType.Runtime, true);
		}
	}
}
