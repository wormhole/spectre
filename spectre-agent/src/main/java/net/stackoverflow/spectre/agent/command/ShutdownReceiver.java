package net.stackoverflow.spectre.agent.command;

import io.netty.channel.Channel;
import net.stackoverflow.spectre.agent.transport.ChannelHolder;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * shutdown命令接收者
 */
public class ShutdownReceiver implements Receiver {
    @Override
    public Object action(Object... args) {
        Channel channel = ChannelHolder.get();
        channel.parent().close();
        return null;
    }
}
