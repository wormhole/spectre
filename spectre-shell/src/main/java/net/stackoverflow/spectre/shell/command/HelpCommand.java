package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.transport.command.Command;

/**
 * 帮助命令
 *
 * @author wormhole
 */
public class HelpCommand implements Command {

    private final String cmd;

    private final SpectreReceiver receiver;

    public HelpCommand(String cmd, SpectreReceiver receiver) {
        this.cmd = cmd;
        this.receiver = receiver;
    }

    @Override
    public String getCmd() {
        return cmd;
    }

    @Override
    public Object execute(String... strings) {
        receiver.help();
        return null;
    }
}
