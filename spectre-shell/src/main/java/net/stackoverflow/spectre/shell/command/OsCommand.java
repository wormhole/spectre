package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.AbstractCommand;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * 查询os信息命令
 *
 * @author wormhole
 */
public class OsCommand extends AbstractCommand {
    public OsCommand(String key, String description, Receiver receiver) {
        super(key, description, receiver);
    }

    @Override
    public Object execute(String... args) {
        return super.execute(key);
    }
}
