package net.stackoverflow.spectre.agent;

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
public class AgentInvoker extends AbstractInvoker {

    private static final Logger log = LoggerFactory.getLogger(AgentInvoker.class);

    private final Map<String, Command> commands;

    public AgentInvoker() {
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
