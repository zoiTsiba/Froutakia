import java.util.List;

public class SlotMachine {
	private static final int MIN_NUM_OF_REELS = 3;
	
	private final int numberOfReels;
	private final int numberOfLines;
	private final int numberOfSymbols;
	private final Reel[] reels;
	
	public SlotMachine(Reel[] reels, int numberOfLines) {
		if (reels == null) 
			throw new IllegalArgumentException("'reels' argument to constructor is null");
		if (numberOfLines <= 0) 
			throw new IllegalArgumentException("number of lines must be positive");
		if (reels.length < MIN_NUM_OF_REELS)
			throw new IllegalArgumentException("number of reels " + reels.length + " is less than minimum " + MIN_NUM_OF_REELS);
		
		for (int i = 1; i < reels.length; ++i) {
			if (reels[i].getNumberOfSymbols() != reels[i - 1].getNumberOfSymbols())
				throw new IllegalArgumentException("reels have different number of distinct symbols");
		}
	    
	    this.numberOfReels = reels.length;
	    this.numberOfLines = numberOfLines;
	    this.numberOfSymbols = reels[0].getNumberOfSymbols();
	    this.reels = new Reel[numberOfReels];
		
	    for (int i = 0; i < numberOfReels; ++i) {
	    	reels[i] = new Reel(reels[i]);
	    }
	}
	
	public Reel getReel(int i) {
		if (i < 0 || i >= numberOfReels)
			throw new IllegalArgumentException("index " + i + " is not between 0 and " + (numberOfReels - 1));
		return reels[i];
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
