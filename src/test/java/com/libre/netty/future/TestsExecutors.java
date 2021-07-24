package com.libre.netty.future;

import com.libre.netty.executor.Executors;

/**
 * @author ZC
 * @date 2021/7/23 18:21
 */
public class TestsExecutors {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(Executors.getInstance().getExecutor());
            }).start();
        }
    }
}
