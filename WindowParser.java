package com.company;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code WindowParser} class parses a complete "window declaration"
 * expression and provides a method that returns the window as an Iterable.
 *
 * The form of a valid "window declaration" expression is:
 *
 * window 5x5;
 *
 * Valid characters for the window' values are:
 *
 * 0-oo
 *
 * There cannot be whitespace characters between a window number and an "x", but
 * there can be one or more after the "window" keyword and before ";" .
 *
 * @author AccelSprinter
 *
 */

public class WindowParser {

        // name
        private static final String WINDOW_PATTERN = "window\\s+([0-9]+(x[0-9]+)*)\\s*"; // pattern for the window declaration expression

        private final LinkedList<String> windowList; // window list

        /**
         * Instantiates a {@code WindowParser} class from an expression.
         *
         * @param expression
         *            the window declaration expression
         */

        public WindowParser(String expression) {

            if (expression == null)
                throw new IllegalArgumentException("argument to constructor is null");

            Pattern pattern = Pattern.compile(WINDOW_PATTERN);
            Matcher matcher = pattern.matcher(expression);

            if (!matcher.matches())
                throw new IllegalArgumentException(expression + " is not a valid window expression");

            String strWindow = matcher.group(1);
            strWindow = strWindow.trim();
            String[] aWindow = strWindow.split("x");

            windowList = new LinkedList<>();
            for (String aStrWindow : aWindow) {
                windowList.add(aStrWindow);
            }
        }

        /**
         * Returns the window as an Iterable.
         *
         * @return the window as an Iterable
         */
        public Iterable<String> window() {
            return windowList;
        }

        // client
        public static void main(String[] args) {
            String expression = "window 4x4";
            com.company.WindowParser wd = new com.company.WindowParser(expression);

            for (String w : wd.window())
                System.out.println(w);

        }



}
