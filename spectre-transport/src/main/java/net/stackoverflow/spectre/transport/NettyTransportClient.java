package net.stackoverflow.spectre.transport;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.stackoverflow.spectre.transport.codec.MessageDecoder;
import net.stackoverflow.spectre.transport.codec.MessageEncoder;
import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.future.ResponseFutureContext;
import net.stackoverflow.spectre.transport.handler.client.ClientCommandHandler;
import net.stackoverflow.spectre.transport.handler.client.ClientHeatBeatHandler;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.Message;
import net.stackoverflow.spectre.transport.proto.MessageTypeConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import java.util.concurrent.CountDownLatch;

/**
 * netty客户端实现
 *
 * @author wormhole
 */
public class NettyTransportClient implements TransportClient {

    private static final Logger log = LoggerFactory.getLogger(NettyTransportClient.class);

    private Channel channel;

    @Override
    public void connect(String ip, int port, CountDownLatch countDownLatch) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) {
                            ChannelPipeline pipeline = sc.pipeline();
                            pipeline.addLast(new MessageDecoder());
                            pipeline.addLast(new MessageEncoder());
                            pipeline.addLast(new ReadTimeoutHandler(60));
                            pipeline.addLast(new ClientHeatBeatHandler());
                            pipeline.addLast(new ClientCommandHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            channel = channelFuture.channel();
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            log.info("[L:{} R:{}] client connect success", channel.localAddress(), channel.remoteAddress());
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("[R:{}] client fail to connect", ip + ":" + port, e);
        } finally {
            log.info("[R:{}] client closed", ip + ":" + port);
            channel = null;
            eventLoopGroup.shutdownGracefully();
        }
    }

    @Override
    public ResponseFuture sendTo(BusinessRequest request) {
        ResponseFutureContext context = ResponseFutureContext.getInstance();
        ResponseFuture future = context.createFuture(request.getId());
        channel.writeAndFlush(new Message(MessageTypeConstant.BUSINESS_REQUEST, request));
        log.trace("[L:{} R:{}] client send request, responseId:{}", channel.localAddress(), channel.remoteAddress(), request.getId());
        return future;
    }
}
