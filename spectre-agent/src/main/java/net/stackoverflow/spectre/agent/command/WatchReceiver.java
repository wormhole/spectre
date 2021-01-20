package net.stackoverflow.spectre.agent.command;

import io.netty.channel.Channel;
import net.stackoverflow.spectre.agent.SpectreHack;
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

    public WatchReceiver(Instrumentation instrumentation) {
        this.transformer = new WatchTransformer();
        this.instrumentation = instrumentation;
        instrumentation.addTransformer(transformer, true);
    }

    @Override
    public Object action(Object... args) {
        Channel channel = (Channel) args[0];
        String[] arguments = (String[]) args[1];
        Class[] classes = instrumentation.getAllLoadedClasses();
        for (Class clazz : classes) {
            if (clazz.getName().equals(arguments[1])) {
                transformer.watch(arguments[1], arguments[2]);
                SpectreHack.watches.put(arguments[1] + "." + arguments[2], channel);
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
