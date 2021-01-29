package net.stackoverflow.spectre.common.command;

import org.apache.commons.cli.Option;

/**
 * unwatch 命令
 *
 * @author wormhole
 */
public class UnWatchCommand extends AbstractCommand {
    public UnWatchCommand(String command, String description, Receiver receiver) {
        super(command, description, receiver);
    }

    @Override
    protected void initOption() {
        Option classname = new Option("t", "target", true, "the target class name and method name");
        classname.setRequired(true);
        classname.setArgName("className methodName");
        classname.setArgs(2);
        options.addOption(classname);
    }
}
