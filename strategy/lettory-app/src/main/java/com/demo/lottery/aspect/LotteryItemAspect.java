package com.demo.lottery.aspect;

import com.demo.lottery.domain.Item;
import com.demo.lottery.domain.LotteryItem;
import com.demo.lottery.domain.annotation.LotteryRandom;
import com.demo.lottery.service.LotteryStrategyProvider;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by brian.huang on 05/09/2017.
 */
@Aspect
@Component
public class LotteryItemAspect {
    @Inject
    LotteryStrategyProvider provider;

    @Pointcut(value = "@annotation(com.demo.lottery.domain.annotation.LotteryRandom) ")
    private void pointcut() {
    }

    @Around(value = "pointcut() && @annotation(lotteryRandom)")
    public Object afterReturning(ProceedingJoinPoint pjp, LotteryRandom lotteryRandom) throws Throwable {
        Object result = pjp.proceed();
        if(result instanceof List) {
            List<LotteryItem> lotteryItems = (List<LotteryItem>) result;
            return provider.lotteryItem(lotteryItems, lotteryRandom);
        }
        return  result;
    }


}
