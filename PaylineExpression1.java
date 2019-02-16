
/******************************************************************************
 *  Compilation:  javac PaylineExpression1.java
 *  Execution:    java PaylineExpression1
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
 * The {@code PaylineExpression1} class represents a parsed payline declaration
 * expression obtained from a {@code Matcher} instance containing a parsed
 * valid payline declaration expression from text input.
 * 
 * This class provides a method for obtaining the payline's name, a method that
 * returns the payline's reel coordinates as an Iterable and a method that
 * returns the payline's line coordinates as an Iterable.
 * 
 * The syntax of the valid payline declaration expression is:
 * 
 * <b>payline <i>paylineName</i>: <i>lineOfReel1,lineOfReel2,..,lineOfReelN</i>;</b>
 * 
 * i.e. payline payline1: 2,2,2,2,2;
 *  
 * There cannot be whitespace characters between a digit and a comma, or
 * between the payline's name and ":". But there can be one or more after the
 * "payline" keyword, after "paylineName:" and before ";" . 
 * 
 * The {@code Matcher} instance's groups correspond to:
 *   - group(0): the whole expression
 *   - group(1): <i>payline name</i>
 *   - group(2): <i>lineOfReel1,lineOfReel2,..,lineOfReelN</i>
 * 
 * @author AccelSprinter
 *
 */
public class PaylineExpression1 implements PaylineExpression {

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
	public PaylineExpression1(Matcher matcher) {
		if (matcher == null)
			throw new IllegalArgumentException("argument to constructor is null");

		paylineName = matcher.group(1);
		reelCoordinates = new LinkedList<>();
		lineCoordinates = new LinkedList<>();

		String strReelCoords = matcher.group(2);
		strReelCoords = strReelCoords.trim();
		String[] aReelCoords = strReelCoords.split(",");

		int i = 1;
		for (String strReelCoord : aReelCoords) {
			int reelCoord = Integer.parseInt(strReelCoord);
			lineCoordinates.add(reelCoord);
			reelCoordinates.add(i++);
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
