import java.util.LinkedList;
import java.util.Queue;

public class SlotMachine {

	private final int numberOfReels;
	private final int numberOfLines;
	private final int numberOfSymbols;
	private final Reel[] reels;

	public SlotMachine(Reel[] reels, int numberOfLines) {
		if (reels == null)
			throw new IllegalArgumentException("first argument to constructor is null");
		if (numberOfLines <= 0)
			throw new IllegalArgumentException("number of lines must be positive");
		for (Reel reel : reels) {
			if (reel.size() < numberOfLines)
				throw new IllegalArgumentException(
						"reel has fewer symbols: " + " than the slot's number of lines: " + numberOfLines);
		}
		for (int i = 1; i < reels.length; ++i) {
			if (reels[i].S() != reels[i - 1].S())
				throw new IllegalArgumentException("reels have different number of distinct symbols");
		}

		this.numberOfReels = reels.length;
		this.numberOfLines = numberOfLines;
		this.numberOfSymbols = reels[0].S();
		this.reels = new Reel[numberOfReels];

		for (int i = 0; i < numberOfReels; ++i) {
			this.reels[i] = new Reel(reels[i]);
		}
	}

	public int R() {
		return numberOfReels;
	}

	public int S() {
		return numberOfSymbols;
	}

	public int L() {
		return numberOfLines;
	}

	public Reel getReel(int i) {
		if (i < 0 || i >= numberOfReels)
			throw new IllegalArgumentException("index " + i + " is not between 0 and " + (numberOfReels - 1));
		return reels[i];
	}
	
	public Iterable<int[]> combinations() {
		Queue<int[]> queue = new LinkedList<>();
		int[] indices = new int[numberOfReels];
		combinations(queue, indices, 0);		
		return queue;
	}
	
	private void combinations(Queue<int[]> queue, int[] indices, int d) {
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");		
		for (Reel reel : reels) {
			sb.append(reel.toString());
			sb.append("\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {

		int numberOfSymbols = 5;
		int numberOfLines = 3;
		int[] line1 = { 0, 1, 2, 3, 4 };
		int[] line2 = { 4, 3, 2, 1, 0, 1, 2, 3, 4 };
		int[] line3 = { 2, 3, 4, 1, 2, 0, 3, 3, 2, 4 };
		int[] freq = { 3, 1, 4, 2, 5 };

		Reel r1 = new Reel(line1, numberOfSymbols);
		Reel r2 = new Reel(line2, numberOfSymbols);
		Reel r3 = new Reel(line3, numberOfSymbols);
		Reel r4 = new Reel(freq);

		Reel[] reels = { r1, r2, r3, r4 };

		SlotMachine sm = new SlotMachine(reels, numberOfLines);
	    int[] indices = {0,0,0,0};
		SlotMachineWindow w = new SlotMachineWindow( indices , sm ); 
		System.out.println(w.toString());
	}

}
