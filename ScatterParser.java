
/******************************************************************************
 *  Compilation:  javac ScatterParser.java
 *  Execution:    java ScatterParser
 *  Dependencies: ScatterExpression
 *
 *  Parses scatter symbol declaration expressions.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code ScatterParser} class parses scatter symbol declaration
 * expressions from text input and provides a method that returns those
 * expressions as an Iterable.
 * 
 * The form of a valid scatter symbol declaration expression is:
 * 
 * <b>scatter <i>scatterSymbol</i>;</b>
 * 
 * Valid characters for the scatter symbol's name are:
 * 
 * A-Z a-z 0-9
 * 
 * There must be at least one whitespace characters between the "scatter" keyword
 * and the scatter symbol.
 * 
 * @author AccelSprinter
 *
 */
public class ScatterParser {

	// acceptable scatter symbol name characters class
	private static final String VALID_SYMBOL_NAME_CHARS_CLASS = "[a-zA-Z0-9]";

	// pattern for a scatter symbol declaration expression
	private static final String WILCARD_PATTERN = "scatter\\s+(" + VALID_SYMBOL_NAME_CHARS_CLASS + "+)\\s*;";

	// scatter symbol expressions list
	private final LinkedList<ScatterExpression> scatterExpressions;

	/**
	 * Instantiates a {@code ScatterParser} class from an expression.
	 * 
	 * @param expression
	 *            the scatter symbol declaration expression
	 */
	public ScatterParser(In in) {

		if (in == null)
			throw new IllegalArgumentException("argument to constructor is null");

		String allText = in.readAll();

		Pattern pattern = Pattern.compile(WILCARD_PATTERN);
		Matcher matcher = pattern.matcher(allText);
		scatterExpressions = new LinkedList<>();

		while (matcher.find()) {
			ScatterExpression we = new ScatterExpression(matcher);
			scatterExpressions.add(we);
		}
	}

	/**
	 * Returns the symbol expressions as an Iterable.
	 * 
	 * @return the symbols expressions as an Iterable
	 */
	public Iterable<ScatterExpression> expressions() {
		return scatterExpressions;
	}

	// client
	public static void main(String[] args) {
		String input = "scatter scat;";
		Scanner scanner = new Scanner(input);
		In in = new In(scanner);
		ScatterParser sp = new ScatterParser(in);

		for (ScatterExpression se : sp.expressions())
			System.out.println(se);

	}

}
