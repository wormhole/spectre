package net.stackoverflow.spectre.agent.receiver;

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
    public Object action(String... args) {
        Class[] classes = instrumentation.getAllLoadedClasses();
        for (Class clazz : classes) {
            if (clazz.getName().equals(args[2])) {
                boolean result = transformer.unwatch(args[2], args[3]);
                if (result) {
                    try {
                        instrumentation.retransformClasses(clazz);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
