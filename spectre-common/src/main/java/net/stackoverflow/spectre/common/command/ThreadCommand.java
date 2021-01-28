package net.stackoverflow.spectre.common.command;

import org.apache.commons.cli.Option;

/**
 * 线程命令
 *
 * @author wormhole
 */
public class ThreadCommand extends ShellCommand {

    public ThreadCommand(String command, String description, Receiver receiver) {
        super(command, description, receiver);
    }

    @Override
    protected void initOption() {
        Option block = new Option("b", false, "filter block thread");
        Option wait = new Option("w", false, "filter waiting and timed_waiting thread");
        block.setRequired(false);
        wait.setRequired(false);
        options.addOption(block);
        options.addOption(wait);
    }
}
