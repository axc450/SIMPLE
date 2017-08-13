# SIMPLE

Open-Source Custom Imperative Programming Language. 
Designed for Education, Powered by Java.

_The SIMPLE project was originally developed as part of a Computer Science BSc Degree._

## Introduction

The SIMPLE language was designed as an introduction to programming, a general purpose language supporting many of the core programming concepts without having confusing syntax or complex set up. Once familiar with SIMPLE, other popular imperative languages such as Java or C will become much easier to learn due to the sharing of common syntax and features. It combines the simplicity of its syntax with the sophistication and power of the Java language.

## Getting Started

As the SIMPLE language is interpreted by Java, make sure you have the latest version of Java installed.

```
https://java.com/en/download/
```

The interpreter itself is a single Jar file however some test code is included as examples of possible programs. To run the interpreter, navigate to the folder in which the Jar is contained, and run the follow from a terminal/command line.

```java -jar simple.jar
```
You can also specify the SIMPLE code file to run straight from the command line.

```java -jar simple.jar Path/To/Code/File
```
Verbose mode can be turned on by specifying the command line argument ‘-v’ as shown below. 

```java -jar simple.jar -v
```
These are the only command line arguments available to the interpreter. They can be combined as follows.

```java -jar simple.jar -v Path/To/Code/File
```

## Functions

The 'main' function is required to be defined for a SIMPLE program to run. This is where code interpretation begins.

```
main()
{

}
```

### Program Functions

- `read()` Read user input. Returns the input.
- `print(a, b, c...)` Prints the arguments given. Returns the number of arguments printed.

## Errors

If the SIMPLE interpreter finds something it does not know how to process, an error will be shown to the user and the program will stop. There are 4 types of errors and multiple error messages that fit into these 4 categories:
- `[Program Error]`    There was a general problem with the interpreter- `[Lex Error]`        There was a problem with the code Lexer- `[Parse Error]`      There was a problem with the code Parser- `[Runtime Error]`    There was a problem when running code

### Program Errors

```
Program Error! [The number of arguments is invalid! Expected: Path to SIMPLE code file]
```
You entered an invalid number of command line arguments. The maximum number is 2.

```
Program Error! [You did not select a valid file! Exiting...]
```
You did not select a valid file with the file chooser.

```
Program Error! [An argument was not recognised! Expected: Path to SIMPLE code file]
```
You typed an invalid argument. The only arguments that are valid are the path to the SIMPLE code file and ‘-v’.


```
Program Error! [The path given was not found or was not a file: <path>]
```
The file path you input either wasn't found or wasn't a file (e.g. a folder).

```
Program Error! [The path given does not seem to be a SIMPLE code file. Attempting to process anyway...]
```
The file given did not have the file extension ‘.simple’.

```
Program Error! [The file could not be read: <path>]
```

The file given could not be read by Java for unknown reasons.

### Lex Errors

```
Lex Error! [Unknown Character Found: <char> on line <line> at position <pos>]
```
You entered an unknown character in the source code.

### Parse Errors

```
Parse Error! [Unexpected end of file!]
```
The Parser ran out of input when processing the AST.

```Parse Error! [Unexpected token found! Expected <char> but found <char>!]
```
The parser found unexpected input when processing the AST.

### Runtime Errors

```
Runtime Error! [A return call was not found in the main body of the function: <func>]
```
You created a function that does not return a value. Ensure there exists a ‘return’ statement in the main body of the function.```
Runtime Error! [There was more then one function found called: <func>]
```
You created two or more functions with the same name. Each function must have a unique name.

```Runtime Error! [A return call was found in the "main" function! This function cannot contain a return statement]
```
You have a ‘return’ statement in the main function, remove it to avoid this error.

```Runtime Error! [The "main" function was given parameters! This function has to contain no parameters]
```
The main function must be defined with no parameters: `main(){}`

```Runtime Error! [Attempted to call <func> but this function does not exist!]
```
You have not defined the function you attempted to call.

```Runtime Error! [Could not read user input! Did you enter a number?]
```
You called the read function but did not enter a numeric value.

```Runtime Error! [The function <func> expected <value> argument(s), but <value> were given!]
```
The arguments that the function expected did not match the number of arguments given when the function was called. You either called the function with too many arguments or too little.

## Repo

- `/src/`	
	SIMPLE source
- `/test/`	
	SIMPLE test code
- `README.md`	
	This readme file
- `SIMPLE.jar`	
	The SIMPLE Interpreter