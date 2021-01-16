package net.stackoverflow.spectre.common.command;

import java.util.Collection;

/**
 * 命令调用抽象类
 *
 * @author wormhole
 */
public interface Invoker {

    void addCommand(Command command);

    Collection<? extends Command> getCommands();

    Object call(Object... args);
}
