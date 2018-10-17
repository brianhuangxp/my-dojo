package com.demo.lottery;


import com.demo.lottery.utils.RandomUtils;

/**
 * Created by brian.huang on 14-8-14.
 */
public class LotteryItem<T> implements Comparable<LotteryItem> {
    private T itemKey;
    private Integer weight;

    public LotteryItem(T itemKey, Integer weight) {
        this.itemKey = itemKey;
        this.weight = weight;
    }

    public T getItemKey() {
        return itemKey;
    }

    public void setItemKey(T itemKey) {
        this.itemKey = itemKey;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ItemLotteryRate{")
                .append("itemKey=").append(itemKey)
                .append(", weight=").append(weight)
                .append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotteryItem that = (LotteryItem) o;

        if (itemKey != null ? !itemKey.equals(that.itemKey) : that.itemKey != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemKey != null ? itemKey.hashCode() : 0;
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
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
