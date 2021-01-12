package net.stackoverflow.spectre.transport.handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.stackoverflow.spectre.transport.proto.Header;
import net.stackoverflow.spectre.transport.proto.Message;
import net.stackoverflow.spectre.transport.proto.MessageTypeConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端心跳handler
 *
 * @author wormhole
 */
public class ServerHeatBeatHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerHeatBeatHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        Header header = message.getHeader();
        if (header.getType() == MessageTypeConstant.HEARTBEAT_PING) {
            ctx.writeAndFlush(new Message(MessageTypeConstant.HEARTBEAT_PONG));
        }
        super.channelRead(ctx, msg);
    }
}
