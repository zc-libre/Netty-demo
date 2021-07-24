package com.libre.netty.protocol;

import cn.hutool.core.util.ArrayUtil;
import com.libre.core.toolkit.StringPool;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author ZC
 * @date 2021/7/25 1:26
 */
public class RedisProtocolHandler extends ChannelInboundHandlerAdapter {

    private static final byte[] LINE = {13, 10};
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
          // set name libre
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes("*3".getBytes());
        buf.writeBytes(LINE);
        buf.writeBytes("$3".getBytes());
        buf.writeBytes(LINE);
        buf.writeBytes("set".getBytes());
        buf.writeBytes(LINE);
        buf.writeBytes("$4".getBytes());
        buf.writeBytes(LINE);
        buf.writeBytes("name".getBytes());
        buf.writeBytes(LINE);
        buf.writeBytes("$5".getBytes());
        buf.writeBytes(LINE);
        buf.writeBytes("libre".getBytes());
        buf.writeBytes(LINE);

        ctx.writeAndFlush(buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(buf.toString());
    }

    private String getCommendLength(int arrayLength) {
        return StringPool.ASTERISK + arrayLength;
    }

    private String getCommend(int commend) {
        return StringPool.DOLLAR + commend;
    }

    private String getCommend(String commend) {
        return StringPool.DOLLAR + commend;
    }

}
