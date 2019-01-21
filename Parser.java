
public class Parser {

	private final SymbolParser symbolParser;
	private final WindowParser windowParser;
	private final ReelParser reelParser;
	private final PaylineParser paylineParser;
	private final PaymentParser paymentParser;
	private final WildcardParser wildcardParser;
	private final ScatterParser scatterParser;

	public Parser(In in) {
		if (in == null)
			throw new IllegalArgumentException("first argument to constructor is null");

		symbolParser = new SymbolParser(in);
		windowParser = new WindowParser(in);
		reelParser   = new ReelParser(in);
		paylineParser = new PaylineParser(in);
		paymentParser = new PaymentParser(in);
		wildcardParser = new WildcardParser(in);
		scatterParser = new ScatterParser(in);
	}

	/**
	 * Returns the symbol parser.
	 * 
	 * @return the symbol parser
	 */
	public SymbolParser getSymbolParser() {
		return symbolParser;
	}
	
	/**
	 * Returns the window parser.
	 * 
	 * @return the window parser
	 */
	public WindowParser getWindowParser() {
		return windowParser;
	}
	
	/**
	 * Returns the reel parser.
	 * 
	 * @return the reel parser
	 */
	public ReelParser getReelParser() {
		return reelParser;
	}
	
	/**
	 * Returns the payline parser.
	 * 
	 * @return the payline parser
	 */
	public PaylineParser getPaylineParser() {
		return paylineParser;
	}
	
	/**
	 * Returns the payment parser.
	 * 
	 * @return the payment parser
	 */
	public PaymentParser getPaymentParser() {
		return paymentParser;
	}
	
	/**
	 * Returns the wildcard parser.
	 * 
	 * @return the wildcard parser
	 */
	public WildcardParser getWildcardParser() {
		return wildcardParser;
	}
	
	/**
	 * Returns the scatter parser.
	 * 
	 * @return the scatter parser
	 */
	public ScatterParser getScatterParser() {
		return scatterParser;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
