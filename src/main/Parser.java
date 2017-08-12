package main;
import enums.CheckType;
import enums.ErrorType;
import enums.OperationType;
import enums.TokenType;
import output.Error;
import output.Print;

import java.util.ArrayList;

import AST.*;
import AST.Number;
import tokens.*;

/**
 * The Parser class.
 * Reads token list generated from the Lexer and creates an Abstract Syntax Tree that the interpreter can process
 * Second stage of Compilation/Interpretation
 * LL(1) parser format
 * 
 */

public class Parser 
{
	
	/** Stores the current token that is being read. */
	private static Token lookahead;
	
	/** The current number of tokens that have been processed. */
	private static int tokenNumber;
	
	/** The list of tokens that still have to be processed (received from the Lexer). */
	private static ArrayList<Token> tokens;
	
	/**
	 * Begin to parse a list of tokens.
	 *
	 * @param tokenInput the token list received from the Lexer
	 * @return the AST of the token list input
	 */
	public static Program parse(ArrayList<Token> tokenInput)
	{
		tokenNumber = 0;		//No tokens have been processed
		tokens = tokenInput;
		
		setLookahead();		//Move the lookahead to the first token	
		Program tree = PROG();	//Parse a PROG
		Print.print(tree.toString());	//Print the AST
		return tree;	//Return the AST
	}
	
	/**
	 * PROG parse.
	 * Parses a program
	 *
	 * @return the program
	 */
	private static Program PROG()
	{
		ArrayList<Def> funcs = new ArrayList<Def>();	//Create a list of function definitions
		while(lookahead != null)	//While there are still tokens left
		{
			funcs.add(DEF());	//Parse all DEF and add to program
		}
		return new Program(funcs);	//Return the program
	}
	
	/**
	 * DEF parse.
	 * Parses a function definition
	 *
	 * @return the function definition
	 */
	private static Def DEF()
	{
		checkEOF();	//Check there are still tokens left
		if (lookahead.getType() != TokenType.Word)	//First token of a DEF must be a word (function name)
		{
			sendParseError("Function Definition", lookahead.getType().toString());
		}
		Def rtn = new Def(((Word)lookahead).getArg());	//Create the DEF node
		setLookahead();	//Move the lookahead
		OPENBRACKET();	//Parse a OPENBRACKET
		if(lookahead.getType() != TokenType.CloseBracket)	//If a CLOSEBRACKET does not follow (params are present)
		{
			rtn.setParams(PARAMS());	//Parse PARAMS
		}
		CLOSEBRACKET();	//Parse a CLOSEBRACKET
		rtn.setBlock(BLOCK());	//Parse a BLOCK
		return rtn;	//The function definition is now complete
	}

	/**
	 * BLOCK parse.
	 * Parses a code block
	 *
	 * @return the code block
	 */
	private static Block BLOCK()
	{
		OPENBRACE();	//First token of BLOCK must be a OPENBRACE
		return BLOCK_LINE(new Block());	//Parse BLOC_LINE
	}
	
	/**
	 * BLOCK_LINE parse.
	 * Parses lines of a block
	 *
	 * @param current the current list of block lines
	 * @return the complete code block
	 */
	private static Block BLOCK_LINE(Block current)
	{
		if(lookahead.getType() == TokenType.CloseBrace)	//If the BLOCK_LINE has ended
		{
			setLookahead();	
			return current;
		}
		else
		{
			current.addLine(LINE());	//Parse a LINE
			return BLOCK_LINE(current);
		}
	}
	
	/**
	 * LINE parse.
	 * Parses a code line
	 *
	 * @return the complete code line
	 */
	private static Node LINE()
	{
		Node rtn;	//Create return value
		checkEOF();	//Check there are still tokens left
		if(lookahead.getType() == TokenType.Word)	//If first token is a word
		{
			switch(((Word)lookahead).getArg())	//Check if that word is a keyword
			{
				case "if":
					return IF();	//Parse IF
				case "repeat":
					return REPEAT();	//Parse REPEAT
				case "return":
					return RETURN();	//PARSE RETURN
			}
		}
		
		if (tokenNumber+1 < tokens.size())	//If this is not last token in the list
		{
			if(tokens.get(tokenNumber).getType() == TokenType.Equals)	//Choose to parse a ASSIGN or VALUE_EXP if a '=' is found
			{
				rtn = ASSIGN();
			}
			else
			{
				rtn = VALUE_EXP(null);
			}
		}
		else
		{
			rtn = VALUE_EXP(null);
		}
		SEMICOLON();	//End code line with a ';'
		return rtn;
	}
	
	/**
	 * IF parse.
	 * Parses an If statement
	 *
	 * @return the If
	 */
	private static If IF()
	{
		checkEOF();	//Check there are still tokens left
		if((lookahead.getType() != TokenType.Word) || !(((Word)lookahead).getArg().equals("if")))	//First token of IF must be 'if'
		{
			sendParseError("If", lookahead.getType().toString());		
		}
		setLookahead();	//Move the lookahead
		
		OPENBRACKET();	//Parse a OPENBRACKET
		If rtn = new If(CHECK());	//Create return value and parse the CHECK
		CLOSEBRACKET();	//Parse a CLOSEBRACKET
		
		rtn.setBlock1(BLOCK());	//Parse a BLOCK
		ELSE(rtn);	//Parse a ELSE
		return rtn;
	}
	
	/**
	 * ELSE parse.
	 * Parses an ELSE statement
	 *
	 * @param node the IF that this ELSE would be joined to
	 */
	private static void ELSE(If node)
	{
		checkEOF();	//Check there are still tokens left
		if((lookahead.getType() == TokenType.Word) && ((Word)lookahead).getArg().equals("else"))	//First token of ELSE must be 'else'
		{
			setLookahead();	//Move the lookahead
			node.setBlock2(BLOCK());	//Parse a BLOCK
		}
	}
	
	/**
	 * REAPEAT parse.
	 * Parses a REPEAT statement
	 *
	 * @return the Repeat
	 */
	private static Repeat REPEAT()
	{
		checkEOF();	//Check there are still tokens left
		if((lookahead.getType() != TokenType.Word) || !(((Word)lookahead).getArg().equals("repeat")))	//First token of REPEAT must be 'repeat'
		{
			sendParseError("Repeat", lookahead.getType().toString());	
		}
		setLookahead();	//Move the lookahead
		
		OPENBRACKET();	//Parse a OPENBRACKET
		Node val;	//The number of times to repeat or the repeat until comparison
		if(tokens.get(tokenNumber).getType() == TokenType.CloseBracket)	//If the next token is a '('
		{
			val = VALUE();	//Parse a VALUE
		}
		else
		{
			val = CHECK();	//Parse a CHECK
		}
		CLOSEBRACKET();	//Parse a CLOSEBRACKET
		
		Repeat rtn = new Repeat(val);	//Create return value
		rtn.setBlock(BLOCK());	//Parse a BLOCK
		return rtn;	//Return the Repeat
	}
	
	/**
	 * ASSIGN parse.
	 * Parses a variable assignment
	 *
	 * @return the assign
	 */
	private static Assign ASSIGN()
	{
		checkEOF();	//Check there are still tokens left
		if (lookahead.getType() != TokenType.Word)	//First token of a ASSIGN must be a word (variable name)
		{
			sendParseError("Variable Assignment", lookahead.getType().toString());	
		}
		String var = ((Word)lookahead).getArg();	//Variable name
		setLookahead();	//Move the lookahead
		EQUALS();	//Parse a EQUALS
		return new Assign(var, VALUE_EXP(null));	//Return the assignment
	}
	
	/**
	 * CHECK parse.
	 * Parses an arithmetic comparison
	 *
	 * @return the check
	 */
	private static Check CHECK()
	{
		Check rtn = new Check();	//Create return value
		rtn.setArg1(VALUE_EXP(null));	//Parse a VALUE_EXP
		CHECKOP(rtn);	//Parse a CHECKOP
		rtn.setArg2(VALUE_EXP(null));	//Parse a VALUE_EXP
		return rtn;	//Return the check
	}
	
	/**
	 * CHECK parse.
	 * Parses an arithmetic comparison
	 *
	 * @return the return
	 */
	private static Return RETURN()
	{
		checkEOF();	//Check there are still tokens left
		if((lookahead.getType() != TokenType.Word) || !(((Word)lookahead).getArg().equals("return")))	//First token of RETURN must be 'return'
		{
			sendParseError("Return", lookahead.getType().toString());	
		}
		setLookahead();	//Move the lookahead
		Return rtn = new Return(VALUE_EXP(null));	//Create return value and parse a VALUE_EXP
		SEMICOLON();	//Parse a SEMICOLON
		return rtn;
	}
	
	/**
	 * VALUE_EXP parse.
	 * Parses an arithmetic expression
	 *
	 * @param current the current expression
	 * @return the expression
	 */
	private static Node VALUE_EXP(Node current)
	{
		checkEOF();	//Check there are still tokens left
		if(lookahead.getType() == TokenType.OpenBracket)	//An expression can start with '('
		{
			
			OPENBRACKET();	//Parse an OPENBRACKET
			Node rtn;	//Create return value
			rtn = VALUE_EXP(current);	//Parse a VALUE_EXP
			CLOSEBRACKET();	//Parse a CLOSEBRACKET
			rtn = OP_EXP(rtn);	//PARSE A OP_EXP
			return rtn;
		}
		else
		{
			if(current == null)
			{
				current = VALUE();	//Parse a VALUE
				return OP_EXP(current);	//Parse a OP_EXP
			}
			else
			{
				((Operation)current).setArg2(OP_EXP(VALUE()));	//Sets the AST to compute in the correct order
				return current;
			}
		}
	}
	
	/**
	 * OP_EXP parse.
	 * Parses an arithmetic expression that contains an operator
	 *
	 * @param current the current expression
	 * @return the expression
	 */
	private static Node OP_EXP(Node current)
	{
		if(lookahead != null)	//If an operator exists
		{
			if(lookahead.getType() == TokenType.Plus)	// '+'
			{
				OP();	//Parse a OP
				Operation op = new Operation(OperationType.Plus);	//Create a new '+' node
				op.setArg1(current);	//Set the first argument
				return VALUE_EXP(op);	//Parse a VALUE_EXP
			}
			else if(lookahead.getType() == TokenType.Minus)	// '-'
			{
				OP();	//Parse a OP
				Operation op = new Operation(OperationType.Minus);	//Create a new '-' node
				op.setArg1(current);	//Set the first argument
				return VALUE_EXP(op);	//Parse a VALUE_EXP
			}
			else if(lookahead.getType() == TokenType.Times)	// '*'
			{
				OP();	//Parse a OP
				Operation op = new Operation(OperationType.Times);	//Create a new '*' node
				op.setArg1(current);	//Set the first argument
				return VALUE_EXP(op);	//Parse a VALUE_EXP
			}
			else if(lookahead.getType() == TokenType.Divide)	// '\'
			{
				OP();	//Parse a OP
				Operation op = new Operation(OperationType.Divide);	//Create a new '\' node
				op.setArg1(current);	//Set the first argument
				return VALUE_EXP(op);	//Parse a VALUE_EXP
			}
		}
		return current;	//Return current
	}
	
	/**
	 * FUNC parse.
	 * Parses a function call
	 *
	 * @return the function call
	 */
	private static Func FUNC()
	{
		Func rtn;	//Create return value
		checkEOF();	//Check there are still tokens left
		if(lookahead.getType() != TokenType.Word)		//First token of a FUNC must be a word (function name)
		{
			sendParseError("Function Call", lookahead.getType().toString());	
		}
		rtn = new Func(((Word)lookahead).getArg());	//Create the Func node
		setLookahead();	//Move the lookahead
		OPENBRACKET();	//Parse a OPENBRACKET
		if(lookahead.getType() != TokenType.CloseBracket)	//If a CLOSEBRACKET does not follow (arguments are present)
		{
			rtn.setArgs(ARGS());	//Parse arguments
		}
		CLOSEBRACKET();	//Parse a CLOSEBRACKET
		return rtn;
	}
	
	/**
	 * ARGS parse.
	 * Parses function arguments
	 *
	 * @return the list of arguments
	 */
	private static ArrayList<Node> ARGS()
	{
		ArrayList<Node> args = new ArrayList<Node>();	//Create a new list of arguments
		args.add(VALUE_EXP(null));	//Parse a VALUE_EXP
		args.addAll(ARGS_LST(args));	//Parse a ARGS_LIST
		return args;
	}
	
	/**
	 * ARGS_LST parse.
	 * Parses extra function arguments
	 *
	 * @param current the current list of function arguments
	 * @return the list of arguments
	 */
	private static ArrayList<Node> ARGS_LST(ArrayList<Node> current)
	{
		if(lookahead.getType() == TokenType.Comma)	//If a comma exists
		{
			setLookahead();	//Move the lookahead
			current.add(VALUE_EXP(null));	//Parse a VALUE_EXP
			return ARGS_LST(current);	//Parse and return a ARGS_LST
		}
		return new ArrayList<Node>();	//Return the list of arguments
	}
	
	/**
	 * PARAMS parse.
	 * Parses function parameters
	 *
	 * @return the list of parameters
	 */
	private static ArrayList<String> PARAMS()
	{
		ArrayList<String> params = new ArrayList<String>();	//Create a new list of parameters
		
		checkEOF();	//Check there are still tokens left
		if(lookahead.getType() != TokenType.Word)	//If word is not found
		{
			sendParseError("Parameter List", lookahead.getType().toString());	
		}
		params.add(((Word)lookahead).getArg());	//Add the parameter to the list
		setLookahead();	//Move the lookahead
		
		params.addAll(PARAMS_LST(params));	//Parse PARAMS_LST
		return params;
	}
	
	/**
	 * PARAMS_LST parse.
	 * Parses extra function parameters
	 *
	 * @param current the current list of parameters
	 * @return the list of parameters
	 */
	private static ArrayList<String> PARAMS_LST(ArrayList<String> current)
	{
		if(lookahead.getType() == TokenType.Comma)	//If a comma exists
		{
			setLookahead();	//Move the lookahead
			checkEOF();	//Check there are still tokens left
			if(lookahead.getType() != TokenType.Word)	//If word is not found
			{
				sendParseError("Parameter List", lookahead.getType().toString());
			}
			current.add(((Word)lookahead).getArg());	//Add the parameter to the list
			setLookahead();	//Move the lookahead
			return PARAMS_LST(current);	//Parse and return a PARAMS_LST
		}
		return new ArrayList<String>();
	}
	
	/**
	 * VALUE parse.
	 * Parses a value (variable or number)
	 *
	 * @return the node
	 */
	private static Node VALUE()
	{
		switch(lookahead.getType())	//Select based on the current token
		{
			case Number:
			case Minus:
				return PURE_VALUE();	//Parse PURE_VALUE
			case Word:
				Node rtn;	//Create return value
				if (tokenNumber+1 < tokens.size())		//If this is not last token in the list
				{
					if(tokens.get(tokenNumber).getType() == TokenType.OpenBracket)	//If the next token is a '('
					{
						rtn = FUNC();	//Parse a FUNC
					}
					else
					{
						rtn = new Variable(((Word)lookahead).getArg());	//Create a Variable node
						setLookahead();	//Move the lookahead
					}
				}
				else
				{
					rtn = new Variable(((Word)lookahead).getArg());	//Create a Variable node
					setLookahead();	//Move the lookahead
				}
				return rtn;
			default:
				sendParseError("Value", lookahead.getType().toString());
				return null;
		}
	}

	/**
	 * VALUE parse.
	 * Parses a value (number)
	 *
	 * @return the number
	 */
	private static Number PURE_VALUE()
	{
		checkEOF();	//Check there are still tokens left
		Number rtn = null;	//Create return value
		switch(lookahead.getType())	//Select based on the current token
		{
			case Number:
				rtn = new Number(((tokens.Number)lookahead).getArg());	//Create a Number node
				setLookahead();	//Move the lookahead
				break;
				
			case Minus:
				setLookahead();	//Move the lookahead
				checkEOF();	//Check there are still tokens left
				if (lookahead.getType() == TokenType.Number)	//If a Number exists
				{
					rtn = new Number(((tokens.Number)lookahead).getArg()*-1);	//Create a Number node
					setLookahead();	//Move the lookahead
				}
				else
				{
					sendParseError("Value", lookahead.getType().toString());
				}
				break;
				
			default:
				sendParseError("Value", lookahead.getType().toString());
		}
		return rtn;
	}
	
	/**
	 * OP parse.
	 * Parses an arithmetic operator
	 */
	private static void OP() 
	{
		checkEOF();	//Check there are still tokens left
		switch(lookahead.getType())	//Select based on the current token
		{
			case Plus:
			case Minus:
			case Times:
			case Divide:
				setLookahead();	//Move the lookahead
				break;
			default:
				sendParseError("Arithemtic Operation", lookahead.getType().toString());
		}
	}
	
	/**
	 * CHECKOP parse.
	 * Parses a comparison operator
	 *
	 * @param node the comparison node
	 */
	private static void CHECKOP(Check node) 
	{
		checkEOF();	//Check there are still tokens left
		switch(lookahead.getType())	//Select based on the current token
		{
			case GreaterThan:
				node.setType(CheckType.GreaterThan);	//Set the node type
				break;
			case LessThan:
				node.setType(CheckType.LessThan);	//Set the node type
				break;
			case Equals:
				node.setType(CheckType.Equals);	//Set the node type
				break;
			default:
				sendParseError("Comparison Operation", lookahead.getType().toString());;
		}
		setLookahead();	//Move the lookahead
	}
	
	/**
	 * OPENBRACKET parse.
	 * Parses a '('
	 */
	private static void OPENBRACKET() 
	{
		checkEOF();	//Check there are still tokens left
		if(lookahead.getType() != TokenType.OpenBracket)	//If a '(' exists
		{
			sendParseError("(", lookahead.getType().toString());
		}
		setLookahead();	//Move the lookahead
	}
	
	/**
	 * CLOSEBRACKET parse.
	 * Parses a ')'
	 */
	private static void CLOSEBRACKET() 
	{
		checkEOF();	//Check there are still tokens left
		if(lookahead.getType() != TokenType.CloseBracket)	//If a ')' exists
		{
			sendParseError(")", lookahead.getType().toString());
		}
		setLookahead();	//Move the lookahead
	}
	
	/**
	 * OPENBRACE parse.
	 * Parses a '{'
	 */
	private static void OPENBRACE() 
	{
		checkEOF();	//Check there are still tokens left
		if(lookahead.getType() != TokenType.OpenBrace)	//If a '{' exists
		{
			sendParseError("{", lookahead.getType().toString());
		}
		setLookahead();	//Move the lookahead
	}
	
	/**
	 * SEMICOLON parse.
	 * Parses a ';'
	 */
	private static void SEMICOLON() 
	{
		checkEOF();	//Check there are still tokens left
		if(lookahead.getType() != TokenType.Semicolon)	//If a ';' exists
		{
			sendParseError(";", lookahead.getType().toString());
		}
		setLookahead();	//Move the lookahead
	}
	
	/**
	 * EQUALS parse.
	 * Parses a '='
	 */
	private static void EQUALS() 
	{
		checkEOF();	//Check there are still tokens left
		if(lookahead.getType() != TokenType.Equals)	//If a =;' exists
		{
			sendParseError("=", lookahead.getType().toString());
		}
		setLookahead();	//Move the lookahead
	}
	
	/**
	 * Check there are still tokens left in the input.
	 */
	private static void checkEOF() 
	{
		if (lookahead == null)
		{
			Error.print("Unexpected end of file!" , ErrorType.Parse, true);
		}
	}

	/**
	 * Sets the lookahead along by 1 token.
	 */
	private static void setLookahead()
	{
		if (tokenNumber < tokens.size())	//If another token exists
		{
			lookahead = tokens.get(tokenNumber);	//Get the next token
		}
		else
		{
			lookahead = null;	
		}
		tokenNumber++;	//Increment token number
	}
	
	/**
	 * Helper function used to print Parser error messages
	 *
	 * @param str1 the expected string
	 * @param str2 the found string
	 */
	private static void sendParseError(String str1, String str2) 
	{
		Error.print("Unexpected token found! Expected '" + str1 + "' but found '" + str2 + "'!" , ErrorType.Parse, true);
	}
}
