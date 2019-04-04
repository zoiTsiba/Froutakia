import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardManager {
	private final Map<Integer, Map<Integer, Integer>> scatterToCountToReward;
	
	public RewardManager(Manager manager) {
		if (manager == null) throw new IllegalArgumentException("argument is null");
		final Parser parser = manager.getParser();
		final ScatterParser scatterParser = parser.getScatterParser(); 
		final SymbolManager symbolManager = manager.getSymbolManager();
		
		scatterToCountToReward = new HashMap<>();
		for (ScatterExpression se : scatterParser.expressions()) {
			final int scatter = symbolManager.getNumericSymbol(se.getScatterName());
			final Map<Integer, Integer> countToReward = new HashMap<>();
			final List<Integer> counts = new ArrayList<>();
			final List<Integer> amounts = new ArrayList<>();
			for (int count : se.counts()) counts.add(count);
			for (int amount : se.amounts()) amounts.add(amount);
			for (int i = 0; i < counts.size(); ++i) {
				countToReward.put(counts.get(i), amounts.get(i));
			}
			scatterToCountToReward.put(scatter, countToReward);
		}
	}
	
	public int getScatterReward(int scatter, int count) {
		if (!scatterToCountToReward.containsKey(scatter)) 
			throw new IllegalArgumentException("scatter: " + scatter + "hasn't been declared");
		Map<Integer, Integer> countToReward = scatterToCountToReward.get(scatter);
		if (!countToReward.containsKey(count))
//			throw new IllegalArgumentException("count: " + count + " doesn't exist for scatter: " + scatter);
			return 0;
		return countToReward.get(count);
	}
}
