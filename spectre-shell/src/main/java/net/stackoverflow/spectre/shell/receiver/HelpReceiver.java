package net.stackoverflow.spectre.shell.receiver;


import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.command.ShellCommand;
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
    public Object action(String... args) {
        System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
        System.out.printf("%-8s %-50s", "command", "description");
        System.out.println(Ansi.ansi().reset());
        for (ShellCommand command : commands) {
            System.out.printf("%-8s %-50s%n", command.command(), command.description());
        }
        return null;
    }
}
