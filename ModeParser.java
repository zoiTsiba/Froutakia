package com.company;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModeParser {
    // name
    private static final String MODE_PATTERN = "game\\s*[{](bonus|base)[}]\\s*;"; // pattern for the mode declaration expression

    private final String mode; // game mode name

    /**
     * Instantiates a {@code ModeParser} class from an expression.
     *
     * @param expression the mode declaration expression
     */

    public ModeParser(String expression) {

        if (expression == null)
            throw new IllegalArgumentException("argument to constructor is null");

        Pattern pattern = Pattern.compile(MODE_PATTERN);
        Matcher matcher = pattern.matcher(expression);

        if (!matcher.matches())
            throw new IllegalArgumentException(expression + " is not a valid game mode expression");

        String strMode = matcher.group(1);
        mode = strMode;
    }

    /**
     * Returns the mode string.
     *
     * @return the mode string.
     */
    public String mode() {
        return mode;
    }

    // client
    public static void main(String[] args) {
        String expression = "game {bonus};";
        com.company.ModeParser mo = new com.company.ModeParser(expression);

        System.out.println(mo.mode());

    }
}
