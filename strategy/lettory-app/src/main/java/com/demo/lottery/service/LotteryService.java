package com.demo.lottery.service;

import com.demo.lottery.domain.Item;
import com.demo.lottery.domain.annotation.LotteryRandom;
import com.demo.lottery.persistence.LotteryItemStore;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class LotteryService {
    @Inject
    LotteryItemStore lotteryItemStore;

    public void dropDoorItems() {
        Item item = lotteryItemStore.loadDoorDropItems();
        System.out.println(item);
    }

    public void dailyLottery() {
        Item item = lotteryItemStore.loadDailyLotteryItems();
        System.out.println(item);
    }

    public void loadInstanceItems() {
        List<Item> items = lotteryItemStore.loadInstanceItems();
        System.out.println(items);
    }

}
