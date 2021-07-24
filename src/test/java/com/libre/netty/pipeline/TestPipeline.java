package com.libre.netty.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author ZC
 * @date 2021/7/23 20:57
 */
@Slf4j
public class TestPipeline {
    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ChannelFuture channelFuture = bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                      //  ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("1");
                                ByteBuf buf = (ByteBuf) msg;
                                String name = buf.toString(StandardCharsets.UTF_8);
                                log.info(name);
                                super.channelRead(ctx, name);
                            }
                        });
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object name) throws Exception {
                                log.info("class: {}, name: {}", name.getClass(), name);
                                log.info("2");
                                Student student = new Student((String) name);
                                super.channelRead(ctx, student);
                            }
                        });
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object student) throws Exception {
                                log.info("class: {}, student: {}", student.getClass(), student);
                                log.info("3");
                                ch.writeAndFlush(ctx.alloc().buffer().writeBytes("hello, world".getBytes()));
                            }
                        });
                        ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("4");
                                super.write(ctx, msg, promise);
                            }
                        });
                        ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("5");
                                super.write(ctx, msg, promise);
                            }
                        });
                        ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("6");
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                }).bind(8001).sync();

    }
    @Data
    @AllArgsConstructor
    static class Student {
        private String name;
    }
}
