package com.demo.lottery.handler;


import com.demo.lottery.LotteryItem;
import com.demo.lottery.RandomStrategy;
import com.demo.lottery.utils.RandomUtils;

import java.util.List;

/**
 * Created by brian.huang on 14-8-14.
 */
public class RandomOneOrNullHandler extends AbstractRandomHandler {
    @Override
    public <T> T lotteryItem(final List<LotteryItem> weightItems, Integer totalWeight, RandomStrategy randomStrategy) {
        int value = RandomUtils.nextInt(0, totalWeight, randomStrategy.getRandom());
        LotteryItem result = null;

        for (LotteryItem weightItem : weightItems) {
            value -= weightItem.getWeight();
            if (value < 0) {
                result = weightItem;
                break;
            }
        }

        return (T) result;
    }
}
