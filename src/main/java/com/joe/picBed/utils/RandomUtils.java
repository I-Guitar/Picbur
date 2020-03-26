package com.joe.picBed.utils;

import java.util.Random;

/**
 * Created by joe on 2020/3/26
 */
public class RandomUtils {

    private static final Random random = new Random();

    public static <T> T randomChoice(T[] array) {
        return array[random.nextInt(array.length)];
    }


}
