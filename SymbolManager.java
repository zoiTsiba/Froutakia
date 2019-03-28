import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SymbolManager {
	private final Map<Integer, String> numericToLiteralAll;
	private final Map<String, Integer> literalToNumericAll;
	private final Map<Integer, String> numericToLiteralNormal;
	private final Map<String, Integer> literalToNumericNormal;
	private final Map<Integer, String> numericToLiteralWildcard;
	private final Map<String, Integer> literalToNumericWildcard;
	private final Map<Integer, String> numericToLiteralScatter;
	private final Map<String, Integer> literalToNumericScatter;

	public SymbolManager(Manager manager) {
		if (manager == null) {
			throw new IllegalArgumentException("argument to constructor is null");
		}

		final Parser parser = manager.getParser();
		final WildcardParser wildcardParser = parser.getWildcardParser();
		final ScatterParser scatterParser = parser.getScatterParser();
		final SymbolParser symbolParser = parser.getSymbolParser();
		int n;

		SymbolManagerValidator.check(parser);

		numericToLiteralAll = new HashMap<>();
		literalToNumericAll = new HashMap<>();
		numericToLiteralNormal = new HashMap<>();
		literalToNumericNormal = new HashMap<>();
		numericToLiteralWildcard = new HashMap<>();
		literalToNumericWildcard = new HashMap<>();
		numericToLiteralScatter = new HashMap<>();
		literalToNumericScatter = new HashMap<>();

		for (SymbolExpression se : symbolParser.expressions()) {
			n = 0;
			for (String symbol : se.symbols()) {
				numericToLiteralAll.put(n, symbol);
				literalToNumericAll.put(symbol, n);
				numericToLiteralNormal.put(n, symbol);
				literalToNumericNormal.put(symbol, n);
				n++;
			}
		}

		for (WildcardExpression we : wildcardParser.expressions()) {
			String wildcardSymbol = we.getWildcardName();
			int wildcardNumeric = literalToNumericAll.get(wildcardSymbol);
			numericToLiteralWildcard.put(wildcardNumeric, wildcardSymbol);
			literalToNumericWildcard.put(wildcardSymbol, wildcardNumeric);
		}

		for (ScatterExpression se : scatterParser.expressions()) {
			String scatterSymbol = se.getScatterName();
			int scatterNumeric = literalToNumericAll.get(scatterSymbol);
			numericToLiteralScatter.put(scatterNumeric, scatterSymbol);
			literalToNumericScatter.put(scatterSymbol, scatterNumeric);
		}
	}

	public String getLiteralSymbol(int symbolNumeric) {
		if (!numericToLiteralAll.containsKey(symbolNumeric)) {
			throw new IllegalArgumentException(symbolNumeric + " is not a declared symbol");
		}
		return numericToLiteralAll.get(symbolNumeric);
	}

	public int getNumericSymbol(String symbolLiteral) {
		if (!literalToNumericAll.containsKey(symbolLiteral)) {
			throw new IllegalArgumentException(symbolLiteral + " is not an existing numeric symbol");
		}
		return literalToNumericAll.get(symbolLiteral);
	}

	public boolean isSymbol(String symbolLiteral) {
		return literalToNumericAll.containsKey(symbolLiteral);
	}

	public boolean isSymbol(int symbolNumeric) {
		return numericToLiteralAll.containsKey(symbolNumeric);
	}

	public boolean isWildcard(String symbolLiteral) {
		return literalToNumericWildcard.containsKey(symbolLiteral);
	}

	public boolean isWildcard(int symbolNumeric) {
		return numericToLiteralWildcard.containsKey(symbolNumeric);
	}

	public boolean isScatter(String symbolLiteral) {
		return literalToNumericScatter.containsKey(symbolLiteral);
	}

	public boolean isScatter(int symbolNumeric) {
		return numericToLiteralScatter.containsKey(symbolNumeric);
	}

	public int getNumberOfAllSymbols() {
		return literalToNumericAll.size();
	}

	public Iterable<Integer> getAllWildcards() {
		return numericToLiteralWildcard.keySet();
	}

	public Iterable<Integer> getAllScatters() {
		return numericToLiteralScatter.keySet();
	}

	private static class SymbolManagerValidator {
		public static void check(Parser parser) {
			checkDuplicates(parser);
			checkWildcardExistsInSymbols(parser);
			checkScatterExistsInSymbols(parser);
			checkDuplicatesBetweenWildcardAndScatter(parser);
			checkCorrespondanceBetweenAmountsAndCountsForScatter(parser);
		}

		private static void checkDuplicates(Parser parser) {
			final SymbolParser symbolParser = parser.getSymbolParser();
			final Set<String> symbols = new HashSet<>();

			for (SymbolExpression se : symbolParser.expressions()) {
				for (String symbol : se.symbols()) {
					if (symbols.contains(symbol)) {
						throw new IllegalArgumentException("'" + symbol + "' is duplicate");
					} else {
						symbols.add(symbol);
					}
				}
			}
		}

		private static void checkWildcardExistsInSymbols(Parser parser) {
			final SymbolParser symbolParser = parser.getSymbolParser();
			final WildcardParser wildcardParser = parser.getWildcardParser();
			final Set<String> symbols = new HashSet<>();

			for (SymbolExpression se : symbolParser.expressions()) {
				for (String symbol : se.symbols()) {
					symbols.add(symbol);
				}
			}
			for (WildcardExpression we : wildcardParser.expressions()) {
				String wildcardSymbol = we.getWildcardName();
				if (!symbols.contains(wildcardSymbol)) {
					throw new IllegalArgumentException(
							"wildcard symbol '" + wildcardSymbol + "' has not been declared as symbol");
				}
			}
		}

		private static void checkScatterExistsInSymbols(Parser parser) {
			final SymbolParser symbolParser = parser.getSymbolParser();
			final ScatterParser scatterParser = parser.getScatterParser();
			final Set<String> symbols = new HashSet<>();

			for (SymbolExpression se : symbolParser.expressions()) {
				for (String symbol : se.symbols()) {
					symbols.add(symbol);
				}
			}
			for (ScatterExpression we : scatterParser.expressions()) {
				String scatterSymbol = we.getScatterName();
				if (!symbols.contains(scatterSymbol)) {
					throw new IllegalArgumentException(
							"scatter symbol '" + scatterSymbol + "' has not been declared as symbol");
				}
			}
		}

		private static void checkDuplicatesBetweenWildcardAndScatter(Parser parser) {
			final WildcardParser wildcardParser = parser.getWildcardParser();
			final ScatterParser scatterParser = parser.getScatterParser();
			final Set<String> wildcardSymbols = new HashSet<>();

			for (WildcardExpression we : wildcardParser.expressions()) {
				wildcardSymbols.add(we.getWildcardName());
			}
			for (ScatterExpression se : scatterParser.expressions()) {
				String scatterSymbol = se.getScatterName();
				if (wildcardSymbols.contains(scatterSymbol)) {
					throw new IllegalArgumentException(
							"'" + scatterSymbol + "' has been declared as both wildcard symbol and scatter symbol");
				}
			}
		}

		private static void checkCorrespondanceBetweenAmountsAndCountsForScatter(Parser parser) {

			final ScatterParser scatterParser = parser.getScatterParser();

			for (ScatterExpression se : scatterParser.expressions()) {
				int amounts = 0;
				int counts = 0;
				for (int x : se.amounts())
					amounts++;
				for (int x : se.counts())
					counts++;
				String scatterName = se.getScatterName();
				if (amounts != counts) {
					throw new IllegalArgumentException("scatter symbol: " + scatterName + " has " + amounts
							+ " amounts but " + counts + " counts");
				}
			}
		}

	}

}
