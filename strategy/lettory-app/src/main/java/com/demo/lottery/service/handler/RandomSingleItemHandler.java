package com.demo.lottery.service.handler;

import com.demo.lottery.domain.Item;
import com.demo.lottery.domain.LotteryItem;
import com.demo.lottery.domain.annotation.LotteryRandom;
import com.demo.lottery.utils.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RandomSingleItemHandler extends AbstractRandomHandler {

    @Override
    public <T> T lotteryItem(List<LotteryItem> lotteryItems, LotteryRandom lotteryRandom) {
        int totalWeight = getTotalWeight(lotteryItems);
        int value = RandomUtils.nextInt(0, totalWeight, random);
        Item result = null;

        for (LotteryItem lottery : lotteryItems) {
            value -= lottery.getWeight();
            if (value < 0) {
                result = buildItem(lotteryRandom, lottery);
                break;
            }
        }

        return (T) result;
    }

    private Item buildItem(LotteryRandom lotteryRandom, LotteryItem lottery) {
        Item result;
        if(lotteryRandom.randomAmount()) {
            int amount = RandomUtils.nextInt(0, lottery.getMaxAmount(), random) + 1;
            result = new Item(lottery, amount);
        } else {
            result = new Item(lottery, 1);
        }
        return result;
    }
}
