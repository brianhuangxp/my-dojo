package com.demo.pattern.strategy.demo3;

import com.demo.pattern.strategy.demo1.CalPrice;

@PriceRegion(max = 10000)
public class Orgnic implements CalPrice {

    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice;
    }
}
