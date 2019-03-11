import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class ReelManager {
	private final SlotMachine slotMachine;
	private final List<String> reelNames;
	private final int numberOfReels;
	private final List<Integer>[] scatterSums;

	public ReelManager(Manager manager) {
		if (manager == null) {
			throw new IllegalArgumentException("argument to constructor is null");
		}

		final Parser parser = manager.getParser();
		final ReelParser reelParser = parser.getReelParser();
		final SymbolManager symbolManager = manager.getSymbolManager();
		final WindowManager windowManager = manager.getWindowManager();
		final List<Reel> reels = new ArrayList<>();
		reelNames = new ArrayList<>();

		ReelManagerValidator.check(parser, manager);

		for (ReelExpression re : reelParser.expressions()) {
			reelNames.add(re.getReelName());
			int[] symbolsArr = new int[re.symbolCount()];
			int index = 0;
			for (String symbol : re.symbols()) {
				symbolsArr[index++] = symbolManager.getNumericSymbol(symbol);
			}
			Reel reel = new Reel(symbolsArr, true);
			reels.add(reel);
		}
		slotMachine = new SlotMachine(reels);
		numberOfReels = slotMachine.getNumberOfReels();

		final int nLines = windowManager.getNumberOfLines();
		final int nReels = windowManager.getNumberOfReels();
		scatterSums = new List[reels.size()];
		for (int i = 0; i < reels.size(); ++i) {
			scatterSums[i] = new ArrayList<>();
		}
		for (int reelIndex = 0; reelIndex < nReels; ++reelIndex) {
			Reel reel = slotMachine.getReel(reelIndex);
			int nSymbols = reel.getNumberOfSymbols();
			for (int symbolIndex = 0; symbolIndex < nSymbols; ++symbolIndex) {
				int sum = 0;
				for (int lineIndex = 0; lineIndex < nLines; ++lineIndex) {
					int currectSymbolIndex = (symbolIndex + lineIndex) % nSymbols;
					if (windowManager.isValid(lineIndex + 1, reelIndex + 1)
							&& symbolManager.isScatter(reel.get(currectSymbolIndex))) {
						sum++;
					}
				}
				scatterSums[reelIndex].add(sum);
			}
		}
	}

	public SlotMachine getSlotMachine() {
		return slotMachine;
	}

	public int getNumberOfReels() {
		return numberOfReels;
	}

	public String getReelName(int i) {
		if (i < 0 || i >= numberOfReels) {
			throw new IllegalArgumentException("Reel index: " + i + " should be between 0 and " + (numberOfReels - 1));
		}
		return reelNames.get(i);
	}
	
	public int scatterSum(int[] indices) {
		if (indices == null) {
			throw new IllegalArgumentException("null argument");
		}
		if (indices.length != numberOfReels) {
			throw new IllegalArgumentException("length of indices should equal number of reels");
		}
		
		int sum = 0;
		for (int i = 0; i < numberOfReels; ++i) {
			sum += scatterSums[i].get(indices[i]);
		}
		return sum;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < slotMachine.getNumberOfReels(); ++i) {
			sb.append(reelNames.get(i));
			sb.append(" : ");
			Reel reel = slotMachine.getReel(i);
			String sep = "";
			for (int j = 0; j < reel.getNumberOfSymbols(); ++j) {
				sb.append(sep);
				sb.append(reel.get(j));
				sep = " ";
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	private static class ReelManagerValidator {

		public static void check(Parser parser, Manager manager) {
			checkNumberOfReels(parser, manager);
			checkNumberOfLines(parser, manager);
			checkSymbolsAppearAtLeastOnce(parser, manager);
		}

		private static void checkNumberOfReels(Parser parser, Manager manager) {
			final WindowManager wm = manager.getWindowManager();
			final ReelParser rp = parser.getReelParser();
			final ArrayList<ReelExpression> reels = new ArrayList<>();
			for (ReelExpression re : rp.expressions()) {
				reels.add(re);
			}
			if (wm.getNumberOfReels() != reels.size()) {
				throw new IllegalArgumentException("window #reels " + wm.getNumberOfReels()
						+ " is different from #declared reels " + reels.size());
			}
		}

		private static void checkNumberOfLines(Parser parser, Manager manager) {
			final WindowManager wm = manager.getWindowManager();
			final ReelParser rp = parser.getReelParser();
			final ArrayList<ArrayList<String>> reels = new ArrayList<>();
			for (ReelExpression re : rp.expressions()) {
				ArrayList<String> symbols = new ArrayList<>();
				reels.add(symbols);
				for (String symbol : re.symbols()) {
					symbols.add(symbol);
				}
			}
			for (ArrayList<String> reel : reels) {
				if (reel.size() < wm.getNumberOfLines()) {
					throw new IllegalArgumentException("a reel has less symbols than the window's #lines");
				}
			}
		}

		private static void checkSymbolsAppearAtLeastOnce(Parser parser, Manager manager) {
			final SymbolManager sm = manager.getSymbolManager();
			final ReelParser rp = parser.getReelParser();
			for (ReelExpression re : rp.expressions()) {
				Set<String> symbols = new HashSet<>();
				for (String symbol : re.symbols()) {
					if (!sm.isSymbol(symbol)) {
						throw new IllegalArgumentException("symbol " + symbol + " has not been declared");
					}
					symbols.add(symbol);
				}
				if (symbols.size() != sm.getNumberOfAllSymbols()) {
					throw new IllegalArgumentException(" reel doesn't contain all declared symbols at least once");
				}
			}
		}
	}

}
