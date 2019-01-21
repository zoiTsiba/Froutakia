
/******************************************************************************
 *  Compilation:  javac WindowParser.java
 *  Execution:    java WindowParser
 *  Dependencies: WindowExpression
 *
 *  Parses window declaration expressions.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code WindowParser} class parses window declaration expressions from
 * text input and provides a method that returns those expressions as an
 * Iterable.
 * 
 * The form of a valid window declaration expression is:
 * 
 * <b>window <i>R</i>x<i>L</i>;</b>
 * 
 * , where R, L are positive integers, denoting the number of reels and number
 * of lines respectively.
 * 
 * There cannot be whitespace characters between a window number and an "x", but
 * there can be one or more after the "window" keyword and before ";" .
 * 
 * @author AccelSprinter
 *
 */
public class WindowParser {

	// pattern for a window declaration expression
	private static final String WINDOW_PATTERN = "window\\s+([0-9]+)x([0-9]+)\\s*";

	// window expressions list
	private final LinkedList<WindowExpression> windowExpressions;

	/**
	 * Instantiates a {@code WindowParser} class from an expression.
	 * 
	 * @param expression
	 *            the window declaration expression
	 */
	public WindowParser(In in) {

		if (in == null)
			throw new IllegalArgumentException("argument to constructor is null");

		String allText = in.readAll();

		Pattern pattern = Pattern.compile(WINDOW_PATTERN);
		Matcher matcher = pattern.matcher(allText);
		windowExpressions = new LinkedList<>();

		while (matcher.find()) {
			WindowExpression we = new WindowExpression(matcher);
			windowExpressions.add(we);
		}
	}

	/**
	 * Returns the symbol expressions as an Iterable.
	 * 
	 * @return the symbols expressions as an Iterable
	 */
	public Iterable<WindowExpression> expressions() {
		return windowExpressions;
	}

	// client
	public static void main(String[] args) {
		String input = "window 5x3 ;" + "window    420x69;" + "windows 5x3 ;";
		;
		Scanner scanner = new Scanner(input);
		In in = new In(scanner);
		WindowParser wp = new WindowParser(in);

		for (WindowExpression we : wp.expressions())
			System.out.println(we);

	}

}
