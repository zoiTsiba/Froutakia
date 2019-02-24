import java.util.Arrays;

public class FullCycleStats {
	private final ReelManager reelManager;
	private final SlotMachine slotMachine;
	private final PaymentManager paymentManager;
	

	public FullCycleStats(Manager manager) {
		if (manager == null)
			throw new IllegalArgumentException("argument to constructor is null");
		this.reelManager = manager.getReelManager();
		this.slotMachine = reelManager.getSlotMachine();
		this.paymentManager = manager.getPaymentManager();
		int[] indices = new int[reelManager.getNumberOfReels()];
		runFullCycle(indices, 0);
	}

	public void runFullCycle(int[] indices, int d) {
		if (indices == null)
			throw new IllegalArgumentException("first argument is null");
		if (d >= reelManager.getNumberOfReels())
			return;
		for (int i = 0, len = slotMachine.getReel(d).getNumberOfSymbols(); i < len; ++i) {
			if (d == indices.length - 1) {
				int[] combination = getCombination(indices);
				System.out.println(Arrays.toString(combination) + " pays " + paymentManager.getReward(combination));
				/*
				 * ~ HERE HAPPENS THE MAGIC ~
				 */
			}
			runFullCycle(indices, d + 1);
			indices[d] = (indices[d] + 1) % slotMachine.getReel(d).getNumberOfSymbols();
		}
	}
	
	private int[] getCombination(int[] indices) {
		if (indices == null) {
			throw new IllegalArgumentException("argument is null");
		}
		int[] combination = new int[indices.length];
		for (int i = 0; i < indices.length; ++i) {
			combination[i] = slotMachine.get(i, indices[i]);
		}
		return combination;
	}

	public static void main(String[] args) {
		String filename = "IO/game.txt";
		In in = new In(filename);
		Manager manager = new Manager(in);
		FullCycleStats fcs = new FullCycleStats(manager);
	}

}