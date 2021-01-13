package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.transport.command.Command;
import net.stackoverflow.spectre.transport.command.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 命令调用者实现
 *
 * @author wormhole
 */
public class AgentInvoker implements Invoker {

    private static final Logger log = LoggerFactory.getLogger(AgentInvoker.class);

    private final Map<String, Command> commands;

    public AgentInvoker() {
        commands = new ConcurrentHashMap<>();
    }

    @Override
    public void addCommand(Command command) {
        commands.put((String) command.getCmd(), command);
    }

    @Override
    public void removeCommand(Command command) {
        commands.remove(command.getCmd());
    }

    @Override
    public Object call(Object cmd, Object... args) {
        Command command = commands.get(cmd);
        if (command != null) {
            return command.execute(args);
        } else {
            return null;
        }
    }
}
