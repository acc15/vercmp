package com.github.acc15.version;

import java.util.Comparator;

import static com.github.acc15.version.Splitters.*;
import static com.github.acc15.version.Tokens.*;

public class VersionComparator implements Comparator<String> {

    private final Level[] levels;

    public static Level splitBy(Splitter splitter) {
        return new Level(splitter);
    }

    public static class Level {
        private Splitter splitter;
        private Comparator<String> tokenComparator;

        public Level(Splitter splitter) {
            this.splitter = splitter;
            this.tokenComparator = Comparator.naturalOrder();
        }

        public Level orderBy(Tokens.TokenMatcher... matchers) {
            this.tokenComparator = Tokens.toComparator(matchers);
            return this;
        }
    }

    public VersionComparator(Level... levels) {
        this.levels = levels;
    }

    private static String emptyIfOutOfBounds(String[] arr, int idx) {
        return idx < arr.length ? arr[idx] : "";
    }

    private int compare(String o1, String o2, int level, Comparator<String> tokenComparator) {
        if (level >= levels.length) {
            return tokenComparator.compare(o1, o2);
        }

        Splitter splitter = levels[level].splitter;
        String[] t1 = splitter.split(o1), t2 = splitter.split(o2);

        int l = Math.max(t1.length, t2.length);
        if (l > 1 && !o1.isEmpty() && !o2.isEmpty()) {
            tokenComparator = levels[level].tokenComparator;
        }

        for (int i = 0; i < l; i++) {
            int cmp = compare(emptyIfOutOfBounds(t1, i), emptyIfOutOfBounds(t2, i), level + 1, tokenComparator);
            if (cmp != 0) {
                return cmp;
            }
        }
        return 0;
    }

    public static VersionComparator createDefault() {
        return new VersionComparator(
            splitBy(pattern("\\.")).orderBy(other(), numeric(), empty()),
            splitBy(pattern("\\-")).orderBy(other(), empty(), numeric()),
            splitBy(digits()).orderBy(empty(), other(), numeric())
        );
    }

    @Override
    public int compare(String o1, String o2) {
        return compare(o1, o2, 0, levels[0].tokenComparator);
    }

}
