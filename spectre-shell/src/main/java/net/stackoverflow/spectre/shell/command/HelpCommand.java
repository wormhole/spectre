package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.transport.command.AbstractCommand;
import net.stackoverflow.spectre.transport.command.Receiver;

/**
 * 帮助命令
 *
 * @author wormhole
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand(String key, String description, Receiver receiver) {
        super(key, description, receiver);
    }
}
