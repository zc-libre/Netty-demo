package com.libre.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;

/**
 * @author ZC
 * @date 2021/7/24 4:15
 */
public class TestMermory {
    // -Dio.netty.leakDetectionLevel=paranoid
    public static void main(String[] args) {
        for (int i = 0; i < 500000; ++i) {
            ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer(1024);
            byteBuf = null;
        }

    }
}
