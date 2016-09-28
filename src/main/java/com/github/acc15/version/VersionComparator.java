package com.github.acc15.version;

import java.util.Comparator;

public class VersionComparator implements Comparator<String> {

    private final Splitter[] splitters;
    private final Comparator<String> tokenComparator;

    public VersionComparator(Comparator<String> tokenComparator, Splitter... splitters) {
        this.splitters = splitters;
        this.tokenComparator = tokenComparator;
    }

    private static String emptyIfOutOfBounds(String[] arr, int idx) {
        return idx < arr.length ? arr[idx] : "";
    }

    private int compare(String o1, String o2, int level) {
        if (level >= splitters.length) {
            return tokenComparator.compare(o1, o2);
        }

        Splitter splitter = splitters[level];
        String[] t1 = splitter.split(o1), t2 = splitter.split(o2);

        int l = Math.max(t1.length, t2.length);
        for (int i = 0; i < l; i++) {
            int cmp = compare(emptyIfOutOfBounds(t1, i), emptyIfOutOfBounds(t2, i), level + 1);
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }

    @Override
    public int compare(String o1, String o2) {
        return compare(o1, o2, 0);
    }

    public static class Builder {
        private Splitter[] splitters;
        public Builder(Splitter[] splitters) {
            this.splitters = splitters;
        }

        public VersionComparator orderBy(Tokens.TokenMatcher... matchers) {
            return new VersionComparator(Tokens.toComparator(matchers), splitters);
        }
    }

    public static Builder splitBy(Splitter... others) {
        return new Builder(others);
    }

}
