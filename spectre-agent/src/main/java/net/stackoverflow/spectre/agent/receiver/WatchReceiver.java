package net.stackoverflow.spectre.agent.receiver;

import net.stackoverflow.spectre.agent.transformer.WatchTransformer;
import net.stackoverflow.spectre.common.command.Receiver;

import java.lang.instrument.Instrumentation;

/**
 * watch命令
 *
 * @author wormhole
 */
public class WatchReceiver implements Receiver {

    private final WatchTransformer transformer;

    private final Instrumentation instrumentation;

    public WatchReceiver(WatchTransformer transformer, Instrumentation instrumentation) {
        this.transformer = transformer;
        this.instrumentation = instrumentation;
        instrumentation.addTransformer(transformer, true);
    }

    @Override
    public Object action(String... args) {
        Class[] classes = instrumentation.getAllLoadedClasses();
        for (Class clazz : classes) {
            if (clazz.getName().equals(args[2])) {
                transformer.watch(clazz, args[2], args[3]);
            }
        }
        return null;
    }
}
