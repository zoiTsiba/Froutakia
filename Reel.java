import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reel {

	private final List<Integer> symbols;
	private final Map<Integer, Integer> symbolToFrequency;
	private final int numberOfSymbols;
	private final int numberOfDistinctSymbols;

	public Reel(int[] symbolsOrFrequencies, boolean symbolsUsed) {
		if (symbolsOrFrequencies == null) {
			throw new IllegalArgumentException("argument to constructor is null");
		}

		if (symbolsUsed) {
			this.numberOfSymbols = symbolsOrFrequencies.length;
			this.symbols = new ArrayList<>();
			symbolToFrequency = new HashMap<>();
			for (int symbol : symbolsOrFrequencies) {
				this.symbols.add(symbol);
				int frequency = 1;
				if (symbolToFrequency.containsKey(symbol)) {
					frequency += symbolToFrequency.get(symbol);
				}
				symbolToFrequency.put(symbol, frequency);
			}
			this.numberOfDistinctSymbols = symbolToFrequency.size();
		} else {
			this.numberOfDistinctSymbols = symbolsOrFrequencies.length;
			symbolToFrequency = new HashMap<>();
			int index = 0;
			for (int frequency : symbolsOrFrequencies) {
				symbolToFrequency.put(index++, frequency);
			}
			this.symbols = new ArrayList<>();
			int symbol = 0;
			for (int frequency : symbolsOrFrequencies) {
				for (int f = 0; f < frequency; ++f) {
					this.symbols.add(symbol);
					symbol++;
				}
			}
			this.numberOfSymbols = this.symbols.size();
		}
	}

	public Reel(Reel that) {
		if (that == null) {
			throw new IllegalArgumentException("argument is null");
		}

		this.numberOfSymbols = that.numberOfSymbols;
		this.numberOfDistinctSymbols = that.numberOfDistinctSymbols;
		this.symbols = new ArrayList<>();
		for (int symbol : that.symbols) {
			this.symbols.add(symbol);
		}
		this.symbolToFrequency = new HashMap<>();
		for (int symbol : that.symbolToFrequency.keySet()) {
			this.symbolToFrequency.put(symbol, that.symbolToFrequency.get(symbol));
		}
	}

	public int getNumberOfSymbols() {
		return numberOfSymbols;
	}

	public int getNumberOfDistinctSymbols() {
		return numberOfDistinctSymbols;
	}

	public int get(int i) {
		validateIndex(i);
		return symbols.get(i);
	}

	public int getFrequency(int symbol) {
		validateSymbol(symbol);
		return symbolToFrequency.get(symbol);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String sep = "";
		for (int symbol : symbols) {
			sb.append(sep);
			sb.append(symbol);
			sep = " ";
		}
		return sb.toString();
	}

	// throws IllegalArgumentException, unless 0 <= i < size() - 1
	private void validateIndex(int i) {
		if (i < 0 || i >= numberOfSymbols) {
			throw new IllegalArgumentException("index " + i + " should be between 0 and " + (numberOfSymbols - 1));
		}
	}

	private void validateSymbol(int s) {
		if (!symbolToFrequency.containsKey(s)) {
			throw new IllegalArgumentException("symbol " + s + " doesn't exist in this reel");
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
