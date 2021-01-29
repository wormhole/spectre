package net.stackoverflow.spectre.common.command;

import org.apache.commons.cli.Option;

/**
 * dump命令
 *
 * @author wormhole
 */
public class DumpCommand extends AbstractCommand {

    public DumpCommand(String command, String description, Receiver receiver) {
        super(command, description, receiver);
    }

    @Override
    protected void initOption() {
        Option file = new Option("f", "file", true, "the dump filename");
        file.setRequired(true);
        options.addOption(file);
    }
}
