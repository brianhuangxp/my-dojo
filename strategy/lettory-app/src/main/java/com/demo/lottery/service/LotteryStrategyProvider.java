package com.demo.lottery.service;

import com.demo.lottery.domain.LotteryItem;
import com.demo.lottery.domain.LotteryRandomStrategy;
import com.demo.lottery.domain.annotation.LotteryRandom;
import com.demo.lottery.service.handler.AbstractRandomHandler;
import com.demo.lottery.service.handler.RandomMultiItemsHandler;
import com.demo.lottery.service.handler.RandomSingleItemHandler;
import com.demo.lottery.utils.AssertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LotteryStrategyProvider {
    @Inject
    AbstractRandomHandler randomSingleItemHandler ;
    @Inject
    AbstractRandomHandler randomMultiItemsHandler;

    private final Map<LotteryRandomStrategy, AbstractRandomHandler> handlerMap = new HashMap<>();
    /*static {
        ABSTRACT_RANDOM_HANDLER_MAP = new HashMap<>();
        ABSTRACT_RANDOM_HANDLER_MAP.put(LotteryRandomStrategy.SINGLE_ITEM, randomSingleItemHandler);
        ABSTRACT_RANDOM_HANDLER_MAP.put(LotteryRandomStrategy.MULTL_ITEMS, new RandomMultiItemsHandler());
    }*/

    @PostConstruct
    public void init() {
        handlerMap.put(LotteryRandomStrategy.SINGLE_ITEM, randomSingleItemHandler);
        handlerMap.put(LotteryRandomStrategy.MULTL_ITEMS, randomMultiItemsHandler);
    }

    private AbstractRandomHandler buildRandomHandler(LotteryRandom lotteryRandom) {
        return handlerMap.get(lotteryRandom.strategy());
    }

    public <T> T lotteryItem(List<LotteryItem> lotteryItems, LotteryRandom lotteryRandom) {
        AbstractRandomHandler abstractRandomHandler = buildRandomHandler(lotteryRandom);
        AssertUtils.notNull(abstractRandomHandler, "Random strategy doesn't support.");

        abstractRandomHandler.validate(lotteryItems);

        return abstractRandomHandler.lotteryItem(lotteryItems, lotteryRandom);
    }
}
