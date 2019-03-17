
import java.util.ArrayList;
import java.util.List;

public class PaylineManager {
	private final List<Payline> paylines;
	public PaylineManager(Manager manager) {

		final Parser parser = manager.getParser();
		final PaylineParser paylineParser = parser.getPaylineParser();
		paylines = new ArrayList<>();

		PaylineManagerValidator.check(parser, manager);

		for (PaylineExpression pe : paylineParser.expressions()) {
			List<Integer> lineCoordList = new ArrayList<>();
			for (int lineCoord : pe.lineCoordinates()) {
				lineCoordList.add(lineCoord);
			}
			List<Integer> reelCoordList = new ArrayList<>();
			for (int reelCoord : pe.reelCoordinates()) {
				reelCoordList.add(reelCoord);
			}
			List<PaylineSegment> paylineSegments = new ArrayList<>();
			for (int i = 0, len = lineCoordList.size(); i < len; ++i) {
				int line = lineCoordList.get(i);
				int reel = reelCoordList.get(i);
				PaylineSegment ps = new PaylineSegment(line, reel);
				paylineSegments.add(ps);
			}
			final String paylineName = pe.getPaylineName();
			Payline payline = new Payline(paylineName, paylineSegments);
			paylines.add(payline);
		}
	}

	public Iterable<Payline> paylines() {
		return paylines;
	}

	public int paylinesSize() {
		return paylines.size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		String sep ="";
		for (Payline p : paylines) {
			sb.append(sep);
			sb.append(p);
			sep = "\n";
		}
		return sb.toString();
	}

	private static class PaylineManagerValidator {

		public static void check(Parser parser, Manager manager) {
			checkIfOverflowsWindow(parser, manager);
		}

		private static void checkIfOverflowsWindow(Parser parser, Manager manager) {
			final WindowManager wm = manager.getWindowManager();
			final PaylineParser pp = parser.getPaylineParser();

			for (PaylineExpression pe : pp.expressions()) {
				List<Integer> lineCoordinates = new ArrayList<>();
				List<Integer> reelCoordinates = new ArrayList<>();
				for (int lineCoordinate : pe.lineCoordinates())
					lineCoordinates.add(lineCoordinate);
				for (int reelCoordinate : pe.reelCoordinates())
					reelCoordinates.add(reelCoordinate);

				for (int i = 0; i < lineCoordinates.size(); ++i) {
					int lineCoordinate = lineCoordinates.get(i);
					int reelCoordinate = reelCoordinates.get(i);
					if (!wm.isValid(lineCoordinate, reelCoordinate)) {
						throw new IllegalArgumentException("payline coordinate: (" + lineCoordinate + ", " + reelCoordinate + ") is invalid");
					}
				}
			}
		}
	}
}
