package com.libre.netty.future;

import com.libre.netty.executor.Executors;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author ZC
 * @date 2021/7/23 19:01
 */
@Slf4j
public class TestPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop executors = new NioEventLoopGroup().next();
        Promise<Integer> promise = new DefaultPromise<>(executors);

        ExecutorService executor = Executors.getInstance().getExecutor();
        executor.execute(() -> {
            try {
                log.info("执行计算");
                int i = 10 / 0;
                TimeUnit.SECONDS.sleep(2);
                promise.setSuccess(80);
            } catch (Exception e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        });
        log.info("等待结果");
        log.info("结果是: {}", promise.get());

    }
}
