package net.stackoverflow.spectre.transport.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.stackoverflow.spectre.transport.future.ResponseFutureContext;
import net.stackoverflow.spectre.transport.proto.*;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端业务处理
 *
 * @author wormhole
 */
public class ClientCommandHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ClientCommandHandler.class);

    private final SerializeManager serializeManager;

    public ClientCommandHandler() {
        this.serializeManager = new JsonSerializeManager();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        Header header = message.getHeader();
        if (header.getType() == MessageTypeConstant.BUSINESS_RESPONSE) {
            //TODO
            BusinessResponse response = (BusinessResponse) message.getBody();
            if (response != null) {
                ResponseFutureContext context = ResponseFutureContext.getInstance();
                context.setResponse(response);
            }
        }
        super.channelRead(ctx, msg);
    }


}
