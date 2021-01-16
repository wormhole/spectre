package net.stackoverflow.spectre.shell;

import net.stackoverflow.spectre.common.command.AbstractInvoker;
import net.stackoverflow.spectre.common.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 命令调用者实现
 *
 * @author wormhole
 */
public class ShellInvoker extends AbstractInvoker {

    private static final Logger log = LoggerFactory.getLogger(ShellInvoker.class);

    private final Map<String, Command> commands;

    public ShellInvoker() {
        commands = new ConcurrentHashMap<>();
    }

    @Override
    public void addCommand(Command command) {
        commands.put(command.key(), command);
    }

    @Override
    public void removeCommand(String key) {
        commands.remove(key);
    }

    @Override
    public Command getCommand(String key) {
        return commands.get(key);
    }

    @Override
    public Collection<Command> getCommands() {
        return commands.values();
    }
}
