package net.stackoverflow.spectre.agent.transport;

import io.netty.channel.ChannelHandlerContext;
import net.stackoverflow.spectre.common.command.Invoker;
import net.stackoverflow.spectre.transport.handler.BusinessHandler;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.proto.Message;
import net.stackoverflow.spectre.transport.proto.MessageType;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * netty服务端业务消息处理回调实现类
 */
public class AgentBusinessHandler implements BusinessHandler {

    private static final Logger log = LoggerFactory.getLogger(AgentBusinessHandler.class);

    private final Invoker invoker;

    public AgentBusinessHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, BusinessRequest request, SerializeManager serializeManager) {
        String[] commands = serializeManager.deserialize(request.getRequest(), String[].class);
        log.info("agent call command {}", Arrays.asList(commands));
        ChannelHolder.set(ctx.channel());
        Object result = invoker.call(commands);
        if (result != null) {
            BusinessResponse response = new BusinessResponse(request.getId(), serializeManager.serialize(result));
            ctx.writeAndFlush(Message.from(MessageType.BUSINESS_RESPONSE).body(response));
        }
    }
}
