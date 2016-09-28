package com.github.acc15.version;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.testng.Assert.*;

public class VersionComparatorTest {
    @Test
    public void testCompare() throws Exception {

        Comparator<String> cmp = VersionComparator.createDefault();

        List<String> versions = Arrays.asList(
            "5.0.43.21",
            "5.0.32.34",
            "5.0.32-alpha.34",
            "6.42b7-rc",
            "5.0.32-beta.34",
            "5.0.32-rc.34",
            "5.0.32-1.34",
            "6.42",
            "6.42b10",
            "5.0.43.21-SNAPSHOT",
            "6.42b7",
            "6.42b",
            "1.00-rc2",
            "1.00",
            "1.00-rc1",
            "6.42b7-rc2",
            "1.00-rc");

        versions.sort(cmp);
        versions.forEach(System.out::println);
        assertEquals(versions, Arrays.asList(
            "1.00-rc",
            "1.00-rc1",
            "1.00-rc2",
            "1.00",
            "5.0.32-alpha.34",
            "5.0.32-beta.34",
            "5.0.32-rc.34",
            "5.0.32.34",
            "5.0.32-1.34",
            "5.0.43.21-SNAPSHOT",
            "5.0.43.21",
            "6.42",
            "6.42b",
            "6.42b7-rc",
            "6.42b7-rc2",
            "6.42b7",
            "6.42b10"
        ));

    }

}