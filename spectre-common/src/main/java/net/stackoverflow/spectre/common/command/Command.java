package net.stackoverflow.spectre.common.command;

/**
 * 命令接口
 *
 * @author wormhole
 */
public interface Command {

    String key();

    String description();

    Object execute(String... args);
}
