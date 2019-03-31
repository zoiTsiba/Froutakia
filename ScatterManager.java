import java.util.*;

public class ScatterManager {

    private final Manager manager;
    private final Map<Integer, List<Integer>[]> scatterToSumLists;

    public ScatterManager(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("argument is null");
        }

        final ReelManager reelManager = manager.getReelManager();
        final SlotMachine slotMachine = reelManager.getSlotMachine();
        final SymbolManager symbolManager = manager.getSymbolManager();
        final WindowManager windowManager = manager.getWindowManager();
        final int nLines = windowManager.getNumberOfLines();
        final int nReels = windowManager.getNumberOfReels();

        this.manager = manager;
        scatterToSumLists = new HashMap<>();
        for (int scatter : symbolManager.getAllScatters()) {
            List<Integer>[] sumList = new List[nReels];
            for (int i = 0; i < nReels; ++i) {
                sumList[i] = new ArrayList<>();
            }
            scatterToSumLists.put(scatter, sumList);
        }

        for (int scatter : symbolManager.getAllScatters()) {

            for (int reelIndex = 0; reelIndex < nReels; ++reelIndex) {
                Reel reel = slotMachine.getReel(reelIndex);
                int nSymbols = reel.getNumberOfSymbols();
                for (int symbolIndex = 0; symbolIndex < nSymbols; ++symbolIndex) {
                    int sum = 0;
                    for (int lineIndex = 0; lineIndex < nLines; ++lineIndex) {
                        int currectSymbolIndex = (symbolIndex + lineIndex) % nSymbols;
                        int currentSymbol = reel.get(currectSymbolIndex);
                        if (windowManager.isValid(lineIndex + 1, reelIndex + 1)
                                && symbolManager.isScatter(currentSymbol)) {
                            sum++;
                        }
                    }
                    scatterToSumLists.get(scatter)[reelIndex].add(sum);
                }
            }
        }
    }

    public int getNumberOfScatterCombinations(int scatter, int[] indices) {
        if (indices == null) {
            throw new IllegalArgumentException("null argument");
        }

        final WindowManager windowManager = manager.getWindowManager();
        final int nReels = windowManager.getNumberOfReels();
        if (indices.length != nReels) {
            throw new IllegalArgumentException("length of indices should equal number of reels");
        }

        int nCombinations = 1;
        for (int i = 0; i < nReels; i++) {
            int scatterInReel = scatterToSumLists.get(scatter)[i].get(indices[i]);
            if (scatterInReel != 0)
                nCombinations *= scatterInReel;
        }
        return nCombinations;
    }

    public int getNumberOfReelsContainingScatters(int scatter, int[] indices) {
        if (indices == null) {
            throw new IllegalArgumentException("null argument");
        }

        final WindowManager windowManager = manager.getWindowManager();
        final int nReels = windowManager.getNumberOfReels();
        if (indices.length != nReels) {
            throw new IllegalArgumentException("length of indices should equal number of reels");
        }

        int nReelsContainingScatters = 0;
        for (int i = 0; i < nReels; ++i) {
            if (scatterToSumLists.get(scatter)[i].get(indices[i]) > 0) {
                nReelsContainingScatters++;
            }
        }
        return nReelsContainingScatters;
    }

}
