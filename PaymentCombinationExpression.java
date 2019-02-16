import java.util.LinkedList;
import java.util.List;

public class PaymentCombinationExpression {
	
	private final int amount;
	private final String direction;
    private final List<String> symbolsList;
    private final int symbolCount; 
    
	public PaymentCombinationExpression(int amount, String direction, Iterable<String> symbolsList) {
		this.amount = amount;
		this.direction = direction;
		this.symbolsList = new LinkedList<>();
		int auxSymbolCount = 0;
		for (String strSymbol : symbolsList) {
			this.symbolsList.add(strSymbol);
			auxSymbolCount++;
		}
		this.symbolCount = auxSymbolCount;
    }
    
	public PaymentCombinationExpression(PaymentCombinationExpression that) {
		this.amount = that.amount;
		this.direction = that.direction;
		this.symbolsList = new LinkedList<>();
		for (String strSymbol : that.symbolsList) {
			this.symbolsList.add(strSymbol);
		}
		this.symbolCount = that.symbolCount;
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
	
	public int symbolCount() {
		return symbolCount;
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