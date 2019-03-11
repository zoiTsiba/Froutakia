import java.util.ArrayList;
import java.util.List;

public class Payline {
	
	private final String name;
	private final List<PaylineSegment> paylineSegments;
	private final int size;
	
	public Payline(String name, Iterable<PaylineSegment> paylineSegments) {
		this.name = name;
		this.paylineSegments = new ArrayList<>();
		for (PaylineSegment ps : paylineSegments) {
			PaylineSegment defensiveCopy = new PaylineSegment(ps);
			this.paylineSegments.add(defensiveCopy);
		}
		this.size = this.paylineSegments.size();
	}
	
	public Iterable<PaylineSegment> paylineSegments() {
		return paylineSegments;
	}
	
	public String getName() {
		return name;
	}
	
	public int size() {
		return size;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(": ");
		String sep = "";
		for (PaylineSegment ps : paylineSegments) {
			sb.append(sep);
			sb.append(ps);
			sep = "-";
		}
		return sb.toString();
		
	}

}
