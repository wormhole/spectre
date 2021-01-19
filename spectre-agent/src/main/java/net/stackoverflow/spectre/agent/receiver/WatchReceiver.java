package net.stackoverflow.spectre.agent.receiver;

import net.stackoverflow.spectre.agent.SpectreTransformer;
import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.WatchInfo;

import java.lang.instrument.Instrumentation;

/**
 * watch命令
 *
 * @author wormhole
 */
public class WatchReceiver implements Receiver {

    private SpectreTransformer transformer;

    private Instrumentation instrumentation;

    public WatchReceiver(Instrumentation instrumentation) {
        this.transformer = new SpectreTransformer();
        this.instrumentation = instrumentation;
        instrumentation.addTransformer(transformer, true);
    }

    @Override
    public Object action(Object... args) {
        String[] arr = ((String) args[0]).split(" ");
        Class[] classes = instrumentation.getAllLoadedClasses();
        for (Class clazz : classes) {
            String className = arr[1];
            if (className.equals(clazz.getName())) {
                transformer.watch(arr[1], arr[2]);
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
