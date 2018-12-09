import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code ReelParser} class parses a valid "reel declaration" expression and
 * provides a method that returns the symbols of this reel as an Iterable and a
 * method that returns this reel's name.
 * 
 * There are 2 forms of a valid "reel declaration" expression.
 * 
 * Form 1:
 * 
 * reel reelName: symbol1,symbol2,..,symbolN;
 * 
 * i.e. reel 1: A,G,F,B,C,A,E,F,G,D; *
 * 
 * There cannot be whitespace characters between a symbol name and a comma, or
 * between the reel's name and ":". But there can be one or more after the
 * "reel" keyword, after "reelName:" and before ";" .
 * 
 * 
 * Form 2:
 * 
 * reel reelName: freq1*symbol1,freq2*symbol2,..,freqN*symbolN;
 * 
 * i.e. reel 1: 3*A,4*B,2*C,1*D;
 * 
 * There cannot be whitespace characters between a symbol name and a comma, or
 * between the reel's name and ":" or between a symbol and its frequency. But
 * there can be one or more after the "reel" keyword, after "reelName:" and
 * before ";" .
 * 
 * Valid characters for the symbols' names are: A..Z, a..z, 0..9
 * 
 * Valid characters for the reel's name are: A..Z, a..z, 0..9
 * 
 * @author AccelSprinter
 *
 */
public class ReelParser {

	private static final String VALID_SYMBOL_NAME_CHARS_CLASS = "[a-zA-Z0-9]"; // acceptable characters for a symbol
																				// name
	private static final String VALID_REEL_NAME_CHARS_CLASS = "[a-zA-Z0-9]"; // acceptable characters for a reel name

	private static final String REEL_PATTERN_1 = "reel\\s+(" + VALID_REEL_NAME_CHARS_CLASS + "+):\\s*\\s*("
			+ VALID_SYMBOL_NAME_CHARS_CLASS + "+(," + VALID_SYMBOL_NAME_CHARS_CLASS + "+)*)\\s*\\s*;"; // pattern #1 for
																										// the reel
																										// declaration
																										// expression
	private static final String REEL_PATTERN_2 = "reel\\s+(" + VALID_REEL_NAME_CHARS_CLASS + "+):\\s*\\s*(\\d+\\*"
			+ VALID_SYMBOL_NAME_CHARS_CLASS + "+(,\\d+\\*" + VALID_SYMBOL_NAME_CHARS_CLASS + "+)*)\\s*\\s*;"; // pattern
																												// #2
																												// for
																												// the
																												// reel
																												// declaration
	// expression
	private final String reelName;
	private final LinkedList<String> symbolsList; // symbol list

	/**
	 * Instantiates a {@code ReelParser} class from a reel declaration expression.
	 * 
	 * @param expression
	 *            the reel declaration expression
	 */
	public ReelParser(String expression) {

		if (expression == null)
			throw new IllegalArgumentException("argument to constructor is null");

		symbolsList = new LinkedList<>();

		Pattern pattern1 = Pattern.compile(REEL_PATTERN_1);
		Pattern pattern2 = Pattern.compile(REEL_PATTERN_2);
		Matcher matcher1 = pattern1.matcher(expression);
		Matcher matcher2 = pattern2.matcher(expression);

		if (!matcher1.matches() && !matcher2.matches())
			throw new IllegalArgumentException(expression + " is not a valid reel declaration expression");

		reelName = matcher1.matches() ? matcher1.group(1) : matcher2.group(1);

		if (matcher1.matches()) { // pattern #1
			String strSymbols = matcher1.group(2);
			strSymbols = strSymbols.trim();
			String[] aSymbols = strSymbols.split(",");

			for (String strSymbol : aSymbols) {
				symbolsList.add(strSymbol);
			}
		} else if (matcher2.matches()) { // pattern #2
			String strPackedSymbols = matcher2.group(2);
			strPackedSymbols = strPackedSymbols.trim();
			String[] aPackedSymbols = strPackedSymbols.split(",");

			for (String strPackedSymbol : aPackedSymbols) {
				String[] aPackedSymbol = strPackedSymbol.split("\\*");
				int freq = Integer.parseInt(aPackedSymbol[0]);
				String strSymbol = aPackedSymbol[1];

				for (int i = 0; i < freq; ++i)
					symbolsList.add(strSymbol);
			}
		} else {
			throw new IllegalArgumentException("No valid matcher. You should not be here.");
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

	/**
	 * Returns this reel's name.
	 * 
	 * @return this reel's name
	 */
	public String reelName() {
		return reelName;
	}

	// debugging client
	public static void main(String[] args) {
		String expression1 = "reel 1: adsfsd,sfsd,sfdsd,fsd,234f,v6g54,5y4,3f4,f345 ;";
		ReelParser rp1 = new ReelParser(expression1);

		System.out.println("expression: " + expression1);
		System.out.println("reel name: " + rp1.reelName());
		System.out.print("symbols: ");
		for (String s : rp1.symbols()) {
			System.out.print(s + " ");
		}
		System.out.println("\n\n");

		String expression2 = "reel 2: 5*adsfsd,2*sfsd,1*sfdsd,0*fsd,1*234f,3*v6g54,2*5y4,3*3f4,2*f345 ;";
		ReelParser rp2 = new ReelParser(expression2);

		System.out.println("expression: " + expression2);
		System.out.println("reel name: " + rp2.reelName());
		System.out.print("symbols: ");
		for (String s : rp2.symbols()) {
			System.out.print(s + " ");
		}
		System.out.println("\n\n");

	}

}
