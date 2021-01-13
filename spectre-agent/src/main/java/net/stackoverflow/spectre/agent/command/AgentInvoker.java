package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.transport.command.AbstractInvoker;
import net.stackoverflow.spectre.transport.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        commands.put(command.getCmd(), command);
    }

    @Override
    public void removeCommand(String cmd) {
        commands.remove(cmd);
    }

    @Override
    public Command getCommand(String cmd) {
        return commands.get(cmd);
    }
}