package com.libre.netty.future;

import com.libre.netty.executor.Executors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author ZC
 * @date 2021/7/23 18:26
 */
@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.getInstance().getExecutor();
        Future<Integer> future = executor.submit(() -> {
            log.info("执行计算");
            TimeUnit.SECONDS.sleep(2);
            return 2;
        });
        log.info("等待结果");
        log.info("结果是: {}", future.get());

        executor.shutdown();
    }
}
