package net.stackoverflow.spectre.transport.handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.Header;
import net.stackoverflow.spectre.transport.proto.Message;
import net.stackoverflow.spectre.transport.proto.MessageTypeConstant;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端业务处理
 *
 * @author wormhole
 */
public class ServerCommandHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerCommandHandler.class);

    private final SerializeManager serializeManager;

    public ServerCommandHandler() {
        this.serializeManager = new JsonSerializeManager();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        Header header = message.getHeader();
        if (header.getType() == MessageTypeConstant.BUSINESS_REQUEST) {
            //TODO
            BusinessRequest request = (BusinessRequest) message.getBody();
            ctx.writeAndFlush(new Message(MessageTypeConstant.BUSINESS_RESPONSE, request.getRequest()));
        }
        super.channelRead(ctx, msg);
    }


}
