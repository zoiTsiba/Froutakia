
/******************************************************************************
 *  Compilation:  javac PaymentParser.java
 *  Execution:    java PaymentParser
 *  Dependencies: PaymentExpression, PaymentExpression1, PaymentExpression2
 *
 *  Parses payment declaration expressions.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code PaymentParser} class parses payment declaration expressions from
 * text input and provides a method that returns those expressions as an
 * Iterable.
 * 
 * There are 2 valid syntaxes of a payment declaration expression.
 * 
 * Syntax 1:
 * 
 * <b>pay <i>amount</i> <i>direction</i> <i>symbol1,symbol2,..,symbolN</i>;</b>
 * 
 * i.e. pay 1000 left Ace,King,Queen,Jack,10;
 * 
 * There must be at least one whitespace character between the words. But there
 * cannot be whitespace characters between a symbol and a comma. A whitespace is
 * optional between the semicolon and its preceding word.
 * 
 * Syntax 2:
 * 
 * <b>pay <i>amount1,amount2,..,amountN</i> <i>direction</i>
 * <i>count1,count2,..,countN</i> <i>symbol</i>;</b>
 * 
 * i.e. pay 10,15,20 left 3,4,5 Ace;
 * 
 * There must be at least one whitespace character between the words. But there
 * cannot be whitespace characters between an amount and a comma and between a
 * count and a comma. A whitespace is optional between the semicolon and its
 * preceding word. The count of amounts should be equal to the count of counts
 * to which they correspond.
 * 
 * Valid characters for the symbol's name are: A..Z, a..z, 0..9
 * 
 * @author AccelSprinter
 *
 */
public class PaymentParser {

	// acceptable characters for a payline name
	private static final String VALID_SYMBOL_CHARS_CLASS = "[a-zA-Z0-9]";

	// directions
	private static final String DIRECTIONS = "(left|right)";

	// pattern #1 for the payment declaration
	private static final String PAYMENT_PATTERN_1 = "pay\\s+(\\d+)\\s+" + DIRECTIONS + "\\s+("
			+ VALID_SYMBOL_CHARS_CLASS + "+(," + VALID_SYMBOL_CHARS_CLASS + "+)*)\\s*;";

	// pattern #2 for the payment declaration
	private static final String PAYMENT_PATTERN_2 = "pay\\s+(\\d+(,\\d+)*)\\s+" + DIRECTIONS + "\\s+(\\d+(,\\d+)*)\\s+("
			+ VALID_SYMBOL_CHARS_CLASS + "+)\\s*;";

	// symbol expressions list
	private final LinkedList<PaymentCombinationExpression> paymentCombinationExpressions;

	/**
	 * Instantiates a {@code PaymentParser} class from a text input.
	 * 
	 * @param text
	 *            the text input
	 */
	public PaymentParser(String text) {

		if (text == null)
			throw new IllegalArgumentException("argument to constructor is null");

		Pattern pattern;
		Matcher matcher;
		paymentCombinationExpressions = new LinkedList<>();

		/* Search for payment pattern #1 */
		pattern = Pattern.compile(PAYMENT_PATTERN_1);
		matcher = pattern.matcher(text);
		while (matcher.find()) {
			PaymentExpression1 pe = new PaymentExpression1(matcher);
			for (PaymentCombinationExpression pce : pe.combinations()) {
				paymentCombinationExpressions.add(pce);
			}
		}

		/* Search for payline pattern #2 */
		pattern = Pattern.compile(PAYMENT_PATTERN_2);
		matcher = pattern.matcher(text);
		while (matcher.find()) {
			PaymentExpression2 pe = new PaymentExpression2(matcher);
			for (PaymentCombinationExpression pce : pe.combinations()) {
				paymentCombinationExpressions.add(pce);
			}
		}
	}

	/**
	 * Returns the payline expressions as an Iterable.
	 * 
	 * @return the payline expressions as an Iterable
	 */
	public Iterable<PaymentCombinationExpression> expressions() {
		return paymentCombinationExpressions;
	}

	// client
	public static void main(String[] args) {
		Scanner scanner;
		In in;
		String expression = "pay 1000 left A,K,Q,J,10;" + "pay 200,250,300 left 3,4,5 A;";

		scanner = new Scanner(expression);
		in = new In(scanner);
		PaymentParser pp = new PaymentParser(in.readAll());

		for (PaymentCombinationExpression pce : pp.expressions()) {
			System.out.println(pce);
		}
	}

}
