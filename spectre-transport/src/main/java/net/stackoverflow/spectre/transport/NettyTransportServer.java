package net.stackoverflow.spectre.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.stackoverflow.spectre.transport.codec.MessageDecoder;
import net.stackoverflow.spectre.transport.codec.MessageEncoder;
import net.stackoverflow.spectre.transport.command.Invoker;
import net.stackoverflow.spectre.transport.handler.server.ServerCommandHandler;
import net.stackoverflow.spectre.transport.handler.server.ServerHeatBeatHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * netty服务端实现类
 *
 * @author wormhole
 */
public class NettyTransportServer implements TransportServer {

    private static final Logger log = LoggerFactory.getLogger(NettyTransportServer.class);

    private volatile Channel channel;

    @Override
    public void start(int port, Invoker invoker) {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel sc) {
                                ChannelPipeline pipeline = sc.pipeline();
                                pipeline.addLast(new MessageDecoder());
                                pipeline.addLast(new MessageEncoder());
                                pipeline.addLast(new ReadTimeoutHandler(60));
                                pipeline.addLast(new ServerHeatBeatHandler());
                                pipeline.addLast(new ServerCommandHandler(invoker));
                            }
                        });
                ChannelFuture future = bootstrap.bind(port).sync();
                channel = future.channel();
                countDownLatch.countDown();
                log.info("[L:{}] server start success", channel.localAddress());
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("server fail to start", e);
            } finally {
                bossGroup.shutdownGracefully();
                workGroup.shutdownGracefully();
                log.info("[L:{}] server closed", channel.localAddress());
                channel = null;
            }
        });
        thread.setDaemon(true);
        thread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }

    @Override
    public void close() {
        if (channel != null) {
            channel.close();
            channel = null;
        }
    }
}
