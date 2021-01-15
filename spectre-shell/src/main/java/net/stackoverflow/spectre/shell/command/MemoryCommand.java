package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.AbstractCommand;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * 查看内存命令
 *
 * @author wormhole
 */
public class MemoryCommand extends AbstractCommand {
    public MemoryCommand(String key, String description, Receiver receiver) {
        super(key, description, receiver);
    }

    @Override
    public Object execute(String... args) {
        return super.execute(key);
    }
}
