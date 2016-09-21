package com.github.acc15.version;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Version {

    private static final Pattern NUMERIC_SPLIT_PATTERN = Pattern.compile("(\\d+)");

    public static class Comparator implements java.util.Comparator<String> {

        private String[] separators;

        public Comparator(String... separators) {
            this.separators = separators;
        }

        @Override
        public int compare(String o1, String o2) {
            return Version.compare(o1, o2, separators, 0);
        }
    }

    public static final Comparator DEFAULT_COMPARATOR = new Comparator("\\.", "\\-");


    public static int compare(String left, String right, String[] separators, int start) {
        if (start < separators.length) {
            return compareSeparated(left, right, separators, start);
        }

        if (left.isEmpty()) {
            return right.isEmpty() ? 0 : 1;
        } else if (right.isEmpty()) {
            return -1;
        }

        String[] leftGroups = splitByNumeric(left), rightGroups = splitByNumeric(right);
        if (leftGroups.length > 1 || rightGroups.length > 1) {
            return compareGroups(leftGroups, rightGroups);
        }
        return compareTokens(left, right);
    }

    private static String emptyIfOutOfBounds(String[] arr, int idx) {
        return idx < arr.length ? arr[idx] : "";
    }

    private static int compareSeparated(String left, String right, String[] separators, int start) {
        String sep = separators[start];
        String[] leftTokens = left.split(sep);
        String[] rightTokens = right.split(sep);
        for (int i = 0; i < Math.max(leftTokens.length, rightTokens.length); i++) {
            int result = compare(emptyIfOutOfBounds(leftTokens, i), emptyIfOutOfBounds(rightTokens, i), separators, start + 1);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    private static int compareGroups(String[] left, String[] right) {
        for (int i = 0; i < Math.max(left.length, right.length); i++) {
            int result = compareTokens(emptyIfOutOfBounds(left, i), emptyIfOutOfBounds(right, i));
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    private static int compareTokens(String left, String right) {

        // any number > any string (if both are numbers then compare as ints)
        try {
            int x = Integer.parseInt(left);
            try {
                int y = Integer.parseInt(right);
                return Integer.compare(x, y);
            } catch (NumberFormatException e) {
                return 1;
            }
        } catch (NumberFormatException e) {
            try {
                Integer.parseInt(right);
                return -1;
            } catch (NumberFormatException ignored) {
            }
        }

        // empty string > any other string (if emptyWins is true)
        return left.toLowerCase().compareTo(right.toLowerCase());
    }

    static String[] splitByNumeric(String token) {
        boolean wasDigit = false;
        int lastPos = 0;

        List<String> groups = new ArrayList<>();
        for (int i = 0; i < token.length(); i++) {
            char ch = token.charAt(i);
            boolean digit = Character.isDigit(ch);
            if (digit == wasDigit) {
                continue;
            }
            if (lastPos != i) {
                groups.add(token.substring(lastPos, i));
            }
            lastPos = i;
            wasDigit = digit;
        }
        groups.add(token.substring(lastPos));
        return groups.toArray(new String[groups.size()]);
    }

}
