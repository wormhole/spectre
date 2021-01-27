package net.stackoverflow.spectre.common.command;

/**
 * 命令接收接口
 *
 * @author wormhole
 */
public interface Receiver {

    Object action(String... args);
}
