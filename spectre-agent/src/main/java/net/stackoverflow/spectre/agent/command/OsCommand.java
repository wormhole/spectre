package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.command.AbstractCommand;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * os命令
 *
 * @author wormhole
 */
public class OsCommand extends AbstractCommand {

    public OsCommand(String key, String description, Receiver receiver) {
        super(key, description, receiver);
    }
}
