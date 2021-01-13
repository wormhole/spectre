package net.stackoverflow.spectre.transport.command;

/**
 * 命令接口
 *
 * @author wormhole
 */
public interface Command {

    String getCmd();

    Object execute(String... args);
}
