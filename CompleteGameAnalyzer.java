import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompleteGameAnalyzer {
	
	private final static String BASE_GAME_REGEX = "(?s).*BASE_GAME;(.*?)(BASE_GAME|BONUS_GAME|$)";
	private final static String BONUS_GAME_REGEX = "(?s).*BONUS_GAME;(.*?)(BASE_GAME|BONUS_GAME|$)";
	
	private final Manager baseManager;
	private final Manager bonusManager;
	private final FCS baseFCS;
	private final FCS bonusFCS;
	
	public CompleteGameAnalyzer(String data) {
		assert data != null;

		Pattern pattern;
		Matcher matcher;
		
		// parse the base game
		pattern  = Pattern.compile(BASE_GAME_REGEX);
		matcher = pattern.matcher(data);
		if (!matcher.find())
			throw new IllegalArgumentException("No base game data to parse");
		baseManager = new Manager(matcher.group(1));
		baseFCS = new FCS(baseManager);
		
		// parse the bonus game
		pattern  = Pattern.compile(BONUS_GAME_REGEX);
		matcher = pattern.matcher(data);
		if (!matcher.find())
			throw new IllegalArgumentException("No bonus game data to parse");
		bonusManager = new Manager(matcher.group(1));
		bonusFCS = new FCS(bonusManager);
	}
	
	public static void main(String[] args) {
		System.out.println("HI");
		final In in = new In("IO/base_bonus.txt");
		final CompleteGameAnalyzer cga = new CompleteGameAnalyzer(in.readAll());
	}
}
