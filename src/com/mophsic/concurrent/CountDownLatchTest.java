package com.mophsic.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaofei
 * @date 2017/2/10
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        // begin
        CountDownLatch begin = new CountDownLatch(1);
        // end
        CountDownLatch end = new CountDownLatch(10);

        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 10; i++) {
            final int N = i;
            threadPool.submit(() -> {
                try {
                    begin.await();
                    Thread.sleep(new Random().nextInt(10000));
                    end.countDown();
                    System.out.println("---------->" + N);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        begin.countDown();
        System.out.println("start");
        end.await();
        System.out.println("end");
        threadPool.shutdown();
    }
}
