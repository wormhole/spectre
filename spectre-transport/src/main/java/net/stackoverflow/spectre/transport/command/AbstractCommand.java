package net.stackoverflow.spectre.transport.command;

import java.util.Comparator;

/**
 * 命令抽象类
 *
 * @author wormhole
 */
public abstract class AbstractCommand implements Command {

    protected String key;

    protected String description;

    protected Receiver receiver;

    public AbstractCommand(String key, String description, Receiver receiver) {
        this.key = key;
        this.description = description;
        this.receiver = receiver;
    }

    public String key() {
        return this.key;
    }

    public String description() {
        return this.description;
    }

    public Object execute(String... args) {
        return receiver.action(args);
    }
}
