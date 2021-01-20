package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.Command;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * 命令抽象类
 *
 * @author wormhole
 */
public class ShellCommand implements Command {

    protected String command;

    protected String description;

    protected Receiver receiver;

    public ShellCommand(String command, String description, Receiver receiver) {
        this.command = command;
        this.description = description;
        this.receiver = receiver;
    }

    public String command() {
        return this.command;
    }

    public String description() {
        return this.description;
    }

    public Object execute(Object... args) {
        return receiver.action(args);
    }
}
