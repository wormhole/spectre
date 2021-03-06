package net.stackoverflow.spectre.transport.handler.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.stackoverflow.spectre.transport.context.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.proto.Header;
import net.stackoverflow.spectre.transport.proto.Message;
import net.stackoverflow.spectre.transport.proto.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端业务处理
 *
 * @author wormhole
 */
public class ClientResponseHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ClientResponseHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        Header header = message.getHeader();
        if (header.getType() == MessageType.BUSINESS_RESPONSE.value()) {
            BusinessResponse response = (BusinessResponse) message.getBody();
            if (response != null) {
                ResponseContext context = ResponseContext.getInstance();
                context.setResponse(response.getId(), response.getResponse());
            }
        }
        super.channelRead(ctx, msg);
    }

}
