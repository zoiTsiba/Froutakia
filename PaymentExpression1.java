
/******************************************************************************
 *  Compilation:  javac PaymentExpression1.java
 *  Execution:    java PaymentExpression1
 *  Dependencies: none
 *
 *  Stores a parsed payment expression.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 
 * The {@code PaymentExpression1} class represents a parsed payment declaration
 * expression obtained from a {@code Matcher} instance containing a parsed valid
 * payment declaration expression from text input.
 * 
 * This class provides a method that returns the payment's combination
 * expressions as an Iterable.
 * 
 * The syntax of the valid payline declaration expression is:
 * 
 * <b>pay <i>amount</i> <i>direction</i> <i>symbol1,symbol2,..,symbolN</i>;</b>
 * 
 * i.e. pay 1000 left Ace,King,Queen,Jack,10;
 * 
 * There must be at least one whitespace character between the words. But there
 * cannot be whitespace characters between a symbol and a comma. A whitespace is
 * optional between the semicolon and its preceding word.
 * 
 * The {@code Matcher} instance's groups correspond to: - group(0): the whole
 * expression - group(1): <i>amount</i> - group(2): <i>direction</i> - group(3):
 * <i>symbol1,symbol2,..,symbolN</i>
 * 
 * @author AccelSprinter
 *
 */
public class PaymentExpression1 implements PaymentExpression {

	// payment combination expressions
	private final List<PaymentCombinationExpression> combinations;

	/**
	 * Instantiates a {@code PaymentExpression1} class from a {@code Matcher}
	 * instance.
	 * 
	 * @param matcher
	 *            the {@code Matcher} instance
	 */
	public PaymentExpression1(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		combinations = new LinkedList<>();
		
		// extract amount
		String strAmount = matcher.group(1);
		int intAmount = Integer.parseInt(strAmount);
		
		// extract direction
		String strDirection = matcher.group(2);
		
		//extract symbol combination
		List<String> symbolsList = new LinkedList<>();
		String strSymbols = matcher.group(3);
		String[] aSymbols = strSymbols.split(",");
		for (String strSymbol : aSymbols) {
			symbolsList.add(strSymbol);
		}
		PaymentCombinationExpression pce = new PaymentCombinationExpression(intAmount, strDirection, symbolsList);
		combinations.add(pce);
	}

	/**
	 * Returns the payment's combination expressions as an Iterable.
	 * 
	 * @return the payment's combination expressions as an Iterable
	 */
	public Iterable<PaymentCombinationExpression> combinations() {
		return combinations;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String separator = "";
		for (PaymentCombinationExpression pce : combinations) {
			sb.append(separator);
			sb.append(pce);
			separator = "\n";
		}
		return sb.toString();
	}

	// client
	public static void main(String[] args) {

	}
}
