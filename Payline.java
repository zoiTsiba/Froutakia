import java.util.ArrayList;
import java.util.List;

public class Payline {
	private final List<PaylineSegment> paylineSegments;
	
	public Payline(Iterable<PaylineSegment> paylineSegments) {
		this.paylineSegments = new ArrayList<>();
		for (PaylineSegment ps : paylineSegments) {
			PaylineSegment defensiveCopy = new PaylineSegment(ps);
			this.paylineSegments.add(defensiveCopy);
		}
	}
	
	public Iterable<PaylineSegment> paylineSegments() {
		return paylineSegments;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String sep = "";
		for (PaylineSegment ps : paylineSegments) {
			sb.append(sep);
			sb.append(ps);
			sep = "-";
		}
		return sb.toString();
		
	}

}
