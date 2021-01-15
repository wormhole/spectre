package net.stackoverflow.spectre.common.command;

import java.util.Collection;

/**
 * 命令调用抽象类
 *
 * @author wormhole
 */
public interface Invoker {

    void addCommand(Command command);

    void removeCommand(String key);

    Command getCommand(String key);

    Collection<Command> getCommands();

    Object call(String key, String... args);
}
