
/******************************************************************************
 *  Compilation:  javac SymbolExpression.java
 *  Execution:    java SymbolExpression
 *  Dependencies: none
 *
 *  Stores symbols from a parsed symbol expression.
 *
 ******************************************************************************/

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 
 * The {@code SymbolExpression} class represents a list of symbols obtained from
 * a {@code Matcher} instance of a parsed valid symbol declaration expression
 * from text input.
 * 
 * This class provides a method that returns those symbols as an Iterable.
 * 
 * The form of a valid "symbol declaration" expression is:
 * 
 * <b>symbols <i>symbolName1,symbolName2,..,symbolNameN;</i></b>
 * 
 * Valid characters for the symbols' names are:
 * 
 * A-Z a-z 0-9
 * 
 * There cannot be whitespace characters between a symbol name and a comma, but
 * there can be one or more after the "symbols" keyword and before ";" .
 * 
 * @author AccelSprinter
 *
 */
public class SymbolExpression {

	// symbol list
	private final List<String> symbols;

	/**
	 * Instantiates a {@code SymbolExpression} class from a {@code Matcher}
	 * instance.
	 * 
	 * @param matcher
	 *            the {@code Matcher} instance
	 */
	public SymbolExpression(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		symbols = new LinkedList<>();

		String strSymbols = matcher.group(1);
		strSymbols = strSymbols.trim();
		String[] aSymbols = strSymbols.split(",");
		for (String strSymbol : aSymbols) {
			symbols.add(strSymbol);
		}
	}

	/**
	 * Returns the symbol as an Iterable.
	 * 
	 * @return the symbols as an Iterable
	 */
	public Iterable<String> symbols() {
		return symbols;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
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
