package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.agent.command.*;
import net.stackoverflow.spectre.agent.transformer.WatchTransformer;
import net.stackoverflow.spectre.transport.NettyTransportServer;
import net.stackoverflow.spectre.transport.TransportServer;
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
        AgentInvoker invoker = new AgentInvoker();
        WatchTransformer transformer = new WatchTransformer();
        invoker.addCommand(new AgentCommand("thread", new ThreadReceiver()));
        invoker.addCommand(new AgentCommand("memory", new MemoryReceiver()));
        invoker.addCommand(new AgentCommand("os", new OsReceiver()));
        invoker.addCommand(new AgentCommand("jvm", new JvmReceiver()));
        invoker.addCommand(new AgentCommand("gc", new GcReceiver()));
        invoker.addCommand(new AgentCommand("watch", new WatchReceiver(transformer, instrumentation)));
        invoker.addCommand(new AgentCommand("unwatch", new UnwatchReceiver(transformer, instrumentation)));
        log.info("agent init command");
        TransportServer server = new NettyTransportServer();
        server.start(9966, invoker);
    }
}
