import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FCS {
	private static final int N_THREADS = Runtime.getRuntime().availableProcessors();

	private final Manager manager;
	private final ExecutorService threadPool;
	private final AtomicInteger totalPrize;
	private final AtomicInteger hits;
	private final AtomicInteger FullCycleSize;
	private double RTP;
	private double hitRate;
	private double hitFreq;
	private double volatility;
	private double stDev;

//	private final AtomicInteger count2;
//	private final AtomicInteger count3;
//	private final AtomicInteger count4;
//	private final AtomicInteger count5;

	public FCS(Manager manager) {
		if (manager == null)
			throw new IllegalArgumentException("argument is null");

		this.manager = manager;
		threadPool = Executors.newFixedThreadPool(N_THREADS);
		totalPrize = new AtomicInteger();
		hits = new AtomicInteger();
		FullCycleSize = new AtomicInteger();
		RTP = 0;
		hitRate = 0;
		volatility = 0;
		stDev = 0;

//		count2 = new AtomicInteger();
//		count3 = new AtomicInteger();
//		count4 = new AtomicInteger();
//		count5 = new AtomicInteger();

		runFullCycle();
	}

	public void runFullCycle() {
		final ReelManager reelManager = manager.getReelManager();
		final int nReels = reelManager.getNumberOfReels();
		final SlotMachine slotMachine = reelManager.getSlotMachine();
		final int[] max = new int[nReels];
		for (int i = 0; i < nReels; ++i) {
			max[i] = slotMachine.getReel(i).getNumberOfSymbols();
		}
		int totalCombinations = 1;
		for (int x : max)
			totalCombinations *= x;
		final int combinationsPerThread = (totalCombinations / N_THREADS == 0) ? totalCombinations
				: totalCombinations / N_THREADS;
		int threadsNeeded = (combinationsPerThread == 0) ? 1 : N_THREADS;
		for (int i = 0; i < threadsNeeded; ++i) {
			final int k = i * combinationsPerThread;
			final int[] start = kthCombination(k + 1, max); // 1-based indices
			for (int j = 0; j < nReels; ++j)
				start[j]--; // 0-based indices
			final int length = (i == threadsNeeded - 1) ? totalCombinations - k : combinationsPerThread;
			threadPool.execute(new FullCycleRunnable(i, start, length, manager.getParser().getAllText()));
		}
		awaitTerminationAfterShutdown(threadPool);

		final PaylineManager paylineManager = manager.getPaylineManager();
		// Paylines of the Full Cycle
		int paylineSize = paylineManager.paylinesSize();
		// Hit rate is the ratio of winning combinations in a Full Cycle
		hitRate = (1.0) * FullCycleSize.get() / hits.get();
		// Hit frequency is the odds that the machine will hit a payout on any given
		// spin.
		hitFreq = 1.0 / hitRate;
		// Return to player (RTP) is the theoretical percentage of playing money that
		// returns to the player
		RTP = (1.0) * totalPrize.get() / (FullCycleSize.get() * paylineSize);

		// FOR DEBUGGING; TO BE REMOVED
//		System.out.println("2: " + count2.toString());
//		System.out.println("3: " + count3.toString());
//		System.out.println("4: " + count4.toString());
//		System.out.println("5: " + count5.toString());

	}

	public double getRTP() {
		return RTP;
	}

	public double getHitRate() {
		return hitRate;
	}

	public double getHitFreq() {
		return hitFreq;
	}

	public int getHits() {
		return hits.get();
	}

	public int getTotalPrize() {
		return totalPrize.get();
	}

	public double getVolatility() {
		return volatility;
	}

	public double getStDev() {
		return stDev;
	}

	public int getFullCycleSize() {
		return FullCycleSize.get();
	}

	private class FullCycleRunnable implements Runnable {

		private final int id;
		private final int[] start;
		private final int length;
		private final Manager manager;

		public FullCycleRunnable(int id, int[] start, int length, String allText) {
			this.id = id;
			this.start = Arrays.copyOf(start, start.length);
			this.length = length;
			Scanner scanner = new Scanner(allText);
			In in = new In(scanner);
			manager = new Manager(in.readAll());
		}

		public void run() {
			final WindowManager windowManager = manager.getWindowManager();
			final PaylineManager paylineManager = manager.getPaylineManager();
			final PaymentManager paymentManager = manager.getPaymentManager();
			final ReelManager reelManager = manager.getReelManager();
			final SlotMachine slotMachine = reelManager.getSlotMachine();
			final int nReels = reelManager.getNumberOfReels();
			final ScatterManager scatterManager = manager.getScatterManager();
			final RewardManager rewardManager = manager.getRewardManager();
			final SymbolManager symbolManager = manager.getSymbolManager();

			int processed = 0;
			while (true) {
				windowManager.getWindow(start);
				// *****************************************************
				// BEGIN :: do whatever you want with current window
				// *****************************************************
				for (Payline payline : paylineManager.paylines()) {
					int[] paylineSymbols = windowManager.getPaylineSymbols(payline);
					int reward = paymentManager.getReward(paylineSymbols);

					if (reward > 0) {
						totalPrize.addAndGet(reward); // Total reward of the Full Cycle
						hits.incrementAndGet(); // Winning combinations of the Full Cycle
					}
				}

				for (int scatter : symbolManager.getAllScatters()) {
					int nScatterCombinations = scatterManager.getNumberOfScatterCombinations(scatter, start);
					int nReelsWithScatters = scatterManager.getNumberOfReelsContainingScatters(scatter, start);
					int scatterRewardPerCombination = rewardManager.getScatterReward(scatter, nReelsWithScatters);
					int scatterReward = nScatterCombinations * scatterRewardPerCombination;
//					switch (nReelsWithScatters) {
//					case 2:
//						count2.addAndGet(nScatterCombinations);
//						System.out.println(count2.toString() + " " + windowManager.windowToString());
//						break;
//					case 3:
//						count3.addAndGet(nScatterCombinations);
//						break;
//					case 4:
//						count4.addAndGet(nScatterCombinations);
//						break;
//					case 5:
//						count5.addAndGet(nScatterCombinations);
//						break;
//					default:
//					}
					if (scatterReward > 0) {
						totalPrize.addAndGet(scatterReward);
						// hits.incrementAndGet(); // Winning combinations of the Full Cycle
						hits.addAndGet(nScatterCombinations);
					}
				}

				FullCycleSize.incrementAndGet(); // All combinations of the Full Cycle
				// *****************************************************
				// END :: do whatever you want with current window
				// *****************************************************

				processed++;
				if (processed == length)
					break;
				// find the rightmost reel that has more
				// elements left after the current element
				// in that reel
				int next = nReels - 1;
				while (next >= 0 && (start[next] + 1 >= slotMachine.getReel(next).getNumberOfSymbols()))
					next--;

				// no such reel is found so no more combinations left
				if (next < 0)
					break;

				// if found move to next element in that reel
				start[next]++;

				// for all reels to the right of this
				// reel current index again points to
				// first element
				for (int i = next + 1; i < nReels; i++)
					start[i] = 0;

			}

		}
	}

	/**
	 * Returns the 1-based k-th combination of {@code max.length} reels, where i-th
	 * reel has size {@code max[i]} and the combinations change from right to left.
	 * 
	 * The {@code k} parameter must be between 0 and Π{@code max[i]}(i = 0, i <
	 * max.length), otherwise an {@code IllegalArgumentException} is thrown. Also
	 * the {@code max} argument must contain only positive elements, otherwise an
	 * {@code IllegalArgumentException} is thrown.
	 * 
	 * @param k
	 *            the combination index
	 * @param max
	 *            the array that holds the size of the reels
	 * @return the 1-based k-th combination
	 */
	private static int[] kthCombination(int k, int[] max) {
		if (max == null)
			throw new IllegalArgumentException("second argument is null");
		for (int x : max)
			if (x <= 0)
				throw new IllegalArgumentException("element: " + x + "should be positive");
		final int n = max.length;
		final int[] products = new int[n];
		int product = 1;
		for (int i = n - 1; i >= 0; --i) {
			product *= max[i];
			products[i] = product;
		}
		if (k <= 0 || k > products[0])
			throw new IllegalArgumentException("k: " + k + " should be between 1 and " + products[0]);
		int index = 0;
		int remainder = k;
		final int[] result = new int[n];
		while (index < n - 1) {
			int quotient = remainder / products[index + 1];
			remainder = remainder % products[index + 1];
			if (remainder == 0) {
				result[index] = quotient;
				for (int i = index + 1; i < n; ++i)
					result[i] = max[i];
				return result;
			}
			result[index] = quotient + 1;
			index++;
		}
		result[index] = remainder; // index == n - 1
		return result;
	}

	private static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
		threadPool.shutdown();
		try {
			if (!threadPool.awaitTermination(10, TimeUnit.MINUTES)) {
				threadPool.shutdownNow();
			}
		} catch (InterruptedException ex) {
			threadPool.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	public static void main(String[] args) {
		// int[] max = { 6, 5, 7 };
		// int k = 211;
		// int[] indexes = FCS.kthCombination(k, max);
		// System.out.println(Arrays.toString(indexes));

		final String filename;
		final StringBuilder sb;
		long start, end;
		DecimalFormat df;
		float elapsedTimeMinutes;

		// filename = "game.txt";
		// filename = "IO/bill.txt";
		// filename = "IO/game.txt";
		filename = "IO/small.txt";
		sb = new StringBuilder();
		df = new DecimalFormat("#.#####");

		start = System.currentTimeMillis();
		In in = new In(filename);
		Manager manager = new Manager(in.readAll());
		FCS fcs = new FCS(manager);
		end = System.currentTimeMillis();

		elapsedTimeMinutes = (end - start) / (60 * 1000F);
		sb.append(" Time : ");
		sb.append(elapsedTimeMinutes);
		sb.append(" min");
		sb.append("\n");
		sb.append(" FullCycle Size :");
		sb.append(df.format(fcs.getFullCycleSize()));
		sb.append("\n");
		sb.append(" Total prize :");
		sb.append(df.format(fcs.getTotalPrize()));
		sb.append("\n");
		sb.append(" Hits :");
		sb.append(df.format(fcs.getHits()));
		sb.append("\n");
		sb.append(" HitRate :");
		sb.append(df.format(fcs.getHitRate()));
		sb.append("\n");
		sb.append(" Hit Frequency :");
		sb.append(df.format(fcs.getHitFreq() * 100));
		sb.append("%");
		sb.append("\n");
		sb.append(" RTP :");
		sb.append(df.format(fcs.getRTP()));
		sb.append("\n");
		System.out.println(sb.toString());
	}
}
