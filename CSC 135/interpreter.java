//Implementation for Hw5Interpreter
//Justin Voo (6767)

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Hw5Interpreter {
	private static LexerSingleCharTokens lex;
	private static byte[] data = new byte[30000];
    private static int didx = 0;
	
	/*
	 * 
	 */
	private static ParseTreeNode parseS() {
		// "!lex.hasNext()" means $ (eof) is reached. Do whatever is appropriate
        if (!lex.hasNext()) {
        	ParseTreeNode child = new ParseTreeNode("", null, null, null);
        	return new ParseTreeNode("S", child, null, null);
        }
        switch (lex.next()) {
	        case ">":
	        case "<":
	        case "+":
	        case "-":
	        case ",":
	        case ".":
	        	return new ParseTreeNode("S", parseC(), parseS(), null);
	        case "[":
	        	return new ParseTreeNode("S", parseL(), parseS(), null);
	        case "]":
	        	ParseTreeNode child = new ParseTreeNode("", null, null, null);
	        	return new ParseTreeNode("S", child, null, null);
            default:
                throw new IllegalStateException("Error");
        }
	}
	
	/*
	 * 
	 */
	private static ParseTreeNode parseL() {
		// "!lex.hasNext()" means $ (eof) is reached. Do whatever is appropriate
        if (!lex.hasNext())
            throw new IllegalStateException("Error");
        switch (lex.next()) {
            case "[":
            	lex.match("[");
            	ParseTreeNode child = parseS();
            	if (lex.match("]"))
            		return new ParseTreeNode("L",
            				new ParseTreeNode("[", null, null, null),
            				child,
            				new ParseTreeNode("]", null, null, null));
            default:
                throw new IllegalStateException("Error");
        }
	}
	
	/*
	 * 
	 */
	private static ParseTreeNode parseC() {
		// "!lex.hasNext()" means $ (eof) is reached. Do whatever is appropriate
        if (!lex.hasNext())
            throw new IllegalStateException("Error");
        ParseTreeNode child;
        switch (lex.next()) {
        case ">":
        	lex.match(">");
        	child = new ParseTreeNode(">", null, null, null);
        	return new ParseTreeNode("C", child, null, null);
        case "<":
        	lex.match("<");
        	child = new ParseTreeNode("<", null, null, null);
        	return new ParseTreeNode("C", child, null, null);
        case "+":
        	lex.match("+");
        	child = new ParseTreeNode("+", null, null, null);
        	return new ParseTreeNode("C", child, null, null);
        case "-":
        	lex.match("-");
        	child = new ParseTreeNode("-", null, null, null);
        	return new ParseTreeNode("C", child, null, null);
        case ",":
        	lex.match(",");
        	child = new ParseTreeNode(",", null, null, null);
        	return new ParseTreeNode("C", child, null, null);
        case ".":
        	lex.match(".");
        	child = new ParseTreeNode(".", null, null, null);
            return new ParseTreeNode("C", child, null, null);
        default:
            throw new IllegalStateException("Error");
        }
	}

	/*
	 * 
	 */
	public static void main(String[] args) throws IOException {
		String program = "";
    
        try {
        	Scanner file = new Scanner(new File("bf.txt"));
            while (file.hasNextLine())
                program = program + file.nextLine();
            program = program.replaceAll("[^-+<>,\\.\\[\\]]","");
            file.close();
        }
        catch (Exception e) {
            System.out.println("Could not read bf.txt");
            return;
        }
        
        lex = new LexerSingleCharTokens(program);
        ParseTreeNode tree = parseS();
        
        if (tree != null && !lex.hasNext())
        	walkTree(tree);
        else
            throw new IllegalStateException("Error");
    }
	
	/*
	 * 
	 */
	public static void walkTree(ParseTreeNode t) throws IOException {
		
		//Convert into a tree walking algorithm
        if (t.type != "") {
        	switch(t.type) {
	        	case "S":
	        		walkTree(t.firstChild);
	        		if (t.secondChild != null)
	        			walkTree(t.secondChild);
	        		break;
	        	case "L":
	        		while (data[didx] != 0)
	        			walkTree(t.secondChild);
	        		break;
	        	case "C":
	        		walkTree(t.firstChild);
	        		break;
	        	case ">":
	        		didx++;
	        		break;
	        	case "<":
	        		didx--;
	        		break;
	        	case "+":
	        		data[didx]++;
	        		break;
	        	case "-":
	        		data[didx]--;
	        		break;
	        	case ",":
	        		try {
	        			data[didx] = (byte)System.in.read();
	        		}
	        		catch (IOException e) {
	                    throw new IllegalStateException("Error");
	        		}
	        		break;
	        	case ".":
	        		System.out.print((char)data[didx]); //Currently prints empty spaces
	        		break;
	        	default:
	                throw new IllegalStateException("Error");
        	}
        }
    }
}