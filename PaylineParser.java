
/******************************************************************************
 *  Compilation:  javac PaylineParser.java
 *  Execution:    java PaylineParser
 *  Dependencies: PaylineExpression, PaylineExpression1, PaylineExpression2
 *
 *  Parses payline declaration expressions.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code PaylineParser} class parses payline declaration expressions from
 * text input and provides a method that returns those expressions as an
 * Iterable.
 * 
 * There are 2 valid syntaxes of a payline declaration expression.
 * 
 * Syntax 1:
 * 
 * <b>payline <i>paylineName</i>:
 * <i>lineOfReel1,lineOfReel2,..,lineOfReelN</i>;</b>
 * 
 * i.e. payline payline1: 2,2,2,2,2;
 * 
 * There cannot be whitespace characters between a digit and a comma, or between
 * the payline's name and ":". But there can be one or more after the "payline"
 * keyword, after "paylineName:" and before ";" .
 * 
 * 
 * Syntax 2:
 * 
 * <b>payline <i>paylineName</i>:
 * <i>(line1,reel1),(line2,reel2),..,(lineN,reelN)</i>;</b>
 * 
 * i.e. payline payline1: (1,1),(2,1),(3,1);
 * 
 * There cannot be whitespace characters between a digit and a comma, or between
 * a digit and a parenthesis, or between a comma and a parenthesis or between
 * the payline's name and ":". But there can be one or more after the "payline"
 * keyword, after "paylineName:" and before ";" .
 * 
 * Valid characters for the payline's name are: A..Z, a..z, 0..9
 * 
 * @author AccelSprinter
 *
 */
public class PaylineParser {

	// acceptable characters for a payline name
	private static final String VALID_PAYLINE_NAME_CHARS_CLASS = "[a-zA-Z0-9]";

	// pattern #1 for the payline declaration
	private static final String PAYLINE_PATTERN_1 = "payline\\s+(" + VALID_PAYLINE_NAME_CHARS_CLASS
			+ "+):\\s+(\\d+(,\\d+)*)\\s*;";

	// pattern #2 for the payline declaration
	private static final String PAYLINE_PATTERN_2 = "payline\\s+(" + VALID_PAYLINE_NAME_CHARS_CLASS
			+ "+):\\s+(\\(\\d+,\\d+\\)(,\\(\\d+,\\d+\\))*)\\s*;";

	// payline expressions list
	private final LinkedList<PaylineExpression> paylineExpressions;

	/**
	 * Instantiates a {@code PaylineParser} class from an {@code In} instance.
	 * 
	 * @param in
	 *            the in instance
	 */
	public PaylineParser(In in) {

		if (in == null)
			throw new IllegalArgumentException("argument to constructor is null");

		String allText = in.readAll();

		Pattern pattern;
		Matcher matcher;
		paylineExpressions = new LinkedList<>();

		/* Search for payline pattern #1 */
		pattern = Pattern.compile(PAYLINE_PATTERN_1);
		matcher = pattern.matcher(allText);
		while (matcher.find()) {
			PaylineExpression1 pe = new PaylineExpression1(matcher);
			paylineExpressions.add(pe);
		}

		/* Search for payline pattern #2 */
		pattern = Pattern.compile(PAYLINE_PATTERN_2);
		matcher = pattern.matcher(allText);
		while (matcher.find()) {
			PaylineExpression2 pe = new PaylineExpression2(matcher);
			paylineExpressions.add(pe);
		}
	}

	/**
	 * Returns the payline expressions as an Iterable.
	 * 
	 * @return the payline expressions as an Iterable
	 */
	public Iterable<PaylineExpression> expressions() {
		return paylineExpressions;
	}

	// client
	public static void main(String[] args) {
		Scanner scanner;
		In in;
		String expression = "payline a: 2,2,2,2,2;"
		 + "payline b: (1,1),(2,1),(3,1),(2,2),(1,3) ;";

		scanner = new Scanner(expression);
		in = new In(scanner);
		PaylineParser pp = new PaylineParser(in);

		for (PaylineExpression pe : pp.expressions()) {
			System.out.println(pe.toString());
		}

	}

}
