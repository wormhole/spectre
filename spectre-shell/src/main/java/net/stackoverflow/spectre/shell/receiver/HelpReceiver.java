package net.stackoverflow.spectre.shell.receiver;


import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.shell.ShellCommand;
import org.fusesource.jansi.Ansi;

import java.util.Collection;

/**
 * help命令接收者
 *
 * @author wormhole
 */
public class HelpReceiver implements Receiver {

    private final Collection<ShellCommand> commands;

    public HelpReceiver(Collection<ShellCommand> commands) {
        this.commands = commands;
    }

    @Override
    public Object action(Object... args) {
        System.out.print(Ansi.ansi().fgBlack().bgDefault().bold());
        System.out.printf("%-8s %s", "command", "description");
        System.out.println(Ansi.ansi().reset());
        for (ShellCommand command : commands) {
            System.out.printf("%-8s %s%n", command.command(), command.description());
        }
        return null;
    }
}
