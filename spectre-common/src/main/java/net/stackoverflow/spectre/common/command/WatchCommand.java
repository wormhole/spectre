package net.stackoverflow.spectre.common.command;

import org.apache.commons.cli.Option;

/**
 * watch命令
 *
 * @author wormhole
 */
public class WatchCommand extends NoOptionCommand {

    public WatchCommand(String command, String description, Receiver receiver) {
        super(command, description, receiver);
    }

    @Override
    protected void initOption() {
        Option classname = new Option("t", "target", true, "the target className and methodName");
        classname.setRequired(true);
        classname.setArgName("className methodName");
        classname.setArgs(2);
        options.addOption(classname);
    }
}
