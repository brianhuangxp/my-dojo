package com.demo.lottery.persistence;

import com.demo.lottery.domain.LotteryItem;
import com.demo.lottery.domain.LotteryRandomStrategy;
import com.demo.lottery.domain.annotation.LotteryRandom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class LotteryItemStore {
    @LotteryRandom(randomAmount = false)
    public <T> T loadDailyLotteryItems() {
        return (T) new ArrayList<>(Arrays.asList(new LotteryItem(1, "豪华一日游", 1),
                new LotteryItem(2, "项链", 4),
                new LotteryItem(3, "戒指", 3),
                new LotteryItem(4, "宝石", 2)));
    }

    @LotteryRandom
    public <T> T loadDoorDropItems() {
        return (T) new ArrayList<>(Arrays.asList(new LotteryItem(1, "金币", 10, 500),
                new LotteryItem(2, "匕首", 4),
                new LotteryItem(3, "木材", 5, 100),
                new LotteryItem(4, "宝石", 1, 5),
                new LotteryItem(null, "空", 10)
        ));
    }

    @LotteryRandom(strategy = LotteryRandomStrategy.MULTL_ITEMS, minMultiItems = 2, maxMultiItems = 4)
    public <T> T loadInstanceItems() {
        return (T) new ArrayList<>(Arrays.asList(new LotteryItem(1, "金币", 10, 500),
                new LotteryItem(2, "匕首", 4),
                new LotteryItem(3, "木材", 5, 100),
                new LotteryItem(4, "宝石", 1, 5),
                new LotteryItem(5, "石材", 5, 100),
                new LotteryItem(null, "空", 10)
        ));
    }
}
