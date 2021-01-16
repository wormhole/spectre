package net.stackoverflow.spectre.common.command;

/**
 * 命令调用抽象类
 *
 * @author wormhole
 */
public interface Invoker {

    void addCommand(Command command);

    Object call(Object... args);
}
