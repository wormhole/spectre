package net.stackoverflow.spectre.shell.receiver;


import net.stackoverflow.spectre.common.util.ColorUtils;
import net.stackoverflow.spectre.shell.command.HelpCommand;
import net.stackoverflow.spectre.transport.command.Command;
import net.stackoverflow.spectre.transport.command.Receiver;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * help命令接收者
 *
 * @author wormhole
 */
public class HelpReceiver implements Receiver {

    private final Collection<Command> commands;

    public HelpReceiver(Collection<Command> commands) {
        this.commands = commands;
    }

    @Override
    public Object action(String... args) {
        ColorUtils.color(ColorUtils.F_BLACK, ColorUtils.B_GREY, ColorUtils.BOLD);
        System.out.printf("%-8s %s", "key", "description");
        ColorUtils.color(ColorUtils.ORIGINAL);
        System.out.println();
        for (Command command : commands) {
            System.out.printf("%-8s %s%n", command.key(), command.description());
        }
        return null;
    }
}
