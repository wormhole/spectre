package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.transport.command.AbstractCommand;
import net.stackoverflow.spectre.transport.command.Receiver;

/**
 * 退出命令
 *
 * @author wormhole
 */
public class ExitCommand extends AbstractCommand {

    public ExitCommand(String key, String description, Receiver receiver) {
        super(key, description, receiver);
    }
}
