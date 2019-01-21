
/******************************************************************************
 *  Compilation:  javac PaylineExpression2.java
 *  Execution:    java PaylineExpression2
 *  Dependencies: none
 *
 *  Stores a parsed payline expression.
 *
 ******************************************************************************/

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 
 * The {@code PaylineExpression2} class represents a parsed payline declaration
 * expression obtained from a {@code Matcher} instance containing a parsed
 * valid payline declaration expression from text input.
 * 
 * This class provides a method for obtaining the payline's name, a method that
 * returns the payline's reel coordinates as an Iterable and a method that
 * returns the payline's line coordinates as an Iterable.
 * 
 * The form of a valid payline declaration expression is:
 * 
 * <b>payline <i>paylineName</i>: <i>(line1,reel1),(line2,reel2),..,(lineN,reelN)</i>;</b>
 * 
 * i.e. payline payline1: (1,1),(2,1),(3,1);
 * 
 * There cannot be whitespace characters between a digit and a comma, or
 * between a digit and a parenthesis, or between a comma and a parenthesis or
 * between the payline's name and ":". But there can be one or more after the
 * "payline" keyword, after "paylineName:" and before ";" .
 * 
 * The {@code Matcher} instance's groups correspond to:
 *   - group(0): the whole expression
 *   - group(1): <i>payline name</i>
 *   - group(2): <i>(line1,reel1),(line2,reel2),..,(lineN,reelN)</i>
 * 
 * @author AccelSprinter
 *
 */
public class PaylineExpression2 implements PaylineExpression {

	// payline name
	private final String paylineName;
	// reel coordinates
	private final List<Integer> reelCoordinates;
	// line coordinates
	private final List<Integer> lineCoordinates;

	/**
	 * Instantiates a {@code PaylineExpression1} class from a {@code Matcher} instance.
	 * 
	 * @param matcher
	 *            the {@code Matcher} instance
	 */
	public PaylineExpression2(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		paylineName = matcher.group(1);
		reelCoordinates = new LinkedList<>();
		lineCoordinates = new LinkedList<>();

		String strCoordinates = matcher.group(2);
		strCoordinates = strCoordinates.trim();
		String[] aCoordinates = strCoordinates.split("\\),\\("); // split by '),('

		for (String strCoordinate : aCoordinates) {
			strCoordinate = strCoordinate.replaceAll("\\(", "");
			strCoordinate = strCoordinate.replaceAll("\\)", "");
			String[] aCoordinate = strCoordinate.split(",");
			int lineCoordinate = Integer.parseInt(aCoordinate[0]);
			int reelCoordinate = Integer.parseInt(aCoordinate[1]);
			lineCoordinates.add(lineCoordinate);
			reelCoordinates.add(reelCoordinate);
		}
	}

	/**
	 * Returns the payline's name.
	 * 
	 * @return the payline's name
	 */
	public String getPaylineName() {
		return paylineName;
	}

	/**
	 * Returns the payline's reel coordinates as an Iterable.
	 * 
	 * @return the payline's reel coordinates as an Iterable
	 */
	public Iterable<Integer> reelCoordinates() {
		return reelCoordinates;
	}

	/**
	 * Returns the payline's line coordinates as an Iterable.
	 * 
	 * @return the payline's line coordinates as an Iterable
	 */
	public Iterable<Integer> lineCoordinates() {
		return lineCoordinates;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(paylineName);
		sb.append(" : ");
		
		ArrayList<Integer> aLineCoords = new ArrayList<>();
		for (int lineCoordinate : lineCoordinates) {
			aLineCoords.add(lineCoordinate); 
		}
			
		ArrayList<Integer> aReelCoords = new ArrayList<>();
		for (int reelCoordinate : reelCoordinates) {
			aReelCoords.add(reelCoordinate); 
		}
		
		String separator = "";
		for (int i = 0, end = aLineCoords.size(); i < end; ++i) {
			sb.append(separator);
			sb.append("(");
			sb.append(aReelCoords.get(i));
			sb.append(",");
			sb.append(aLineCoords.get(i));
			sb.append(")");
			separator = ",";
		}
		return sb.toString();
	}

	// client
	public static void main(String[] args) {

	}
}
