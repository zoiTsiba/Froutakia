import java.util.ArrayList;

public class WindowManager {
	private final boolean[][] window;
	private final int nLines;
	private final int nReels;

	public WindowManager(Manager manager) {
		if (manager == null) {
			throw new IllegalArgumentException("argument to constructor is null");
		}
		
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
		this.window = new boolean[nLines + 1][nReels + 1]; // making a 1-based indexed window array 
		for (int l = 1; l <= nLines; ++l) {
			for (int r = 1; r <= nReels; ++r) {
				this.window[l][r] = true;
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
		return window[line][reel];
	}
	
	public int getNumberOfLines() {
		return nLines;
	}

	public int getNumberOfReels() {
		return nReels;
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
