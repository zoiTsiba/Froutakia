import java.util.ArrayList;

public class WindowManager {
	private final Manager manager;
	private final boolean[][] marked; // valid slots
	private final int[][] window;     // values 
	private final int nLines;
	private final int nReels;

	public WindowManager(Manager manager) {
		if (manager == null) {
			throw new IllegalArgumentException("argument to constructor is null");
		}
		
		this.manager = manager;
		final Parser parser = manager.getParser();
		final WindowParser wp = parser.getWindowParser();
		final ArrayList<WindowExpression> windowExpressions = new ArrayList<>();
		final WindowExpression windowExpression;

		WindowManagerValidator.check(parser);
		
		for (WindowExpression we : wp.expressions()) {
			windowExpressions.add(we);
		}
		windowExpression = windowExpressions.get(0); 
		nLines = windowExpression.getNumberOfLines();
		nReels = windowExpression.getNumberOfReels();
		this.marked = new boolean[nLines + 1][nReels + 1]; // IMPORTANT: 1-based
		this.window = new int[nLines + 1][nReels + 1]; // IMPORTANT: 1-based 
		for (int l = 1; l <= nLines; ++l) {
			for (int r = 1; r <= nReels; ++r) {
				this.marked[l][r] = true;
			}
		}
		
	}
	
	public boolean isValid(int line, int reel) {
		if (line <= 0 || line > nLines) {
			throw new IllegalArgumentException("line " + line + " should be between 1 and " + nLines);
		}
		if (reel <= 0 || reel > nReels) {
			throw new IllegalArgumentException("reel " + reel + " should be between 1 and " + nReels);
		}
		return marked[line][reel];
	}
	
	public int getNumberOfLines() {
		return nLines;
	}

	public int getNumberOfReels() {
		return nReels;
	}
	
	public void getWindow(int[] indices) {
		if (indices == null) {
			throw new IllegalArgumentException("null argument");
		}
		if (indices.length != nReels) {
			throw new IllegalArgumentException("length of indices should equal number of reels");
		}
		ReelManager reelManager = manager.getReelManager();
		SlotMachine slotMachine = reelManager.getSlotMachine();
		for (int reelIndex = 0; reelIndex < nReels; ++reelIndex) {
			Reel reel = slotMachine.getReel(reelIndex);
			for (int lineIndex = 0; lineIndex < nLines; ++lineIndex) {
				int symbolIndex = (indices[reelIndex] + lineIndex) % reel.getNumberOfSymbols();
				int symbol = reel.get(symbolIndex);
				window[lineIndex + 1][reelIndex + 1] = symbol;
			}
		}
		
	}
	
	public int[] getPaylineSymbols(Payline payline) {
		if (payline == null) {
			throw new IllegalArgumentException("null argument");
		}
		int[] paylineSymbols = new int[payline.size()];
		int index = 0;
		for (PaylineSegment paylineSegment : payline.paylineSegments()) {
			int lineIndex = paylineSegment.getLine();
			int reelIndex = paylineSegment.getReel();
			paylineSymbols[index++] = window[lineIndex][reelIndex];
		}
		return paylineSymbols;
	}
	
	public String windowToString() {
		StringBuilder sb = new StringBuilder();
		String sep = "";
		for (int lineIndex = 1; lineIndex <= nLines; ++lineIndex) {
			sep = "";
			for (int reelIndex = 1; reelIndex <= nReels; ++reelIndex) {
				sb.append(sep);
				sb.append(window[lineIndex][reelIndex]);
				sep = " ";
			}
			sep = "\n";
			sb.append(sep);
		}
		return sb.toString();
	}
	
	private static class WindowManagerValidator {

		public static void check(Parser parser) {
			checkIfOnlyOneWindow(parser);
		}
		
		private static void checkIfOnlyOneWindow(Parser parser) {
			final WindowParser wp = parser.getWindowParser();
			final ArrayList<WindowExpression> windowExpressions = new ArrayList<>();
			int n;
			
			n = 0;
			for (WindowExpression we : wp.expressions()) {
				if (n > 1) {
					throw new IllegalArgumentException("there is more than one window declaration");
				}
				windowExpressions.add(we);
				n++;
			}
			
		}
	}

}
