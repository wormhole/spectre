package net.stackoverflow.spectre.core.command;

import net.stackoverflow.spectre.transport.command.Command;

/**
 * 退出命令
 *
 * @author wormhole
 */
public class ExitCommand implements Command {

    public final String cmd;

    private final SpectreReceiver receiver;

    public ExitCommand(String cmd, SpectreReceiver receiver) {
        this.cmd = cmd;
        this.receiver = receiver;
    }

    @Override
    public String getCmd() {
        return cmd;
    }

    @Override
    public Object execute(String... args) {
        receiver.exit();
        return null;
    }
}
