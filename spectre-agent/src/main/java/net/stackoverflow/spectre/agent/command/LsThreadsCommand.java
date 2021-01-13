package net.stackoverflow.spectre.agent.command;

import io.netty.channel.ChannelHandlerContext;
import net.stackoverflow.spectre.transport.command.Command;

/**
 * 列出线程命令
 *
 * @author wormhole
 */
public class LsThreadsCommand implements Command {

    private final String cmd;

    private final AgentReceiver receiver;

    public LsThreadsCommand(String cmd, AgentReceiver receiver) {
        this.cmd = cmd;
        this.receiver = receiver;
    }

    @Override
    public Object getCmd() {
        return cmd;
    }

    @Override
    public Object execute(Object... args) {
        return receiver.lsThreads();
    }
}
