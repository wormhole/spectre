package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.command.Command;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * agent命令实现
 *
 * @author wormhole
 */
public class AgentCommand implements Command {

    protected String command;

    protected Receiver receiver;

    public AgentCommand(String command, Receiver receiver) {
        this.command = command;
        this.receiver = receiver;
    }

    public String command() {
        return this.command;
    }

    public Object execute(Object... args) {
        return receiver.action(args);
    }
}
