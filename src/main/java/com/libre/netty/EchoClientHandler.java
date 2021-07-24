package com.libre.netty;

import com.libre.netty.executor.Executors;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

/**
 * @author ZC
 * @date 2021/7/24 3:24
 */
@Slf4j
public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ExecutorService executor = Executors.getInstance().getExecutor();
        executor.execute(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                log.info("请输入消息: ");
                String line = scanner.nextLine();
                if ("q".equals(line)) {
                    ctx.close();
                    break;
                }
                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(line.getBytes(StandardCharsets.UTF_8)));
            }
        });
        executor.shutdown();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        log.info("message: {}", buf.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
