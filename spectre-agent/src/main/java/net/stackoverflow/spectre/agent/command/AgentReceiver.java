package net.stackoverflow.spectre.agent.command;

import io.netty.channel.ChannelHandlerContext;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.command.Receiver;
import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.proto.Message;
import net.stackoverflow.spectre.transport.proto.MessageTypeConstant;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 命令接收者实现
 *
 * @author wormhole
 */
public class AgentReceiver implements Receiver {

    private static final Logger log = LoggerFactory.getLogger(AgentReceiver.class);

    public void exit(ChannelHandlerContext context) {
        context.channel().parent().close();
    }

    public Map<Long, String> lsThreads() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Map<Long, String> result = new HashMap<>();
        for (Long threadId : threadMXBean.getAllThreadIds()) {
            result.put(threadId, threadMXBean.getThreadInfo(threadId).getThreadName());
        }
        return result;
    }
}
