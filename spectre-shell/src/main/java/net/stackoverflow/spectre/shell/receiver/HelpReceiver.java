package net.stackoverflow.spectre.shell.receiver;


import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.util.ColorUtils;
import net.stackoverflow.spectre.shell.ShellCommand;

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
        ColorUtils.color(ColorUtils.F_BLACK, ColorUtils.B_GREY, ColorUtils.BOLD);
        System.out.printf("%-8s %s", "command", "description");
        ColorUtils.color(ColorUtils.ORIGINAL);
        System.out.println();
        for (ShellCommand command : commands) {
            System.out.printf("%-8s %s%n", command.command(), command.description());
        }
        return null;
    }
}
