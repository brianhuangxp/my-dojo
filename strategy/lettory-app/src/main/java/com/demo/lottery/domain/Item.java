package com.demo.lottery.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    private Integer amount;
    private LotteryItem lotteryItem;

    public Item(LotteryItem lotteryItem, Integer amount) {
        this.lotteryItem = lotteryItem;
        this.amount = amount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("物品：").append(lotteryItem.label)
                .append(", ")
                .append(amount).append("件");
        return sb.toString();
    }
}
