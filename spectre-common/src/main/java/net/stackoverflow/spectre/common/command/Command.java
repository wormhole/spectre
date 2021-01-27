package net.stackoverflow.spectre.common.command;

/**
 * 命令接口
 *
 * @author wormhole
 */
public interface Command {

    Object execute(String... args);
}
