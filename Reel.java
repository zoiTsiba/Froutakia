
public class Reel {

	private final int[] reel;
	private final int[] frequencies;
	private final int size;
	private int numberOfSymbols;

	public Reel(int[] frequencies) {

		if (frequencies == null)
			throw new IllegalArgumentException("argument 'freq' to constructor is null");
		for (int frequency : frequencies) {
			if (frequency <= 0)
				throw new IllegalArgumentException("frequencies must be positive");
		}

		this.numberOfSymbols = frequencies.length;
		this.frequencies = new int[this.numberOfSymbols];
		for (int i = 0; i < this.numberOfSymbols; ++i)
			this.frequencies[i] = frequencies[i];

		int total = 0;
		for (int frequency : frequencies) {
			total += frequency;
		}
		this.size = total;
		this.reel = new int[this.size];
		int i = 0;
		for (int j = 0; j < this.numberOfSymbols; ++j) {
			for (int k = 0; k < frequencies[j]; ++k) {
				reel[i++] = j;
			}
		}
	}

	public Reel(int[] reel, int numberOfSymbols) {

		if (reel == null)
			throw new IllegalArgumentException("argument 'reel' to constructor is null");		
		if (numberOfSymbols < 0)
			throw new IllegalArgumentException("number of symbols is negative");		
		this.size = reel.length;
		boolean[] marked = new boolean[numberOfSymbols];
		for (int i = 0; i < reel.length; ++i)
			validate(reel[i]);
		for (int i = 0; i < reel.length; ++i)
			marked[reel[i]] = true;
		for (int i = 0; i < reel.length; ++i)
			if (!marked[i])
				throw new IllegalArgumentException("symbol " + i + " is missing from the reel");
		
		this.numberOfSymbols = numberOfSymbols;
		this.frequencies = new int[this.numberOfSymbols];
		for (int i = 0; i < this.size; ++i)
			this.frequencies[reel[i]]++;
		
		this.reel = new int[this.size];
		for (int i = 0; i < this.size; ++i)
			this.reel[i] = reel[i];
	}
	
	public Reel(Reel that) {
		if (that == null)
			throw new IllegalArgumentException("argument 'that' to constructor is null");
		
		this.size = that.size();
		this.numberOfSymbols = that.getNumberOfSymbols();
		
		this.reel = new int[this.size];
		for (int i = 0; i < this.size; ++i)
			this.reel[i] = that.reel[i];
		
		this.frequencies = new int[this.numberOfSymbols];
		for (int i = 0; i < this.numberOfSymbols; ++i)
			this.frequencies[i] = that.frequencies[i];
	}

	public int size() {
		return size;
	}
	
	public int getNumberOfSymbols() {
		return numberOfSymbols;
	}

	public int getSymbol(int i) {
		validate(i);
		return i;
	}

	public int getFrequency(int symbol) {
		validate(symbol);
		return frequencies[symbol];
	}
	
	// throws IllegalArgumentException, unless 0 <= i < size() - 1
	protected void validate(int i) {
		if (i < 0 || i >= this.size)
			throw new IllegalArgumentException("index/symbol " + i + " is not between 0 and " + (size() - 1));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
