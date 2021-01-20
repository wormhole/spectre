package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.Command;
import net.stackoverflow.spectre.common.command.Invoker;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调用者抽象类
 *
 * @author wormhole
 */
public class ShellInvoker implements Invoker {

    private final Map<String, ShellCommand> commands;

    public ShellInvoker() {
        commands = new ConcurrentHashMap<>();
    }

    @Override
    public void addCommand(Command command) {
        ShellCommand shellCommand = (ShellCommand) command;
        commands.put(shellCommand.command, shellCommand);
    }

    public Collection<ShellCommand> getCommands() {
        return commands.values();
    }

    public Object call(Object... args) {
        Command command = commands.get(args[0]);
        if (command != null) {
            return command.execute(args);
        } else {
            return null;
        }
    }
}
