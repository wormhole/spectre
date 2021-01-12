package net.stackoverflow.spectre.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.stackoverflow.spectre.transport.codec.MessageDecoder;
import net.stackoverflow.spectre.transport.codec.MessageEncoder;
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

    @Override
    public void bind(String ip, int port, CountDownLatch countDownLatch) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) {
                            sc.pipeline().addLast(new MessageDecoder());
                            sc.pipeline().addLast(new MessageEncoder());
                            sc.pipeline().addLast(new ReadTimeoutHandler(60));
                            sc.pipeline().addLast(new ServerHeatBeatHandler());
                            sc.pipeline().addLast(new ServerCommandHandler());
                        }
                    });
            ChannelFuture future = bootstrap.bind(ip, port).sync();
            countDownLatch.countDown();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server fail to bind", e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
