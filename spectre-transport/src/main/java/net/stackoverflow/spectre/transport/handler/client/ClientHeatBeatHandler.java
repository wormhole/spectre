package net.stackoverflow.spectre.transport.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.stackoverflow.spectre.transport.proto.Message;
import net.stackoverflow.spectre.transport.proto.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 客户端心跳检测handler
 *
 * @author wormhole
 */
public class ClientHeatBeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ClientHeatBeatHandler.class);

    private volatile ScheduledFuture<?> heartBeatFuture;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (heartBeatFuture != null) {
            heartBeatFuture.cancel(true);
            ctx.close();
            log.error("[L:{} R:{}] client closed and cancel heartbeat", ctx.channel().localAddress(), ctx.channel().remoteAddress());
        }
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (heartBeatFuture == null) {
            heartBeatFuture = ctx.executor().scheduleAtFixedRate(() -> {
                Message ping = Message.from(MessageType.HEARTBEAT_PING);
                ctx.writeAndFlush(ping);
            }, 0, 5000, TimeUnit.MILLISECONDS);
        }
        super.channelActive(ctx);
    }
}
