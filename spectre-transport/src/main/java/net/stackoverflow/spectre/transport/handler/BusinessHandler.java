package net.stackoverflow.spectre.transport.handler;

import io.netty.channel.ChannelHandlerContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;

/**
 * netty服务端业务消息处理回调
 *
 * @author wormhole
 */
public interface BusinessHandler {

    void handle(ChannelHandlerContext ctx, BusinessRequest request, SerializeManager serializeManager);
}
