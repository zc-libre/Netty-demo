package com.libre.netty.eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author ZC
 * @date 2021/7/23 1:35
 */
@Slf4j
public class EventGroupTests {
    public static void main(String[] args) {
       // System.out.println(NettyRuntime.availableProcessors());
        EventLoopGroup group = new NioEventLoopGroup(2);
        System.out.println(group.next());

        // 执行普通任务
    /*
        group.next().execute(() -> {
            log.info("ok");
        });
        log.info("end");*/

        group.next().scheduleAtFixedRate(() -> {
            log.info("ok");
        }, 0, 1, TimeUnit.SECONDS);
    }
}
