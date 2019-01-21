
/******************************************************************************
 *  Compilation:  javac WindowExpression.java
 *  Execution:    java WindowExpression
 *  Dependencies: none
 *
 *  Stores a parsed window expression.
 *
 ******************************************************************************/

import java.util.regex.Matcher;

/**
 * 
 * The {@code WindowExpression} class represents a parsed valid window
 * declaration expression obtained from a {@code Matcher} instance.
 * 
 * This class provides methods for obtaining the number of reels and the number
 * of lines.
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
public class WindowExpression {

	private final int numberOfReels; // number of reels
	private final int numberOfLines; // number of lines

	/**
	 * Instantiates a {@code WindowExpression} class from a {@code Matcher}
	 * instance.
	 * 
	 * @param matcher
	 *            the {@code Matcher} instance
	 */
	public WindowExpression(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		String strNumberOfReels = matcher.group(1);
		numberOfReels = Integer.parseInt(strNumberOfReels);
		String strNumberOfLines = matcher.group(2);
		numberOfLines = Integer.parseInt(strNumberOfLines);
	}

	/**
	 * Returns the number of reels.
	 * 
	 * @return the number of reels
	 */
	public int getNumberOfReels() {
		return numberOfReels;
	}

	/**
	 * Returns the number of lines.
	 * 
	 * @return the number of lines
	 */
	public int strNumberOfLines() {
		return numberOfLines;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(numberOfReels);
		sb.append(",");
		sb.append(numberOfLines);
		return sb.toString();
	}

	// client
	public static void main(String[] args) {

	}
}
