package com.demo.lottery.utils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by brian.huang on 14-8-14.
 */
public class RandomUtils {
    private static final ThreadLocalRandom DEFAULT_RANDOM = ThreadLocalRandom.current();

    /**
     * random lottery
     *
     * @param lotteryRate: lottery rate
     * @param random
     * @return
     */
    public static boolean lottery(double lotteryRate, ThreadLocalRandom random) {
        float randomFloat = nextFloat(random);
        return lottery(randomFloat, lotteryRate);
    }

    public static boolean lottery(double lotteryRate) {
        return lottery(lotteryRate, DEFAULT_RANDOM);
    }

    public static boolean lottery(double lotteryRate, double randomValue) {
        return randomValue + lotteryRate > 1;
    }

    public static void shuffle(List list) {
        shuffle(list, null);
    }

    /**
     * Shuffle items from list.
     *
     * @param list
     * @param random
     */
    public static void shuffle(List list, ThreadLocalRandom random) {
        ThreadLocalRandom targetRandom = getDefaultRandom(random);
        Collections.shuffle(list, targetRandom);
    }

    /**
     * lottery item from list.
     *
     * @param list
     * @param random
     * @param <T>
     * @return list
     */
    public static <T> T lotteryItemFromList(List<?> list, ThreadLocalRandom random) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            return (T) list.get(0);
        }

        int randomIdx = RandomUtils.nextInt(0, list.size(), random);
        return (T) list.get(randomIdx);
    }

    public static <T> T lotteryItemFromList(List<?> list) {
        return lotteryItemFromList(list, null);
    }

    public static float nextFloat(ThreadLocalRandom random) {
        ThreadLocalRandom targetRandom = getDefaultRandom(random);
        return targetRandom.nextFloat();
    }

    public static float nextFloat() {
        return nextFloat(null);
    }


    public static int nextInt(int least, int bound, ThreadLocalRandom random) {
        ThreadLocalRandom targetRandom = getDefaultRandom(random);
        return targetRandom.nextInt(least, bound);
    }

    public static int nextInt(int least, int bound) {
        return nextInt(least, bound, null);
    }

    public static long nextLong(long least, long bound, ThreadLocalRandom random) {
        ThreadLocalRandom targetRandom = getDefaultRandom(random);
        return targetRandom.nextLong(least, bound);
    }

    public static long nextLong(long least, long bound) {
        return nextLong(least, bound, null);
    }

    public static double nextDouble(double least, double bound, ThreadLocalRandom random) {
        ThreadLocalRandom targetRandom = getDefaultRandom(random);
        return targetRandom.nextDouble(least, bound);
    }

    public static double nextDouble(double least, double bound) {
        return nextDouble(least, bound, null);
    }

    private static ThreadLocalRandom getDefaultRandom(ThreadLocalRandom random) {
        return random == null ? DEFAULT_RANDOM : random;
    }
}
