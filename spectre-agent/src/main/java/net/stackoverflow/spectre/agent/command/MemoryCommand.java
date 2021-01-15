package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.command.AbstractCommand;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * 内存命令
 *
 * @author wormhole
 */
public class MemoryCommand extends AbstractCommand {
    public MemoryCommand(String key, String description, Receiver receiver) {
        super(key, description, receiver);
    }
}
