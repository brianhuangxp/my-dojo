package com.demo.pattern.strategy.demo3;

import com.demo.pattern.strategy.demo1.CalPrice;

@PriceRegion(max=20000)
public class Vip implements CalPrice {
    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice * 0.9;
    }
}
