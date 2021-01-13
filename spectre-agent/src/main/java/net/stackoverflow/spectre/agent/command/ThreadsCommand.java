package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.transport.command.Command;

/**
 * 列出线程命令
 *
 * @author wormhole
 */
public class ThreadsCommand implements Command {

    private final String cmd;

    private final AgentReceiver receiver;

    public ThreadsCommand(String cmd, AgentReceiver receiver) {
        this.cmd = cmd;
        this.receiver = receiver;
    }

    @Override
    public String getCmd() {
        return cmd;
    }

    @Override
    public Object execute(String... args) {
        return receiver.threads();
    }
}
