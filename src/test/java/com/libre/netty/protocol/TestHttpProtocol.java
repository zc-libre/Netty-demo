package com.libre.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author ZC
 * @date 2021/7/25 2:28
 */
public class TestHttpProtocol {
    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap.group(boss, worker)
                    .localAddress(8080)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<DefaultHttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, DefaultHttpRequest request) throws Exception {
                                    HttpVersion httpVersion = request.protocolVersion();

                                    byte[] content = "<h1>hello, world</h1>".getBytes(StandardCharsets.UTF_8);
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpVersion, HttpResponseStatus.OK);
                                    HttpHeaders headers = response.headers();
                                    headers.setInt(HttpHeaderNames.CONTENT_LENGTH, content.length);
                                    response.content().writeBytes(content);

                                    ctx.writeAndFlush(response);
                                }
                            });
                        }
                    });
            ChannelFuture future = serverBootstrap.bind().sync();
            future.channel().closeFuture().sync();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
           boss.shutdownGracefully();
           worker.shutdownGracefully();
        }
    }
}
