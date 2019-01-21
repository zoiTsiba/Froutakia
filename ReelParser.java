
/******************************************************************************
 *  Compilation:  javac ReelParser.java
 *  Execution:    java ReelParser
 *  Dependencies: ReelExpression
 *
 *  Parses reel declaration expressions.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code ReelParser} class parses reel declaration expressions from text
 * input and provides a method that returns those expressions as an Iterable.
 * 
 * There are 2 forms of a valid reel declaration expression.
 * 
 * Form 1:
 * 
 * <b>reel <i>reelName</i>: <i>symbol1,symbol2,..,symbolN</i>;</b>
 * 
 * i.e. reel 1: A,G,F,B,C,A,E,F,G,D; *
 * 
 * There cannot be whitespace characters between a symbol name and a comma, or
 * between the reel's name and ":". But there can be one or more after the
 * "reel" keyword, after "reelName:" and before ";" .
 * 
 * 
 * Form 2:
 * 
 * <b>reel <i>reelName</i>:
 * <i>freq1*symbol1,freq2*symbol2,..,freqN*symbolN</i>;</b>
 * 
 * i.e. reel 1: 3*A,4*B,2*C,1*D;
 * 
 * There cannot be whitespace characters between a symbol name and a comma, or
 * between the reel's name and ":" or between a symbol and its frequency. But
 * there can be one or more after the "reel" keyword, after "reelName:" and
 * before ";" .
 * 
 * Valid characters for the symbols' names are: A..Z, a..z, 0..9
 * 
 * Valid characters for the reel's name are: A..Z, a..z, 0..9
 * 
 * @author AccelSprinter
 *
 */
public class ReelParser {

	// acceptable characters for a symbol name
	private static final String VALID_SYMBOL_NAME_CHARS_CLASS = "[a-zA-Z0-9]";

	// acceptable characters for a reel name
	private static final String VALID_REEL_NAME_CHARS_CLASS = "[a-zA-Z0-9]";

	// pattern #1 for the reel declaration
	private static final String REEL_PATTERN_1 = "reel\\s+(" + VALID_REEL_NAME_CHARS_CLASS + "+):\\s*("
			+ VALID_SYMBOL_NAME_CHARS_CLASS + "+(," + VALID_SYMBOL_NAME_CHARS_CLASS + "+)*)\\s*;";

	// pattern #2 for the reel declaration
	private static final String REEL_PATTERN_2 = "reel\\s+(" + VALID_REEL_NAME_CHARS_CLASS + "+):\\s*(\\d+\\*"
			+ VALID_SYMBOL_NAME_CHARS_CLASS + "+(,\\d+\\*" + VALID_SYMBOL_NAME_CHARS_CLASS + "+)*)\\s*;";

	// reel expressions list
	private final LinkedList<ReelExpression> reelExpressions;

	/**
	 * Instantiates a {@code ReelParser} class from an expression.
	 * 
	 * @param expression
	 *            the reel declaration expression
	 */
	public ReelParser(In in) {

		if (in == null)
			throw new IllegalArgumentException("argument to constructor is null");

		String allText = in.readAll();

		Pattern pattern;
		Matcher matcher;
		reelExpressions = new LinkedList<>();

		/* Search for reel pattern #1 */
		pattern = Pattern.compile(REEL_PATTERN_1);
		matcher = pattern.matcher(allText);
		while (matcher.find()) {
			ReelExpression1 re = new ReelExpression1(matcher);
			reelExpressions.add(re);
		}

		/* Search for reel pattern #2 */
		pattern = Pattern.compile(REEL_PATTERN_2);
		matcher = pattern.matcher(allText);
		while (matcher.find()) {
			ReelExpression2 re = new ReelExpression2(matcher);
			reelExpressions.add(re);
		}
	}

	/**
	 * Returns the reel expressions as an Iterable.
	 * 
	 * @return the reel expressions as an Iterable
	 */
	public Iterable<ReelExpression> expressions() {
		return reelExpressions;
	}

	// client
	public static void main(String[] args) {
		Scanner scanner;
		In in;
		String expression = "reel 1: do,re,mi,fa ;"
				+ "reel 2: 5*a,2*b,1*c,0*d,1*e,3*f,2*g,3*h,2*i ;";

		scanner = new Scanner(expression);
		in = new In(scanner);
		ReelParser rp = new ReelParser(in);

		for (ReelExpression re : rp.expressions()) {
			System.out.println(re.toString());
		}

	}

}
