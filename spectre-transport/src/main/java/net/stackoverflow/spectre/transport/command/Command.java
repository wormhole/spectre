package net.stackoverflow.spectre.transport.command;

/**
 * 命令接口
 *
 * @author wormhole
 */
public interface Command {

    Object execute(Object... args);
}
