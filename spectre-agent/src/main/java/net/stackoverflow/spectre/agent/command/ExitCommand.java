package net.stackoverflow.spectre.agent.command;

import io.netty.channel.ChannelHandlerContext;
import net.stackoverflow.spectre.transport.command.Command;

/**
 * 退出命令
 *
 * @author wormhole
 */
public class ExitCommand implements Command {

    public final Object cmd;

    private final AgentReceiver receiver;

    public ExitCommand(String cmd, AgentReceiver receiver) {
        this.cmd = cmd;
        this.receiver = receiver;
    }

    @Override
    public Object getCmd() {
        return cmd;
    }

    @Override
    public Object execute(Object... args) {
        receiver.exit((ChannelHandlerContext) args[0]);
        return null;
    }
}
