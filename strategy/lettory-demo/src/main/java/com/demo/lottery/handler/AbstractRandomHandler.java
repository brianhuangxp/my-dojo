package com.demo.lottery.handler;


import com.demo.lottery.LotteryItem;
import com.demo.lottery.RandomStrategy;
import com.demo.lottery.utils.AssertUtils;
import com.demo.lottery.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by brian.huang on 14-8-14.
 */
public abstract class AbstractRandomHandler {
    public <T> T lotteryItem(final List<LotteryItem> weightItems, RandomStrategy randomStrategy) {
        int totalWeight = getTotalWeight(weightItems);
        return lotteryItem(weightItems, totalWeight, randomStrategy);
    }

    public abstract <T> T lotteryItem(final List<LotteryItem> weightItems, Integer totalWeight, RandomStrategy randomStrategy);

    public List<LotteryItem> sortItems(final List<LotteryItem> weightItems, RandomStrategy randomStrategy) {
        List<LotteryItem> targetList = new ArrayList<>(weightItems);
        switch (randomStrategy.getSortOrder()) {
            case ASCENDING:
                Collections.sort(targetList);
                break;
            case DESCENDING:
                Collections.sort(targetList);
                Collections.reverse(targetList);
                break;
            case RANDOM:
                RandomUtils.shuffle(targetList, randomStrategy.getRandom());
                break;
            default:
        }

        return targetList;
    }

    public void validate(List<LotteryItem> weightItems) {
        AssertUtils.notEmpty(weightItems);
    }

    protected int getTotalWeight(List<LotteryItem> weightItems) {
        int totalWeight = 0;
        for (LotteryItem weightItem : weightItems) {
            totalWeight += weightItem.getWeight();
        }
        return totalWeight;
    }
}
