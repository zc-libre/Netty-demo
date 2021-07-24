package com.libre.netty.executor;

import cn.hutool.core.thread.ExecutorBuilder;
import lombok.experimental.UtilityClass;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZC
 * @date 2021/7/23 18:05
 */

public class Executors {

    private Executors(){};

    private final ExecutorService executor = ExecutorBuilder.create()
                                                            .setCorePoolSize(8)
            .setMaxPoolSize(16)
            .setWorkQueue(new LinkedBlockingDeque<>(16))
            .setKeepAliveTime(60, TimeUnit.SECONDS)
            .setThreadFactory(java.util.concurrent.Executors.defaultThreadFactory())
            .setHandler(new ThreadPoolExecutor.CallerRunsPolicy())
            .build();

    private static class ThreadPool {
        private static final Executors INSTANCE = new Executors();
    }

    public ExecutorService getExecutor() {
       return executor;
    }

    public static Executors getInstance() {
        return ThreadPool.INSTANCE;
    }
}
