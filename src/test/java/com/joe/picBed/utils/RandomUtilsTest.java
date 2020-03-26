package com.joe.picBed.utils;

import org.junit.Test;

public class RandomUtilsTest {

    @Test
    public void randomArray() {
        String[] array = {"0", "1", "2"};

        final String result = RandomUtils.randomChoice(array);
        System.out.println();
    }

    @Test
    public void split() {
        String test = "a;s";
        final String[] result = test.split(";");
        System.out.println();
    }

}