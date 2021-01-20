package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.agent.transformer.WatchTransformer;
import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.WatchInfo;

import java.lang.instrument.Instrumentation;

/**
 * watch命令
 *
 * @author wormhole
 */
public class WatchReceiver implements Receiver {

    private final WatchTransformer transformer;

    private final Instrumentation instrumentation;

    public WatchReceiver(Instrumentation instrumentation) {
        this.transformer = new WatchTransformer();
        this.instrumentation = instrumentation;
        instrumentation.addTransformer(transformer, true);
    }

    @Override
    public Object action(Object... args) {
        Class[] classes = instrumentation.getAllLoadedClasses();
        for (Class clazz : classes) {
            String className = (String) args[1];
            if (className.equals(clazz.getName())) {
                transformer.watch((String) args[1], (String) args[2]);
                try {
                    instrumentation.retransformClasses(clazz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new WatchInfo();
    }
}
