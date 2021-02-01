package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.agent.receiver.*;
import net.stackoverflow.spectre.agent.transformer.WatchTransformer;
import net.stackoverflow.spectre.agent.transport.AgentBusinessHandler;
import net.stackoverflow.spectre.common.command.*;
import net.stackoverflow.spectre.transport.NettyTransportServer;
import net.stackoverflow.spectre.transport.TransportServer;
import net.stackoverflow.spectre.transport.handler.BusinessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * SpectreAgent代理类
 *
 * @author wormhole
 */
public class SpectreAgent {

    private static final Logger log = LoggerFactory.getLogger(SpectreAgent.class);

    private final String agentArgs;

    private final Instrumentation instrumentation;

    public SpectreAgent(String agentArgs, Instrumentation instrumentation) {
        this.agentArgs = agentArgs;
        this.instrumentation = instrumentation;
    }

    public void start() {
        if (NettyTransportServer.isBind) {
            log.info("spectre server already bind");
            return;
        }
        ShellInvoker invoker = new ShellInvoker();
        WatchTransformer transformer = new WatchTransformer(instrumentation);
        invoker.addCommand(new ThreadCommand("thread", null, new ThreadReceiver()));
        invoker.addCommand(new NoOptionCommand("memory", null, new MemoryReceiver()));
        invoker.addCommand(new NoOptionCommand("os", null, new OsReceiver()));
        invoker.addCommand(new NoOptionCommand("jvm", null, new JvmReceiver()));
        invoker.addCommand(new NoOptionCommand("gc", null, new GcReceiver()));
        invoker.addCommand(new WatchCommand("watch", null, new WatchReceiver(transformer, instrumentation)));
        invoker.addCommand(new UnWatchCommand("unwatch", null, new UnwatchReceiver(transformer, instrumentation)));
        invoker.addCommand(new StackCommand("stack", null, new StackReceiver()));
        invoker.addCommand(new NoOptionCommand("shutdown", null, new ShutdownReceiver(transformer)));
        log.info("agent init command");

        BusinessHandler handler = new AgentBusinessHandler(invoker);
        TransportServer server = new NettyTransportServer(handler);
        server.bind(9966);
    }
}
