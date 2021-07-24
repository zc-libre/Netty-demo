package com.libre.netty.eventloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * @author ZC
 * @date 2021/7/23 1:50
 */
public class EventLoopServer {
    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ChannelFuture channelFuture = bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                String message = buf.toString(StandardCharsets.UTF_8);
                                System.out.println(message);
                            }
                        });
                    }
                }).bind(8080);

        Channel channel = channelFuture.sync().channel();

        channel.closeFuture().addListener((ChannelFutureListener) future -> {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        });

    }
}
