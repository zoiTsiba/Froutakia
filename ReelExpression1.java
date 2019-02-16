
/******************************************************************************
 *  Compilation:  javac ReelExpression1.java
 *  Execution:    java ReelExpression1
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
 * The {@code ReelExpression1} class represents a parsed reel declaration
 * expression obtained from a a {@code Matcher} instance containing a parsed
 * valid symbol declaration expression from text input.
 * 
 * This class provides a method for obtaining the reels name and a method that
 * returns the reel's symbols as an Iterable.
 * 
 * The form of a valid reel declaration expression is:
 * 
 * <b>reel <i>reelName</i>: <i>symbol1,symbol2,..,symbolN</i>;</b>
 * 
 * i.e. reel 1: A,G,F,B,C,A,E,F,G,D; *
 * 
 * There cannot be whitespace characters between a symbol name and a comma, or
 * between the reel's name and ":". But there can be one or more after the
 * "reel" keyword, after "reelName:" and before ";" .
 * 
 * @author AccelSprinter
 *
 */
public class ReelExpression1 implements ReelExpression {

	// reel name
	private final String reelName;
	// symbol list
	private final List<String> symbols;
	private final int symbolCount;

	/**
	 * Instantiates a {@code ReelExpression1} class from a {@code Matcher} instance.
	 * 
	 * @param matcher
	 *            the {@code Matcher} instance
	 */
	public ReelExpression1(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		reelName = matcher.group(1);
		symbols = new LinkedList<>();
		String strSymbols = matcher.group(2);
		strSymbols = strSymbols.trim();
		String[] aSymbols = strSymbols.split(",");
		int auxSymbolCount = 0;
		for (String strSymbol : aSymbols) {
			symbols.add(strSymbol);
			auxSymbolCount++;
		}
		this.symbolCount = auxSymbolCount;
	}

	/**
	 * 
	 */
	public String getReelName() {
		return reelName;
	}

	/**
	 * Returns the reel expression's symbols as an Iterable.
	 * 
	 * @return the reel expression's symbols as an Iterable
	 */
	public Iterable<String> symbols() {
		return symbols;
	}

	/**
	 * Return the reel expression's symbol count.
	 * 
	 * @return the reel expression's symbol count
	 */
	public int symbolCount() {
		return symbolCount;
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
