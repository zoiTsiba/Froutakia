
public class PaylineSegment {
	private final int line;
	private final int reel;
	
	public PaylineSegment(int line, int reel) {
		if (line <= 0) 
			throw new IllegalArgumentException("line: " + line + " is less or equal than 0");
		if (reel <= 0) 
			throw new IllegalArgumentException("reel: " + reel + " is less or equal than 0");
		this.line = line;
		this.reel = reel;
	}
	
	public PaylineSegment(PaylineSegment that) {
		this.line = that.line;
		this.reel = that.reel;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getReel() {
		return reel;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(line);
		sb.append(", ");
		sb.append(reel);
		sb.append(")");
		return sb.toString();
	}
}
