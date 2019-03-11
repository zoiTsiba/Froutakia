import java.util.Arrays;

public class FullCycleStats {
	private final Manager manager;

	public FullCycleStats(Manager manager) {
		if (manager == null)
			throw new IllegalArgumentException("argument to constructor is null");
		this.manager = manager;
		final PaylineManager paylineManager = manager.getPaylineManager();
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

			// *****************************************************
			// BEGIN :: do whatever you want with current window
			// *****************************************************
			windowManager.getWindow(indices);

			String windowStr = "\n" + windowManager.windowToString();
			for (Payline payline : paylineManager.paylines()) {
				int[] paylineSymbols = windowManager.getPaylineSymbols(payline);
				int reward = paymentManager.getReward(paylineSymbols);

				StringBuilder sb = new StringBuilder();
				if (reward > 0) {
					sb.append(windowStr);
					windowStr = "";
					sb.append(payline);
					sb.append(" pays ");
					sb.append(reward);
					System.out.println(sb.toString());
				}

			}
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
				return;

			// if found move to next element in that reel
			indices[next]++;

			// for all reels to the right of this
			// reel current index again points to
			// first element
			for (int i = next + 1; i < nReels; i++)
				indices[i] = 0;
		}
	}

	public static void main(String[] args) {
		String filename = "IO/game.txt";
		In in = new In(filename);
		Manager manager = new Manager(in);
		FullCycleStats fcs = new FullCycleStats(manager);
	}

}