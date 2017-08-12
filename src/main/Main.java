package main;
import interpretation.Interpreter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import AST.Program;
import output.Error;
import output.Print;
import tokens.*;
import enums.ErrorType;

/**
 * The Main class.
 */

public class Main 
{	
	
	/**
	 * The main method.
	 *
	 * @param args the command line arguments
	 */
	
	public static void main(String[] args) 
	{
		if(args.length > 2)	//Argument check
		{
			//Invalid number of arguments
			Error.print("The number of arguments is invalid! Expected: Path to SIMPLE code file", ErrorType.Program, true);
		}
		
		String fileArg = "";
		
		if(args.length == 0)		//Argument check
		{
			JFileChooser fc = new JFileChooser();	//Allow user to choose a file if non given
			FileNameExtensionFilter filter = new FileNameExtensionFilter("SIMPLE Code File", "simple", "SIMPLE");
			fc.setFileFilter(filter);
			int rtn = fc.showOpenDialog(null);
		    if(rtn == JFileChooser.APPROVE_OPTION) 
		    {
		       fileArg = fc.getSelectedFile().getPath();
		    }
		    else
		    {
		    	//Invalid file selection
		    	Error.print("You did not select a valid file! Exiting...", ErrorType.Program, true);
		    }
		}
		
		if(args.length == 1)		//Argument check
		{
			if(args[0].equals("-v"))
			{
				Print.verbose = true;	//Set verbose mode to true
				
				JFileChooser fc = new JFileChooser();	//Allow user to choose a file if non given
				FileNameExtensionFilter filter = new FileNameExtensionFilter("simple", "SIMPLE");
				fc.setFileFilter(filter);
				int rtn = fc.showOpenDialog(null);
			    if(rtn == JFileChooser.APPROVE_OPTION) 
			    {
			       fileArg = fc.getSelectedFile().getPath();
			    }
			    else
			    {
			    	//Invalid file selection
			    	Error.print("You did not select a valid file! Exiting...", ErrorType.Program, true);
			    }
			}
			else
			{
				fileArg = args[0];
			}
		}
		
		if(args.length == 2)	//Argument check
		{
			if(args[0].equals("-v"))	//Verbose mode check
			{
				Print.verbose = true;	//Set verbose mode to true
				fileArg = args[1];
			}
			else
			{
				//Couldn't find a path to a code file
				Error.print("An argument was not recognised! Expected: Path to SIMPLE code file", ErrorType.Program, true);
			}
		}
		
		Print.print("Welcome to the SIMPLE Interpreter - Verbose Mode\n");
		
		Print.print("Attempting to load file: " + fileArg);
		
		Path filePath = FileSystems.getDefault().getPath(fileArg);	//Attempt to load file
		if (!Files.isRegularFile(filePath))
		{
			//The given path points to a non-file
			Error.print("The path given was not found or was not a file: " + fileArg, ErrorType.Program, true);			
		}
		if (!(filePath.getFileName().toString().endsWith(".simple") || filePath.getFileName().toString().endsWith(".SIMPLE")))
		{
			//The given file does not have the extension .simple
			Error.print("The path given does not seem to be a SIMPLE code file. Attempting to process anyway...", ErrorType.Program, false);
		}
		
		ArrayList<String> codeRaw = new ArrayList<String>();	//Stores each line of the file
		try
		{
			codeRaw = (ArrayList<String>) Files.readAllLines(filePath);	//Read the file lines
			Print.print("Loaded File!");
		}
		catch (IOException e)
		{
			Error.print("The file could not be read: " + fileArg, ErrorType.Program, true);
		}
		
		Print.print("Lexing file...\n");
		ArrayList<Token> tokens = Lexer.lex(codeRaw);	//Lex the code
		Print.print("\nFile lexed successfully!");
		Print.print("Parsing tokens...\n");
		Program ast = Parser.parse(tokens);		//Parse the code
		Print.print("\nParsing completed successfully!");
		Print.print("Running code...\n");
		long startTime = System.nanoTime();	//Start execution timer
		Interpreter.startInterpret(ast);	//Interpret the code
		long endTime = System.nanoTime();	//Stop execution timer
		Print.print("\nProgram completed successfully! [" + (endTime - startTime)/1000000 + "ms]");
	}
}
