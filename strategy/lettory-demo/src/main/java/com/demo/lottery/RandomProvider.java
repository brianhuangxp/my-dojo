package com.demo.lottery;



import com.demo.lottery.handler.AbstractRandomHandler;
import com.demo.lottery.handler.RandomAlwaysOneHandler;
import com.demo.lottery.handler.RandomOneOrNullHandler;
import com.demo.lottery.utils.AssertUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by brian.huang on 14-8-14.
 */
public class RandomProvider {
    private static final Map<RandomStrategy.RandomStrategyEnum, AbstractRandomHandler> ABSTRACT_RANDOM_HANDLER_MAP;

    static {
        ABSTRACT_RANDOM_HANDLER_MAP = new HashMap<>();
        ABSTRACT_RANDOM_HANDLER_MAP.put(RandomStrategy.RandomStrategyEnum.ONE_OR_NULL, new RandomOneOrNullHandler());
        ABSTRACT_RANDOM_HANDLER_MAP.put(RandomStrategy.RandomStrategyEnum.ALWAYS_ONE, new RandomAlwaysOneHandler());
    }

    private ThreadLocalRandom random;

    public <T> T lotteryItem(List<LotteryItem> weightItems, RandomStrategy randomStrategy) {
        return lotteryItem(weightItems, randomStrategy, null);
    }

    private AbstractRandomHandler buildRandomHandler(RandomStrategy randomStrategy) {
        return ABSTRACT_RANDOM_HANDLER_MAP.get(randomStrategy.getRandomStrategyEnum());
    }

    public <T> T lotteryItem(List<LotteryItem> weightItems, RandomStrategy randomStrategy, Integer totalWeight) {
        AbstractRandomHandler abstractRandomHandler = buildRandomHandler(randomStrategy);
        AssertUtils.notNull(abstractRandomHandler, "Random strategy doesn't support.");

        abstractRandomHandler.validate(weightItems);
        List<LotteryItem> soredItems = abstractRandomHandler.sortItems(weightItems, randomStrategy);

        if (random != null) {
            randomStrategy.setRandom(random);
        }
        if (totalWeight != null) {
            return abstractRandomHandler.lotteryItem(soredItems, totalWeight, randomStrategy);
        } else {
            return abstractRandomHandler.lotteryItem(soredItems, randomStrategy);
        }
    }

    public void setRandom(ThreadLocalRandom random) {
        this.random = random;
    }
}
