package com.demo.lottery.service.handler;

import com.demo.lottery.domain.LotteryItem;
import com.demo.lottery.domain.annotation.LotteryRandom;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class RandomMultiItemsHandler extends AbstractRandomHandler {

    @Override
    public <T> T lotteryItem(List<LotteryItem> lotteryItems, LotteryRandom lotteryRandom) {
        System.out.println("todo");
        //todo
        return null;
    }
}
