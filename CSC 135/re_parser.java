import java.util.Scanner;

public class Hw4Parser {
	private static LexerSingleCharTokens lex;
	
	private static boolean parseE() {
		if (!lex.hasNext())
			return false;
		switch (lex.next()) {
			case "(":
				return parseT() && parseEp();
			case "a":
				return parseT() && parseEp();
			default:
				return false;
		}
	}
	
	private static boolean parseEp() {
		if (!lex.hasNext())
			return true;
		switch (lex.next()) {
			case "+":
				return lex.match("+") && parseT() && parseEp();
			case ")":
				return true;
			default:
				return false;
		}
	}
	
	private static boolean parseT() {
		if (!lex.hasNext())
			return false;
		switch (lex.next()) {
			case "(":
				return parseF() && parseTp();
			case "a":
				return parseF() && parseTp();
			default:
				return false;
		}
	}
	
	private static boolean parseTp() {
		if (!lex.hasNext())
			return true;
		switch (lex.next()) {
			case "*":
				return lex.match("*") && parseF() && parseTp();
			case "+":
				return true;
			case ")":
				return true;
			default:
				return false;
		}
	}
	
	private static boolean parseF() {
		if (!lex.hasNext())
			return false;
		switch (lex.next()) {
			case "(":
				return lex.match("(") && parseE() && lex.match(")");
			case "a":
				return lex.match("a");
			default:
				return false;
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		lex = new LexerSingleCharTokens(in.nextLine());
		
		if (parseE() && !lex.hasNext())
			System.out.println("accept");
		else
			System.out.println("reject");
		in.close();
	}
}