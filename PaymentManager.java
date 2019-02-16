import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PaymentManager {
	private final static String LEFT = "left";
	private final static String RIGHT = "right";
	private final static int WILDCARD_FACTOR = 2;

	private final SymbolTrie trieLR;
	private final SymbolTrie trieRL;
	private final List<PaymentCombinationExpression> combinations;

	public PaymentManager(Manager manager) {
		if (manager == null) {
			throw new IllegalArgumentException("Argument to constructor is null");
		}

		final Parser parser = manager.getParser();
		final PaymentParser paymentParser = parser.getPaymentParser();
		final SymbolManager sm = manager.getSymbolManager();
		trieLR = new SymbolTrie();
		trieRL = new SymbolTrie();
		combinations = new ArrayList<>();
		PaymentParserValidator.check(parser, manager);

		// add declared combinations
		for (PaymentCombinationExpression pce : paymentParser.expressions()) {
			PaymentCombinationExpression defCopy = new PaymentCombinationExpression(pce);
			combinations.add(defCopy);
			
			int[] combination = new int[pce.symbolCount()];
			int i = 0;
			for (String symbolStr : pce.symbols()) {
				int symbolNumeric = sm.getNumericSymbol(symbolStr);
				combination[i++] = symbolNumeric;
			}
			int amount = pce.amount();
			String direction = pce.direction();
			switch (direction) {
			case LEFT:
				trieLR.put(combination, amount);
				break;
			case RIGHT:
				reverse(combination);
				trieRL.put(combination, amount);
				break;
			default:
				throw new IllegalArgumentException("unknown direction token: " + direction);
			}
		}
		
		// wildcard injection 
		for (PaymentCombinationExpression pce : paymentParser.expressions()) {			
			int[] combination = new int[pce.symbolCount()];
			int i = 0;
			for (String symbolStr : pce.symbols()) {
				int symbolNumeric = sm.getNumericSymbol(symbolStr);
				combination[i++] = symbolNumeric;
			}
			int amount = pce.amount();
			String direction = pce.direction();
			switch (direction) {
			case LEFT:
				for(int wildcardNumeric : sm.getAllWildcards()) {
					for (int[] injectedCombination : injectedPermutation(combination, wildcardNumeric)) {
						trieLR.put(injectedCombination, WILDCARD_FACTOR * amount);						
					}
				}
				break;
			case RIGHT:
				for(int wildcardNumeric : sm.getAllWildcards()) {
					for (int[] injectedCombination : injectedPermutation(combination, wildcardNumeric)) {
						reverse(injectedCombination);
						trieLR.put(injectedCombination, WILDCARD_FACTOR * amount);						
					}
				}
				break;
			default:
				throw new IllegalArgumentException("unknown direction token: " + direction + " during wildcard injection");
			}
		}

	}
	
	public Iterable<PaymentCombinationExpression> combinations() {
		return combinations;
	}
	
	public int getReward(int[] combination) {
		if (combination == null) {
			throw new IllegalArgumentException("argument is null");
		}
		return getMaxReward(combination);
	}
	
	private int getMaxReward(int[] combination) {
		int max = 0;
		if (trieLR.get(combination) > max) max = trieLR.get(combination);
		if (trieRL.get(combination) > max) max = trieRL.get(combination);
		return max;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String sep = "";
		for (PaymentCombinationExpression pce : combinations) {
			sb.append(sep);
			sb.append(pce);
			sep = "\n";
		}
		return sb.toString();
	}
	
	private void reverse(int[] arr) {
		if (arr == null) {
			throw new IllegalArgumentException("argument is null");
		}
		for (int i = 0, len = arr.length / 2; i < len; ++i) {
			int swap = arr[i];
			arr[i] = arr[arr.length - i - 1];
			arr[arr.length - i - 1] = swap;
		}
	}
	
	private Iterable<int[]> injectedPermutation(int[] arr, int x) {
        Queue<int[]> queue = new LinkedList<>();
        injectedPermutation(arr, x, 0, queue);
        return queue;
    }

    private void injectedPermutation(int[] arr, int x, int d, Queue<int[]> q) {
        if (d == arr.length) {
            if (arr.length == 1) return;
            for (int i = 1, len = arr.length; i < len; ++i) {
                if (arr[i - 1] != arr[i]) {
                    q.add(arr);
                    return;
                }
            }
            return;
        }

        int[] arrCopy;

        arrCopy = Arrays.copyOf(arr, arr.length);
        injectedPermutation(arrCopy, x, d + 1, q);


        arrCopy = Arrays.copyOf(arr, arr.length);
        arrCopy[d] = x;
        injectedPermutation(arrCopy, x, d + 1, q);
    }

	private static class PaymentParserValidator {
		public static void check(Parser parser, Manager manager) {
			if (parser == null && manager == null) {
				throw new IllegalArgumentException("argument is null");
			}
			checkValidSymbols(parser, manager);
		}

		private static void checkValidSymbols(Parser parser, Manager manager) {
			final SymbolManager sm = manager.getSymbolManager();
			final PaymentParser pp = parser.getPaymentParser();

			for (PaymentCombinationExpression pce : pp.expressions()) {
				for (String symbolStr : pce.symbols()) {
					if (!sm.isSymbol(symbolStr)) {
						throw new IllegalArgumentException("symbol " + symbolStr + " has not been declared");
					}
				}
			}
		}
	}
}