package com.demo.lottery.handler;


import com.demo.lottery.LotteryItem;
import com.demo.lottery.RandomStrategy;

import java.util.List;

/**
 * Created by brian.huang on 14-8-14.
 */
public class RandomAlwaysOneHandler extends RandomOneOrNullHandler {
    @Override
    public <T> T lotteryItem(final List<LotteryItem> weightItems, Integer totalWeight, RandomStrategy randomStrategy) {
        LotteryItem item = super.lotteryItem(weightItems, totalWeight, randomStrategy);
        if (item == null) {
            List<LotteryItem> sortItems = sortItems(weightItems, new RandomStrategy(RandomStrategy.RandomStrategyEnum.ALWAYS_ONE));
            item = sortItems.get(sortItems.size() - 1);
        }
        return (T) item;
    }
}
