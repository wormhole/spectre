package net.stackoverflow.spectre.transport.handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.stackoverflow.spectre.common.command.Invoker;
import net.stackoverflow.spectre.transport.proto.*;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 服务端业务处理
 *
 * @author wormhole
 */
public class ServerCommandHandler extends ChannelInboundHandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(ServerCommandHandler.class);

    private final SerializeManager serializeManager;

    private final Invoker invoker;

    public ServerCommandHandler(Invoker invoker) {
        this.serializeManager = new JsonSerializeManager();
        this.invoker = invoker;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        Header header = message.getHeader();
        if (header.getType() == MessageType.BUSINESS_REQUEST.value()) {
            BusinessRequest request = (BusinessRequest) message.getBody();
            String[] commands = serializeManager.deserialize(request.getRequest(), String[].class);
            log.info("agent call command {}", Arrays.asList(commands));
            Object result = invoker.call(ctx.channel(), commands);
            if (result != null) {
                BusinessResponse response = new BusinessResponse(request.getId(), serializeManager.serialize(result));
                ctx.writeAndFlush(Message.from(MessageType.BUSINESS_RESPONSE).body(response));
            }
        }
        super.channelRead(ctx, msg);
    }


}
