package net.stackoverflow.spectre.transport.command;

/**
 * 命令调用抽象类
 *
 * @author wormhole
 */
public interface Invoker {

    void addCommand(Command command);

    void removeCommand(Command command);

    Object call(Object... args);
}
