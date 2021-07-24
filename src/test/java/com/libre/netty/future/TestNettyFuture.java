package com.libre.netty.future;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author ZC
 * @date 2021/7/23 18:42
 */
@Slf4j
public class TestNettyFuture {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();
        EventLoop executor = group.next();

        Future<Integer> future = executor.submit(() -> {
            log.info("执行计算");
            TimeUnit.SECONDS.sleep(2);
            return 2;
        });

        log.info("等待结果");

        future.addListener(nettyFuture -> {
            log.info("结果是: {}", nettyFuture.getNow());
            group.shutdownGracefully();
        });
    }
}
