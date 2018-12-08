import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code SymbolParser} class parses a complete "symbol declaration"
 * expression and provides a method that returns the symbols as an Iterable.
 * 
 * The form of a valid "symbol declaration" expression is:
 * 
 * symbols symbolName1,symbolName2,..,symbolNameN;
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
public class SymbolParser {

	private static final String VALID_SYMBOL_NAME_CHARS_CLASS = "[a-zA-Z0-9]"; // acceptable characters for a symbol
																				// name
	private static final String SYMBOL_PATTERN = "symbols\\s+(" + VALID_SYMBOL_NAME_CHARS_CLASS + "+(,"
			+ VALID_SYMBOL_NAME_CHARS_CLASS + "+)*)\\s*;"; // pattern for the symbol declaration expression

	private final LinkedList<String> symbolsList; // symbol list

	/**
	 * Instantiates a {@code SymbolParser} class from an expression.
	 * 
	 * @param expression
	 *            the symbol declaration expression
	 */
	public SymbolParser(String expression) {

		if (expression == null)
			throw new IllegalArgumentException("argument to constructor is null");

		Pattern pattern = Pattern.compile(SYMBOL_PATTERN);
		Matcher matcher = pattern.matcher(expression);

		if (!matcher.matches())
			throw new IllegalArgumentException(expression + " is not a valid symbol expression");

		String strSymbols = matcher.group(1);
		strSymbols = strSymbols.trim();
		String[] aSymbols = strSymbols.split(",");

		symbolsList = new LinkedList<>();
		for (String strSymbol : aSymbols) {
			symbolsList.add(strSymbol);
		}
	}

	/**
	 * Returns the symbols as an Iterable.
	 * 
	 * @return the symbols as an Iterable
	 */
	public Iterable<String> symbols() {
		return symbolsList;
	}

	// client
	public static void main(String[] args) {
		String expression = "symbols adsfsd,sfsd,sfdsd,fsd,234f,v6g54,5y4,3f4,f345 ;";
		SymbolParser sm = new SymbolParser(expression);

		for (String s : sm.symbols())
			System.out.println(s);

	}

}
