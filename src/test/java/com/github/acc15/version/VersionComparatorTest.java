package com.github.acc15.version;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.*;

public class VersionComparatorTest {
    @Test
    public void testCompare() throws Exception {

        Comparator<String> cmp = VersionComparator.splitBy(
            Splitters.pattern("\\."),
            Splitters.pattern("\\-")
        ).orderBy(
            Tokens.other(),
            Tokens.numeric(),
            Tokens.empty()
        );

        List<String> versions = Arrays.asList(
            "5.0.43.21",
            "5.0.43.21-SNAPSHOT",
            "5.0.32.34");

        versions.sort(cmp);
        versions.forEach(System.out::println);

        assertEquals(versions, Arrays.asList(
            "5.0.32.34",
            "5.0.43.21-SNAPSHOT",
            "5.0.43.21"
        ));

    }

}