
/******************************************************************************
 *  Compilation:  javac ScatterExpression.java
 *  Execution:    java ScatterExpression
 *  Dependencies: none
 *
 *  Stores a parsed scatter symbol expression.
 *
 ******************************************************************************/

import java.util.regex.Matcher;

/**
 * 
 * The {@code ScatterExpression} class represents a parsed valid scatter symbol
 * declaration expression obtained from a {@code Matcher} instance.
 * 
 * This class provides methods for obtaining the name of the scatter symbol.
 * 
 * The form of a valid scatter symbol declaration expression is:
 * 
 * <b>wild <i>scatterSymbol</i>;</b>
 * 
 * Valid characters for the scatter symbol's name are:
 * 
 * A-Z a-z 0-9
 * 
 * There must be at least one whitespace characters between the "wild" keyword
 * and the scatter symbol.
 * 
 * The {@code Matcher} instance's groups correspond to: 
 * 
 * - group(0): the whole expression
 * - group(1): the scatter symbol's name
 * 
 * @author AccelSprinter
 *
 */
public class ScatterExpression {

	private final String scatterName;

	/**
	 * Instantiates a {@code ScatterExpression} class from a {@code Matcher}
	 * instance.
	 * 
	 * @param matcher
	 *            the {@code Matcher} instance
	 */
	public ScatterExpression(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		scatterName = matcher.group(1);
	}

	/**
	 * Returns the number of reels.
	 * 
	 * @return the number of reels
	 */
	public String getScatterName() {
		return scatterName;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(scatterName);
		return sb.toString();
	}

	// client
	public static void main(String[] args) {

	}
}
