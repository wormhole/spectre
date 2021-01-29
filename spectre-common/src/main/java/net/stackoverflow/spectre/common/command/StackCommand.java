package net.stackoverflow.spectre.common.command;

import org.apache.commons.cli.Option;

/**
 * stack命令
 *
 * @author wormhole
 */
public class StackCommand extends AbstractCommand {

    public StackCommand(String command, String description, Receiver receiver) {
        super(command, description, receiver);
    }

    @Override
    protected void initOption() {
        Option tid = new Option("t", "tid", true, "the thread id witch stacktrace");
        tid.setRequired(true);
        options.addOption(tid);
    }
}