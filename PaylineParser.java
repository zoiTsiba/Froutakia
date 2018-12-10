import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code PaylineParser} class parses a valid "payline declaration"
 * expression and provides a method that returns the positions of this payline
 * as an Iterable and a method that returns this payline's alias.
 * 
 * A valid payline decalration expression has thefollowing form:
 * 
 * payline paylineAlias: pos1,pos2,..posN;
 * 
 * i.e. payline pl1: 2,2,2,2,2;
 * 
 * There cannot be whitespace characters between a payline position and a comma,
 * or between the payline's alias and ":". But there can be one or more after
 * the "payline" keyword, after "paylineAlias:" and before ";" . *
 * 
 * Valid characters for the payline's alias are: A..Z, a..z, 0..9
 * 
 * 
 * @author AccelSprinter
 *
 */
public class PaylineParser {

	private static final String VALID_PAYLINE_NAME_CHARS_CLASS = "[a-zA-Z0-9]"; // acceptable characters for a payline
																				// alias

	private static final String PAYLINE_PATTERN = "payline\\s+(" + VALID_PAYLINE_NAME_CHARS_CLASS
			+ "+):\\s*(\\d+(,\\d+)*)\\s*;";

	private final String paylineAlias;
	private final LinkedList<Integer> paylinePositionList; // payline position list

	/**
	 * Instantiates a {@code PaylineParser} class from a valid payline declaration
	 * expression.
	 * 
	 * @param expression
	 *            the payline declaration expression
	 */
	public PaylineParser(String expression) {

		if (expression == null)
			throw new IllegalArgumentException("argument to constructor is null");

		Pattern pattern = Pattern.compile(PAYLINE_PATTERN);
		Matcher matcher = pattern.matcher(expression);

		if (!matcher.matches())
			throw new IllegalArgumentException(expression + " is not a valid payline declaration expression");

		paylineAlias = matcher.group(1);
		paylinePositionList = new LinkedList<>();

		String strPayline = matcher.group(2);
		String[] aPayline = strPayline.split(",");

		try {
			for (String strPos : aPayline) {
				int intPos = Integer.parseInt(strPos);
				paylinePositionList.add(intPos);
			}
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("couln not parse payline position from expression", nfe);
		}

	}

	/**
	 * Returns the alias of this payline.
	 * 
	 * @return the alias of this payline
	 */
	public String getPaylineAlias() {
		return paylineAlias;
	}

	/**
	 * Returns the positions of a payline on a window as an Iterable. The first
	 * position represents the number of line counting from the top of the game
	 * window, corresponding to the first reel from the left of the window; the
	 * second position corresponds to the second reel etc.
	 * 
	 * @return the positions of a payline on a window as an Iterable
	 */
	public Iterable<Integer> paylinePositions() {
		return paylinePositionList;
	}

	// debugging client
	public static void main(String[] args) {
		String expression1 = "payline 1: 3,3,3,3,3;";
		PaylineParser pp1 = new PaylineParser(expression1);

		System.out.println("expression: " + expression1);
		System.out.println("payline name: " + pp1.getPaylineAlias());
		System.out.print("payline positions: ");
		for (int paylinePosition : pp1.paylinePositions()) {
			System.out.print(paylinePosition + " ");
		}
		System.out.println("\n\n");

		String expression2 = "payline magicalPayline: 2,1,3,1,2;";
		PaylineParser pp2 = new PaylineParser(expression2);

		System.out.println("expression: " + expression2);
		System.out.println("payline name: " + pp2.getPaylineAlias());
		System.out.print("payline positions: ");
		for (int paylinePosition : pp2.paylinePositions()) {
			System.out.print(paylinePosition + " ");
		}

	}

}
