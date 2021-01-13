package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.transport.command.Command;

/**
 * 列出线程命令
 *
 * @author wormhole
 */
public class ThreadsCommand implements Command {

    private final String cmd;

    private final SpectreReceiver receiver;

    public ThreadsCommand(String cmd, SpectreReceiver receiver) {
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
