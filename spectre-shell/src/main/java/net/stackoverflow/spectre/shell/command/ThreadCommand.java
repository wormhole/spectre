package net.stackoverflow.spectre.shell.command;

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

    @Override
    public Object execute(String... args) {
        return super.execute(key);
    }
}
