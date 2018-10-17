package com.demo.lotery.handler;

import com.demo.lottery.LotteryItem;
import com.demo.lottery.RandomStrategy;
import com.demo.lottery.handler.AbstractRandomHandler;
import com.demo.lottery.handler.RandomOneOrNullHandler;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brian.huang on 14-8-14.
 */
public class AbstractRandomHandlerTest {
    @Test
    public void testUnSortItems() {
        AbstractRandomHandler handler = new RandomOneOrNullHandler();
        List<LotteryItem> lotteryItems = new ArrayList<>();
        lotteryItems.add(new LotteryItem<Integer>(7, 12));
        lotteryItems.add(new LotteryItem<Integer>(1, 2));
        lotteryItems.add(new LotteryItem<Integer>(3, 5));
        lotteryItems.add(new LotteryItem<Integer>(5, 10));
        lotteryItems.add(new LotteryItem<Integer>(6, 10));
        lotteryItems.add(new LotteryItem<Integer>(2, 10));
        lotteryItems.add(new LotteryItem<Integer>(4, 4));

        List<LotteryItem> sortItems = handler.sortItems(lotteryItems,
                new RandomStrategy(RandomStrategy.RandomStrategyEnum.ALWAYS_ONE, 1, null, RandomStrategy.SortOrder.UNSORTED));
        Assert.assertArrayEquals(sortItems.toArray(), lotteryItems.toArray());

    }

    @Test
    public void testAESSortItems() {
        AbstractRandomHandler handler = new RandomOneOrNullHandler();
        List<LotteryItem> lotteryItems = new ArrayList<>();
        LotteryItem first = new LotteryItem<Integer>(7, 1);
        LotteryItem last = new LotteryItem<Integer>(4, 11);
        lotteryItems.add(new LotteryItem<Integer>(10, 2));
        lotteryItems.add(first);
        lotteryItems.add(last);
        lotteryItems.add(new LotteryItem<Integer>(1, 2));
        lotteryItems.add(new LotteryItem<Integer>(3, 5));
        lotteryItems.add(new LotteryItem<Integer>(5, 10));
        lotteryItems.add(new LotteryItem<Integer>(6, 10));
        lotteryItems.add(new LotteryItem<Integer>(2, 10));

        List<LotteryItem> sortItems = handler.sortItems(lotteryItems,
                new RandomStrategy(RandomStrategy.RandomStrategyEnum.ALWAYS_ONE, 1, null, RandomStrategy.SortOrder.ASCENDING));
        Assert.assertEquals(sortItems.get(0), first);
        Assert.assertEquals(sortItems.get(sortItems.size() - 1), last);

    }

    @Test
    public void testDESCSortItems() {
        AbstractRandomHandler handler = new RandomOneOrNullHandler();
        List<LotteryItem> lotteryItems = new ArrayList<>();
        LotteryItem last = new LotteryItem<Integer>(7, 1);
        LotteryItem first = new LotteryItem<Integer>(4, 11);
        lotteryItems.add(new LotteryItem<Integer>(10, 2));
        lotteryItems.add(first);
        lotteryItems.add(last);
        lotteryItems.add(new LotteryItem<Integer>(1, 2));
        lotteryItems.add(new LotteryItem<Integer>(3, 5));
        lotteryItems.add(new LotteryItem<Integer>(5, 10));
        lotteryItems.add(new LotteryItem<Integer>(6, 10));
        lotteryItems.add(new LotteryItem<Integer>(2, 10));

        List<LotteryItem> sortItems = handler.sortItems(lotteryItems,
                new RandomStrategy(RandomStrategy.RandomStrategyEnum.ALWAYS_ONE, 1, null, RandomStrategy.SortOrder.DESCENDING));
        Assert.assertEquals(sortItems.get(0), first);
        Assert.assertEquals(sortItems.get(sortItems.size() - 1), last);
    }

    @Test
    public void testRandomSortItems() {
        AbstractRandomHandler handler = new RandomOneOrNullHandler();
        List<LotteryItem> lotteryItems = new ArrayList<>();
        lotteryItems.add(new LotteryItem<Integer>(0, 1));
        lotteryItems.add(new LotteryItem<Integer>(1, 2));
        lotteryItems.add(new LotteryItem<Integer>(2, 3));
        lotteryItems.add(new LotteryItem<Integer>(3, 4));


        List<LotteryItem> randomItems = handler.sortItems(lotteryItems,
                new RandomStrategy(RandomStrategy.RandomStrategyEnum.ALWAYS_ONE, 1, null, RandomStrategy.SortOrder.RANDOM));
        Assert.assertTrue(randomItems.size() == 4);


        boolean sameOrder = true;
        for (int i = 0; i < lotteryItems.size(); i++) {
            LotteryItem randomItem = randomItems.get(i);
            LotteryItem item = lotteryItems.get(i);
            if (!randomItem.equals(item)) {
                sameOrder = false;
            }
            Assert.assertTrue(lotteryItems.contains(randomItem));
        }

        Assert.assertFalse(sameOrder);
    }
}
