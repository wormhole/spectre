package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.command.Command;
import net.stackoverflow.spectre.common.command.Invoker;
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

    private final Map<String, AgentCommand> commands;

    public AgentInvoker() {
        commands = new ConcurrentHashMap<>();
    }

    @Override
    public void addCommand(Command command) {
        AgentCommand agentCommand = (AgentCommand) command;
        commands.put(agentCommand.command, agentCommand);
    }

    @Override
    public Object call(Object... args) {
        Command command = commands.get(args[0]);
        if (command != null) {
            return command.execute(args);
        } else {
            return null;
        }
    }
}
