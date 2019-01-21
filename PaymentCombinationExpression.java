import java.util.LinkedList;
import java.util.List;

public class PaymentCombinationExpression {
	
	private final int amount;
	private final String direction;
	private List<String> symbolsList;
	public PaymentCombinationExpression(int amount, String direction, Iterable<String> symbolsList) {
		this.amount = amount;
		this.direction = direction;
		this.symbolsList = new LinkedList<>();
		for (String strSymbol : symbolsList) {
			this.symbolsList.add(strSymbol);
		}
	}
	
	public int amount() {
		return amount;
	}
	
	public String direction() {
		return direction;
	}
	
	public Iterable<String> symbols() {
		return symbolsList;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		String separator = "";
		for (String strSymbol : symbols()) {
			sb.append(separator);
			sb.append(strSymbol);
			separator = ",";
		}
		sb.append(" ");
		sb.append(amount);
		
		sb.append(" ");
		sb.append(direction);
		
		return sb.toString();
	}
    // client
	public static void main(String[] args) {
	
	}

}
