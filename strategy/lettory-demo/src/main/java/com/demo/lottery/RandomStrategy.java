package com.demo.lottery;



import com.demo.lottery.utils.AssertUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by brian.huang on 14-8-14.
 */
public class RandomStrategy {
    private int maxItem;
    private RandomStrategyEnum randomStrategyEnum;
    private ThreadLocalRandom random;
    private SortOrder sortOrder;

    public RandomStrategy(RandomStrategyEnum randomStrategyEnum) {
        this(randomStrategyEnum, 2);
    }

    public RandomStrategy(RandomStrategyEnum randomStrategyEnum, int maxItem) {
        this(randomStrategyEnum, maxItem, null, SortOrder.ASCENDING);
    }

    public RandomStrategy(RandomStrategyEnum randomStrategyEnum, int maxItem, ThreadLocalRandom random, SortOrder sortOrder) {
        this.randomStrategyEnum = randomStrategyEnum;
        this.random = random;
        this.sortOrder = sortOrder;
        switch (randomStrategyEnum) {
            case ONE_OR_NULL:
            case ALWAYS_ONE:
                this.maxItem = 1;
                break;
            default:
                AssertUtils.isTrue(maxItem > 1, "Random strategy maxItem must be >= 2.");
                this.maxItem = maxItem;
                break;
        }
    }

    public int getMaxItem() {
        return maxItem;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RandomStrategy{")
                .append("maxItem=").append(maxItem)
                .append(", randomStrategyEnum=").append(randomStrategyEnum)
                .append(", random=").append(random)
                .append(", sortOrder=").append(sortOrder)
                .append('}');
        return sb.toString();
    }

    public RandomStrategyEnum getRandomStrategyEnum() {
        return randomStrategyEnum;
    }

    public ThreadLocalRandom getRandom() {
        return random;
    }

    public void setRandom(ThreadLocalRandom random) {
        this.random = random;
    }

    public static enum SortOrder {
        /**
         * Order don't change
         */
        UNSORTED,
        /**
         * Descend items
         */
        DESCENDING,
        /**
         * Ascend items
         */
        ASCENDING,

        /**
         * Random order
         */
        RANDOM
    }

    public static enum RandomStrategyEnum {
        /**
         * random one or null
         */
        ONE_OR_NULL,
        /**
         * only one
         */
        ALWAYS_ONE,

        /**
         * random many items(gt 1 or null)
         */
        //MANY_ITEMS_OR_NULL,
        /**
         * gt 1
         */
        //GREATER_THAN_OR_EQUAL_ONE;
    }
}
