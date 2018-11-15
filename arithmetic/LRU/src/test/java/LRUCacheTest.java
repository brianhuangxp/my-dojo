import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LRUCacheTest {

    private static Map<Integer, Integer> dataMap = new HashMap<>();
    private long startTime;

    static {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        while (dataMap.size() < 100000) {
            dataMap.put(random.nextInt(4, 10000000), random.nextInt(1, 10000));
        }
    }

    private void putDefault(LRUCacheInterface lurCache) {
        dataMap.entrySet().forEach(entry -> lurCache.put(entry.getKey(), entry.getValue()));
        startTime = System.currentTimeMillis();
    }

    private void checkExecTime(String testName) {
        System.out.println(String.format("执行[%s]时间：%s ms", testName, System.currentTimeMillis() - startTime));
    }

    /*@Before
    public void checkTime() {
        startTime = System.currentTimeMillis();
    }

    @After
    public void checkExecTime() {
        System.out.println(String.format("执行时间：%s ms", System.currentTimeMillis() - startTime));
    }*/

    @Test
    public void testLRUCache() {
        LRUCache cache = new LRUCache(2000 /* capacity */);

        putDefault(cache);
        testLRUInterface(cache);
        checkExecTime("LRU1");
    }

    @Test
    public void testLRUCache2() {
        LRUCache2 cache = new LRUCache2(2000 /* capacity */);

        putDefault(cache);
        testLRUInterface(cache);
        checkExecTime("LRU2");
    }

    @Test
    public void testLRUCache3() {
        LRUCache3 cache = new LRUCache3(2000 /* capacity */);

        putDefault(cache);
        testLRUInterface(cache);
        checkExecTime("LRU3");
    }


    @Test
    public void testLRUCache_performance() {
        LRUCache cache = new LRUCache(2000 /* capacity */);

        putDefault(cache);
        for (int i = 0; i < 1000000; i++) {
            testLRUInterface(cache);
        }
        checkExecTime("LRU1-performance");
    }

    @Test
    public void testLRUCache2_performance() {
        LRUCache2 cache = new LRUCache2(2000 /* capacity */);

        putDefault(cache);
        for (int i = 0; i < 1000000; i++) {
            testLRUInterface(cache);
        }
        checkExecTime("LRU2-performance");
    }

    @Test
    public void testLRUCache3_performance() {
        LRUCache3 cache = new LRUCache3(2000 /* capacity */);

        putDefault(cache);
        for (int i = 0; i < 1000000; i++) {
            testLRUInterface(cache);
        }
        checkExecTime("LRU3-performance");
    }

    private void testLRUInterface(LRUCacheInterface lurCache) {
        lurCache.put(1, 1);
        lurCache.put(2, 2);
        Assert.assertEquals(1, lurCache.get(1));
        lurCache.put(3, 3);    // evicts key 2
//        Assert.assertEquals(-1, lurCache.get(2));
        lurCache.put(4, 4);    // evicts key 1
//        Assert.assertEquals(-1, lurCache.get(1));
        Assert.assertEquals(3, lurCache.get(3));
        Assert.assertEquals(4, lurCache.get(4));
    }
}
