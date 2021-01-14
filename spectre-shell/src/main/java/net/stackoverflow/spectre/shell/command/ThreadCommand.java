package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.transport.command.Command;

/**
 * 列出线程命令
 *
 * @author wormhole
 */
public class ThreadCommand implements Command {

    private final String cmd;

    private final SpectreReceiver receiver;

    public ThreadCommand(String cmd, SpectreReceiver receiver) {
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
