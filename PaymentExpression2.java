
/******************************************************************************
 *  Compilation:  javac PaymentExpression2.java
 *  Execution:    java PaymentExpression2
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
 * The {@code PaymentExpression2} class represents a parsed payment declaration
 * expression obtained from a {@code Matcher} instance containing a parsed valid
 * payment declaration expression from text input.
 * 
 * This class provides a method that returns the payment's combination
 * expressions as an Iterable.
 * 
 * The syntax of the valid payline declaration expression is:
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
 * The {@code Matcher} instance's groups correspond to:
 * 
 * - group(0): the whole expression - group(1):
 * <i>amount1,amount2,..,amountN</i> - group(3): <i>direction</i> - group(4):
 * <i>count1,count2,..,countN</i> - group(6): <i>symbol</i>
 * 
 * @author AccelSprinter
 *
 */
public class PaymentExpression2 implements PaymentExpression {

	// payment combination expressions
	private final List<PaymentCombinationExpression> combinations;

	/**
	 * Instantiates a {@code PaymentExpression2} class from a {@code Matcher}
	 * instance.
	 * 
	 * @param matcher
	 *            the {@code Matcher} instance
	 */
	public PaymentExpression2(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		combinations = new LinkedList<>();

		// extract amounts
		String strAmounts = matcher.group(1);
		String[] aStrAmounts = strAmounts.split(",");
		int[] aIntAmounts = new int[aStrAmounts.length];
		for (int i = 0, len = aStrAmounts.length; i < len; ++i) {
			aIntAmounts[i] = Integer.parseInt(aStrAmounts[i]);
		}
		
		// extract direction
		String strDirection = matcher.group(3);

		// extract counts
		String strCounts = matcher.group(4);
		String[] aStrCounts = strCounts.split(",");
		int[] aIntCounts = new int[aStrCounts.length];
		for (int i = 0, len = aStrCounts.length; i < len; ++i) {
			aIntCounts[i] = Integer.parseInt(aStrCounts[i]);
		}

		// extract symbol
		String strSymbol = matcher.group(6);
		
		
		if (aIntAmounts.length != aIntCounts.length) {
			throw new IllegalArgumentException("amounts don't have a 1-1 correspondance with counts");
		}
		
		for (int i = 0, len = aIntCounts.length; i < len; ++i) {
			List<String> symbolsList = new LinkedList<>();
			for (int j = 0; j < aIntCounts[i]; ++j) {
				symbolsList.add(strSymbol);
			}
			PaymentCombinationExpression pce = new PaymentCombinationExpression(aIntAmounts[i], strDirection, symbolsList);			
			combinations.add(pce);
		}
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
