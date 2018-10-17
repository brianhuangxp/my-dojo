package com.demo.lotery;

import com.demo.lottery.LotteryItem;
import com.demo.lottery.RandomProvider;
import com.demo.lottery.RandomStrategy;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.Mockito.when;

/**
 * Created by brian.huang on 14-8-14.
 */
@RunWith(MockitoJUnitRunner.class)
public class RandomProviderTest {
    @Mock
    private ThreadLocalRandom random;

    @InjectMocks
    private RandomProvider randomProvider;

    @Test
    public void testLotteryOne() throws Exception {
        when(random.nextInt(0, 35)).thenReturn(11);
        List<LotteryItem> lotteryItems = new ArrayList<>();
        lotteryItems.add(new LotteryItem<Integer>(1, 5));
        lotteryItems.add(new LotteryItem<Integer>(2, 6));
        lotteryItems.add(new LotteryItem<Integer>(3, 7));
        lotteryItems.add(new LotteryItem<Integer>(4, 8));
        lotteryItems.add(new LotteryItem<Integer>(5, 9));
        LotteryItem<Integer> lotteryItem = randomProvider.lotteryItem(lotteryItems,
                new RandomStrategy(RandomStrategy.RandomStrategyEnum.ONE_OR_NULL));
        Assert.assertNotNull(lotteryItem);
        Assert.assertTrue(lotteryItem.getItemKey() == 3);
        Assert.assertTrue(lotteryItem.getWeight() == 7);
    }

    @Test
    public void testLotteryOneNull() throws Exception {
        when(random.nextInt(0, 100)).thenReturn(99);
        List<LotteryItem> lotteryItems = new ArrayList<>();
        lotteryItems.add(new LotteryItem<Integer>(1, 5));
        lotteryItems.add(new LotteryItem<Integer>(2, 6));
        lotteryItems.add(new LotteryItem<Integer>(3, 7));
        lotteryItems.add(new LotteryItem<Integer>(4, 8));
        LotteryItem<Integer> lotteryItem = randomProvider.lotteryItem(lotteryItems,
                new RandomStrategy(RandomStrategy.RandomStrategyEnum.ONE_OR_NULL), 100);
        Assert.assertNull(lotteryItem);
    }

    @Test
    public void testLotteryAwaysOne() throws Exception {
        when(random.nextInt(0, 100)).thenReturn(11);
        List<LotteryItem> lotteryItems = new ArrayList<>();
        lotteryItems.add(new LotteryItem<Integer>(1, 5));
        lotteryItems.add(new LotteryItem<Integer>(2, 6));
        lotteryItems.add(new LotteryItem<Integer>(3, 7));
        lotteryItems.add(new LotteryItem<Integer>(4, 8));
        lotteryItems.add(new LotteryItem<Integer>(5, 9));
        LotteryItem<Integer> lotteryItem = randomProvider.lotteryItem(lotteryItems,
                new RandomStrategy(RandomStrategy.RandomStrategyEnum.ALWAYS_ONE), 100);
        Assert.assertNotNull(lotteryItem);
        Assert.assertTrue(lotteryItem.getItemKey() == 3);
        Assert.assertTrue(lotteryItem.getWeight() == 7);
    }

    @Test
    public void testLotteryAwaysOneWithDefault() throws Exception {
        when(random.nextInt(0, 100)).thenReturn(99);
        List<LotteryItem> lotteryItems = new ArrayList<>();
        lotteryItems.add(new LotteryItem<Integer>(1, 5));
        lotteryItems.add(new LotteryItem<Integer>(2, 6));
        lotteryItems.add(new LotteryItem<Integer>(3, 7));
        lotteryItems.add(new LotteryItem<Integer>(4, 8));
        lotteryItems.add(new LotteryItem<Integer>(5, 9));
        LotteryItem<Integer> lotteryItem = randomProvider.lotteryItem(lotteryItems,
                new RandomStrategy(RandomStrategy.RandomStrategyEnum.ALWAYS_ONE), 100);
        Assert.assertNotNull(lotteryItem);
        Assert.assertTrue(lotteryItem.getItemKey() == 5);
        Assert.assertTrue(lotteryItem.getWeight() == 9);
    }
}
