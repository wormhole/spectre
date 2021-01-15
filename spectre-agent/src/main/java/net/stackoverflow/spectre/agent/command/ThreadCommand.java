package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.command.AbstractCommand;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * 列出线程命令
 *
 * @author wormhole
 */
public class ThreadCommand extends AbstractCommand {

    public ThreadCommand(String key, String description, Receiver receiver) {
        super(key, description, receiver);
    }
}
