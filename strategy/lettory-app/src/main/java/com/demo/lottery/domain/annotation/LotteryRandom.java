package com.demo.lottery.domain.annotation;

import com.demo.lottery.domain.LotteryRandomStrategy;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LotteryRandom {
    LotteryRandomStrategy strategy() default LotteryRandomStrategy.SINGLE_ITEM;
    boolean randomAmount() default true;
    int minMultiItems() default 0;
    int maxMultiItems() default Integer.MAX_VALUE;
    boolean allowDuplicated() default false;
}
