
public class SlotMachineWindow {
	int[][] window;

	public SlotMachineWindow(int[] reelIndices, SlotMachine sm) {
		if (reelIndices == null)
			throw new IllegalArgumentException("first argument of constructor is null");
		if (sm == null)
			throw new IllegalArgumentException("second argument of constructor is null");
		if (sm.R() !=reelIndices.length)
			throw new IllegalArgumentException("number of reels should be equal to length ofreelIndices array");
		for (int i = 0, end =reelIndices.length; i < end; ++i) {
			if (reelIndices[i] < 0 ||reelIndices[i] >= sm.getReel(i).size())
				throw new IllegalArgumentException("index " +reelIndices[i] + " is out of bounds");
		}
		
		window  = new int[sm.L()][sm.R()];
		
		for (int i = 0, end =  reelIndices.length; i < end; ++i) {
			for (int j = 0; j < sm.L(); ++i) {
				int symbol = sm.getReel(i).get((reelIndices[i] + j) % sm.getReel(i).size());
				window[j][i] = symbol;
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < window.length; ++i) {
			for (int j = 0; j < window[0].length; ++j) {
				sb.append(window[i][j]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
