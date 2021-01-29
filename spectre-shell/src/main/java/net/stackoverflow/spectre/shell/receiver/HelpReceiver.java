package net.stackoverflow.spectre.shell.receiver;


import net.stackoverflow.spectre.common.command.AbstractCommand;
import net.stackoverflow.spectre.common.command.Receiver;
import org.fusesource.jansi.Ansi;

import java.util.Collection;

/**
 * help命令接收者
 *
 * @author wormhole
 */
public class HelpReceiver implements Receiver {

    private final Collection<AbstractCommand> commands;

    public HelpReceiver(Collection<AbstractCommand> commands) {
        this.commands = commands;
    }

    @Override
    public Object action(String... args) {
        if (args.length == 1) {
            System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
            System.out.printf("%-8s %-50s", "command", "description");
            System.out.println(Ansi.ansi().reset());
            for (AbstractCommand command : commands) {
                System.out.printf("%-8s %-50s%n", command.command(), command.description());
            }
        } else if (args.length == 2) {
            for (AbstractCommand command : commands) {
                if (command.command().equals(args[1])) {
                    command.usage();
                    return null;
                }
            }
            System.out.println(Ansi.ansi().fgRed().a("unknown command: " + args[1]).reset());
        } else {
            System.out.println(Ansi.ansi().fgRed().a("unmatch arguments size").reset());
        }
        return null;
    }
}
