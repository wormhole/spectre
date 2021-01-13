package net.stackoverflow.spectre.transport.command;

/**
 * 调用者抽象类
 *
 * @author wormhole
 */
public abstract class AbstractInvoker implements Invoker {

    public Object call(String cmd, String... args) {
        Command command = getCommand(cmd);
        if (command != null) {
            return command.execute(args);
        } else {
            return null;
        }
    }
}
