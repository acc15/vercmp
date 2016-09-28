package com.github.rshift.vercmp;

import java.util.Comparator;

public class Tokens {

    public static abstract class TokenMatcher implements Comparator<String> {

        private final int priority;

        public TokenMatcher() {
            this(0);
        }

        public TokenMatcher(int priority) {
            this.priority = priority;
        }

        public abstract boolean matches(String str);

        public int compare(String o1, String o2) {
            return 0;
        }
    }

    public static TokenMatcher empty() {
        return new TokenMatcher() {
            @Override
            public boolean matches(String str) {
                return str.isEmpty();
            }
        };
    }

    public static TokenMatcher value(String val) {
        return new TokenMatcher(1) {
            @Override
            public boolean matches(String str) {
                return val.equals(str);
            }
        };
    }

    public static TokenMatcher other() {
        return new TokenMatcher(-1) {
            @Override
            public boolean matches(String str) {
                return true;
            }

            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
    }

    public static TokenMatcher numeric() {
        return new TokenMatcher() {
            @Override
            public boolean matches(String str) {
                try {
                    int val = Integer.parseInt(str);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
            }
        };
    }

    private static int findMatcher(String str, TokenMatcher[] matchers) {
        int found = -1;
        for (int i = 0; i < matchers.length; i++) {
            if (matchers[i].matches(str) && (found < 0 || matchers[found].priority < matchers[i].priority)) {
                found = i;
            }
        }
        return found;
    }

    public static Comparator<String> toComparator(TokenMatcher... matchers) {
        return (a, b) -> {
            int m1 = findMatcher(a, matchers);
            int m2 = findMatcher(b, matchers);
            int c = Integer.compare(m1, m2);
            return c == 0 ? matchers[m1].compare(a, b) : c;
        };
    }

}
