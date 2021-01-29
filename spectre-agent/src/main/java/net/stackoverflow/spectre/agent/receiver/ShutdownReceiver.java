package net.stackoverflow.spectre.agent.receiver;

import io.netty.channel.Channel;
import net.stackoverflow.spectre.agent.transformer.WatchTransformer;
import net.stackoverflow.spectre.agent.transport.ChannelHolder;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * shutdown命令接收者
 */
public class ShutdownReceiver implements Receiver {

    private final WatchTransformer transformer;

    public ShutdownReceiver(WatchTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public Object action(String... args) {
        transformer.unwatchAll();
        Channel channel = ChannelHolder.get();
        channel.parent().close();
        return null;
    }
}
