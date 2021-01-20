package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.agent.SpectreHack;
import net.stackoverflow.spectre.agent.transformer.WatchTransformer;
import net.stackoverflow.spectre.common.command.Receiver;

import java.lang.instrument.Instrumentation;

/**
 * unwatch命令
 *
 * @author wormhole
 */
public class UnwatchReceiver implements Receiver {
    private final WatchTransformer transformer;

    private final Instrumentation instrumentation;

    public UnwatchReceiver(WatchTransformer transformer, Instrumentation instrumentation) {
        this.transformer = transformer;
        this.instrumentation = instrumentation;
    }

    @Override
    public Object action(Object... args) {
        String[] arguments = (String[]) args[1];
        Class[] classes = instrumentation.getAllLoadedClasses();
        for (Class clazz : classes) {
            if (clazz.getName().equals(arguments[1])) {
                transformer.unwatch(arguments[1], arguments[2]);
                SpectreHack.watches.remove(arguments[1] + "." + arguments[2]);
                try {
                    instrumentation.retransformClasses(clazz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}