
import java.text.DecimalFormat;

public class FullCycleStats {
	private final Manager manager;
	private double FullCycleSize;
	private double RTP;
	private double hitRate;
	private double hitFreq;
	private double hits;
	private double totalPrize;
	private double volatility;
	private double stDev;

	public double getRTP() {
		return RTP;
	}

	public double getHitRate() {
		return hitRate;
	}

	public double getHitFreq() {
		return hitFreq;
	}

	public double getHits() {
		return hits;
	}

	public double getTotalPrize() {
		return totalPrize;
	}

	public double getVolatility() {
		return volatility;
	}

	public double getStDev() {
		return stDev;
	}

	public double getFullCycleSize() {
		return FullCycleSize;
	}

	public FullCycleStats(Manager manager) {
		if (manager == null)
			throw new IllegalArgumentException("argument to constructor is null");
		this.manager = manager;
		RTP = 0;
		hitRate = 0;
		hits = 0;
		totalPrize = 0;
		volatility = 0;
		stDev = 0;
		FullCycleSize = 0;
		runFullCycle();
	}

	private void runFullCycle() {

		final ReelManager reelManager = manager.getReelManager();
		final SlotMachine slotMachine = reelManager.getSlotMachine();
		final WindowManager windowManager = manager.getWindowManager();
		final PaylineManager paylineManager = manager.getPaylineManager();
		final PaymentManager paymentManager = manager.getPaymentManager();

		// number of reels
		int nReels = slotMachine.getNumberOfReels();

		// to keep track of next element in each of the nReels reels
		int[] indices = new int[nReels];

		while (true) {

			windowManager.getWindow(indices);
			// *****************************************************
			// BEGIN :: do whatever you want with current window
			// *****************************************************
			for (Payline payline : paylineManager.paylines()) {
				int[] paylineSymbols = windowManager.getPaylineSymbols(payline);
				int reward = paymentManager.getReward(paylineSymbols);

				if (reward > 0) {
					totalPrize += reward; // Total reward of the Full Cycle
					hits++; // Winning combinations of the Full Cycle
				}
			}

			FullCycleSize++; // All combinations of the Full Cycle
			// *****************************************************
			// END :: do whatever you want with current window
			// *****************************************************

			// find the rightmost reel that has more
			// elements left after the current element
			// in that reel
			int next = nReels - 1;
			while (next >= 0 && (indices[next] + 1 >= slotMachine.getReel(next).getNumberOfSymbols()))
				next--;

			// no such reel is found so no more combinations left
			if (next < 0)
				break;

			// if found move to next element in that reel
			indices[next]++;

			// for all reels to the right of this
			// reel current index again points to
			// first element
			for (int i = next + 1; i < nReels; i++)
				indices[i] = 0;

		}

		int paylineSize = paylineManager.paylinesSize(); //  Paylines  of the Full Cycle
		hitRate = FullCycleSize / hits; // Hit rate is the ratio of winning combinations in a Full Cycle
		hitFreq = hits / FullCycleSize; // Hit frequency is the odds that the machine will hit a payout on any given spin.
		RTP = totalPrize / (FullCycleSize * paylineSize); // Return to player (RTP) is the theoretical percentage of playing money that returns to the player
	}

	public static void main(String[] args) {

		final String filename;
		final StringBuilder sb;
		long start, end;
		DecimalFormat df;
		float elapsedTimeMinutes;

		filename = "game.txt";
//		filename = "IO/bill.txt";
		sb = new StringBuilder();
		df = new DecimalFormat("#.#####");

		start = System.currentTimeMillis();
		In in = new In(filename);
		Manager manager = new Manager(in);
		FullCycleStats fcs = new FullCycleStats(manager);
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
		sb.append(df.format(fcs.getHitFreq()*100));
		sb.append("%");
		sb.append("\n");
		sb.append(" RTP :");
		sb.append(df.format(fcs.getRTP()));
		sb.append("\n");
		System.out.println(sb.toString());
	}

}