package com.demo.lottery.service.handler;


import com.demo.lottery.domain.LotteryItem;
import com.demo.lottery.domain.annotation.LotteryRandom;
import com.demo.lottery.utils.AssertUtils;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractRandomHandler {

    protected ThreadLocalRandom random;
    public abstract <T> T lotteryItem(final List<LotteryItem> weightItems, LotteryRandom lotteryRandom);

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

    public void setRandom(ThreadLocalRandom random) {
        this.random = random;
    }
}
