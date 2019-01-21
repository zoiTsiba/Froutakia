
/******************************************************************************
 *  Compilation:  javac SymbolParser.java
 *  Execution:    java SymbolParser
 *  Dependencies: SymbolExpression
 *
 *  Parses symbol declaration expressions.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code SymbolParser} class parses symbol declaration expressions from
 * text input and provides a method that returns those expressions as an
 * Iterable.
 * 
 * The form of a valid symbol declaration expression is:
 * 
 * <b>symbols <i>symbolName1,symbolName2,..,symbolNameN;</i></b>
 * 
 * Valid characters for the symbols' names are:
 * 
 * A-Z a-z 0-9
 * 
 * There cannot be whitespace characters between a symbol name and a comma, but
 * there can be one or more after the "symbols" keyword and before ";" .
 * 
 * @author AccelSprinter
 *
 */
public class SymbolParser {

	// acceptable symbol name characters class
	private static final String VALID_SYMBOL_NAME_CHARS_CLASS = "[a-zA-Z0-9]";

	// pattern for a symbol declaration expression
	private static final String SYMBOL_PATTERN = "symbols\\s+(" + VALID_SYMBOL_NAME_CHARS_CLASS + "+(,"
			+ VALID_SYMBOL_NAME_CHARS_CLASS + "+)*)\\s*;";

	// symbol expressions list
	private final LinkedList<SymbolExpression> symbolExpressions;

	/**
	 * Instantiates a {@code SymbolParser} class from an expression.
	 * 
	 * @param expression
	 *            the symbol declaration expression
	 */
	public SymbolParser(In in) {

		if (in == null)
			throw new IllegalArgumentException("argument to constructor is null");

		String allText = in.readAll();

		Pattern pattern = Pattern.compile(SYMBOL_PATTERN);
		Matcher matcher = pattern.matcher(allText);
		symbolExpressions = new LinkedList<>();

		while (matcher.find()) {
			SymbolExpression se = new SymbolExpression(matcher);
			symbolExpressions.add(se);
		}
	}

	/**
	 * Returns the symbol expressions as an Iterable.
	 * 
	 * @return the symbols expressions as an Iterable
	 */
	public Iterable<SymbolExpression> expressions() {
		return symbolExpressions;
	}

	// client
	public static void main(String[] args) {
		String input = "symbols a,b,c,d,e,f,g,h,i,j,k,l,m,o,n,o,p,q,r,s,t,u,v,w,x,y,z ;"
				+ "symbols 1,2,3,4,5,6,7,8,9,0;" + "symbol adsfsd,sfsd,sfdsd,fsd,234f,v6g54,5y4,3f4,f345 ;";
		;
		Scanner scanner = new Scanner(input);
		In in = new In(scanner);
		SymbolParser sp = new SymbolParser(in);

		for (SymbolExpression se : sp.expressions())
			System.out.println(se);

	}

}
