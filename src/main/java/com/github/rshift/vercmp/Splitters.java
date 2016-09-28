package com.github.rshift.vercmp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Splitters {

    public static Splitter pattern(String pattern) {
        return Pattern.compile(pattern)::split;
    }

    public static Splitter digits() {
        return str -> {
            boolean wasDigit = false;
            List<String> tokens = new ArrayList<>();
            StringBuilder token = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                boolean isDigit = Character.isDigit(ch);
                if (i > 0 && isDigit != wasDigit) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                token.append(ch);
                wasDigit = isDigit;
            }
            if (str.isEmpty() || token.length() > 0) {
                tokens.add(token.toString());
            }
            return tokens.toArray(new String[tokens.size()]);
        };
    }

}
