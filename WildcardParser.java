
/******************************************************************************
 *  Compilation:  javac WildcardParser.java
 *  Execution:    java WildcardParser
 *  Dependencies: WildcardExpression
 *
 *  Parses wildcard symbol declaration expressions.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code WildcardParser} class parses wildcard symbol declaration
 * expressions from text input and provides a method that returns those
 * expressions as an Iterable.
 * 
 * The form of a valid wildcard symbol declaration expression is:
 * 
 * <b>wild <i>wildcardSymbol</i>;</b>
 * 
 * Valid characters for the wildcard symbol's name are:
 * 
 * A-Z a-z 0-9
 * 
 * There must be at least one whitespace characters between the "wild" keyword
 * and the wildcard symbol.
 * 
 * @author AccelSprinter
 *
 */
public class WildcardParser {

	// acceptable wildcard symbol name characters class
	private static final String VALID_SYMBOL_NAME_CHARS_CLASS = "[a-zA-Z0-9]";

	// pattern for a wildcard symbol declaration expression
	private static final String WILCARD_PATTERN = "wild\\s+(" + VALID_SYMBOL_NAME_CHARS_CLASS + "+)\\s*;";

	// wildcard symbol expressions list
	private final LinkedList<WildcardExpression> wildcardExpressions;

	/**
	 * Instantiates a {@code WildcardParser} class from a text input.
	 * 
	 * @param text
	 *            the text input
	 */
	public WildcardParser(String text) {

		if (text == null)
			throw new IllegalArgumentException("argument to constructor is null");

		Pattern pattern = Pattern.compile(WILCARD_PATTERN);
		Matcher matcher = pattern.matcher(text);
		wildcardExpressions = new LinkedList<>();

		while (matcher.find()) {
			WildcardExpression we = new WildcardExpression(matcher);
			wildcardExpressions.add(we);
		}
	}

	/**
	 * Returns the symbol expressions as an Iterable.
	 * 
	 * @return the symbols expressions as an Iterable
	 */
	public Iterable<WildcardExpression> expressions() {
		return wildcardExpressions;
	}

	// client
	public static void main(String[] args) {
		String input = "wild joker;";
		Scanner scanner = new Scanner(input);
		In in = new In(scanner);
		WildcardParser wp = new WildcardParser(in.readAll());

		for (WildcardExpression we : wp.expressions())
			System.out.println(we);

	}

}
