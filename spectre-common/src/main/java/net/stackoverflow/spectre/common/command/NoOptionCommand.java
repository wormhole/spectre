package net.stackoverflow.spectre.common.command;

/**
 * 无选项命令
 */
public class NoOptionCommand extends AbstractCommand {

    public NoOptionCommand(String command, String description, Receiver receiver) {
        super(command, description, receiver);
    }

    @Override
    protected void initOption() {

    }
}
