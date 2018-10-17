package com.demo.lottery.domain;


import com.demo.lottery.utils.RandomUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.inject.Inject;

/**
 * Created by brian.huang on 14-8-14.
 */
@Data
@EqualsAndHashCode(of={"itemKey"})
public class LotteryItem implements Comparable<LotteryItem> {
    protected Integer itemKey;
    protected String label;
    protected Integer weight;
    protected Integer maxAmount;

    public LotteryItem(Integer itemKey, String label, Integer weight) {
        this(itemKey, label, weight, null);
    }

    public LotteryItem(Integer itemKey, String label, Integer weight, Integer maxAmount) {
        this.itemKey = itemKey;
        this.label = label;
        this.weight = weight;

        if(maxAmount == null) {
            maxAmount = 1;
        }
        this.maxAmount = maxAmount;
    }

    @Override
    public int compareTo(LotteryItem o) {
        if (weight < o.getWeight()) {
            return -1;
        } else if (weight > o.getWeight()) {
            return 1;
        } else {
            return RandomUtils.nextFloat() >= 0.5 ? 1 : -1; // if the weights are same, random them.
        }
    }
}
