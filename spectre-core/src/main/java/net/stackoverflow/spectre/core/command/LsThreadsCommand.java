package net.stackoverflow.spectre.core.command;

import net.stackoverflow.spectre.transport.command.Command;

/**
 * 列出线程命令
 *
 * @author wormhole
 */
public class LsThreadsCommand implements Command {

    private final String cmd;

    private final SpectreReceiver receiver;

    public LsThreadsCommand(String cmd, SpectreReceiver receiver) {
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
