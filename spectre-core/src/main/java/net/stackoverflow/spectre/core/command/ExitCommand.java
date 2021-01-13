package net.stackoverflow.spectre.core.command;

import net.stackoverflow.spectre.transport.command.Command;

/**
 * 退出命令
 *
 * @author wormhole
 */
public class ExitCommand implements Command {

    public final Object cmd;

    private final SpectreReceiver receiver;

    public ExitCommand(String cmd, SpectreReceiver receiver) {
        this.cmd = cmd;
        this.receiver = receiver;
    }

    @Override
    public Object getCmd() {
        return cmd;
    }

    @Override
    public Object execute(Object... args) {
        receiver.exit();
        return null;
    }
}
