
/******************************************************************************
 *  Compilation:  javac WildcardExpression.java
 *  Execution:    java WildcardExpression
 *  Dependencies: none
 *
 *  Stores a parsed wildcard symbol expression.
 *
 ******************************************************************************/

import java.util.regex.Matcher;

/**
 * 
 * The {@code WildcardExpression} class represents a parsed valid wildcard symbol
 * declaration expression obtained from a {@code Matcher} instance.
 * 
 * This class provides methods for obtaining the name of the wildcard symbol.
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
 * The {@code Matcher} instance's groups correspond to: 
 * 
 * - group(0): the whole expression
 * - group(1): the wildcard symbol's name
 * 
 * @author AccelSprinter
 *
 */
public class WildcardExpression {

	private final String wildcardName;

	/**
	 * Instantiates a {@code WildcardExpression} class from a {@code Matcher}
	 * instance.
	 * 
	 * @param matcher
	 *            the {@code Matcher} instance
	 */
	public WildcardExpression(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		wildcardName = matcher.group(1);
	}

	/**
	 * Returns the number of reels.
	 * 
	 * @return the number of reels
	 */
	public String getWildcardName() {
		return wildcardName;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(wildcardName);
		return sb.toString();
	}

	// client
	public static void main(String[] args) {

	}
}
