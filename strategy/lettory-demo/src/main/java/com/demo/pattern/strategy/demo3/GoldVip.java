package com.demo.pattern.strategy.demo3;

import com.demo.pattern.strategy.demo1.CalPrice;

@PriceRegion(min=30000)
public class GoldVip implements CalPrice {
    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice * 0.7;
    }
}
