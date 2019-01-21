
/******************************************************************************
 *  Compilation:  javac ReelExpression2.java
 *  Execution:    java ReelExpression2
 *  Dependencies: none
 *
 *  Stores a parsed reel expression.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 
 * The {@code ReelExpression2} class represents a parsed reel declaration
 * expression obtained from a a {@code Matcher} instance containing a parsed
 * valid symbol declaration expression from text input.
 * 
 * This class provides a method for obtaining the reels name and a method that
 * returns the reel's symbols as an Iterable.
 * 
 * The form of a valid reel declaration expression is:
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
 * @author AccelSprinter
 *
 */
public class ReelExpression2 implements ReelExpression {

	// reel name
	private final String reelName;
	// symbol list
	private final List<String> symbols;

	/**
	 * Instantiates a {@code ReelExpression2} class from a {@code Matcher} instance.
	 * 
	 * @param matcher
	 *            the {@code Matcher} instance
	 */
	public ReelExpression2(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		reelName = matcher.group(1);
		symbols = new LinkedList<>();

		String strPackedSymbols = matcher.group(2);
		strPackedSymbols = strPackedSymbols.trim();
		String[] aPackedSymbols = strPackedSymbols.split(",");

		for (String strPackedSymbol : aPackedSymbols) {
			String[] aPackedSymbol = strPackedSymbol.split("\\*");
			int freq = Integer.parseInt(aPackedSymbol[0]);
			String strSymbol = aPackedSymbol[1];

			for (int i = 0; i < freq; ++i)
				symbols.add(strSymbol);
		}
	}

	/**
	 * 
	 */
	public String getReelName() {
		return reelName;
	}

	/**
	 * Returns the reel's symbols as an Iterable.
	 * 
	 * @return the reel's symbols as an Iterable
	 */
	public Iterable<String> symbols() {
		return symbols;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(reelName);
		sb.append(" : ");
		for (String symbol : symbols) {
			sb.append(symbol);
			sb.append(" ");
		}
		return sb.toString();
	}

	// client
	public static void main(String[] args) {

	}
}
