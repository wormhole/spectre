package net.stackoverflow.spectre.agent.command;

import io.netty.channel.Channel;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * shutdown命令接收者
 */
public class ShutdownReceiver implements Receiver {
    @Override
    public Object action(Object... args) {
        Channel channel = (Channel) args[0];
        channel.parent().close();
        return null;
    }
}
