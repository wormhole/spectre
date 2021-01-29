package net.stackoverflow.spectre.common.command;

import org.apache.commons.cli.Option;

/**
 * help命令
 *
 * @author wormhole
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand(String command, String description, Receiver receiver) {
        super(command, description, receiver);
    }

    @Override
    protected void initOption() {
        Option cmd = new Option("c", "command", true, "print help of the command");
        options.addOption(cmd);
    }
}
