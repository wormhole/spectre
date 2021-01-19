package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.agent.receiver.*;
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

    private String agentArgs;

    private Instrumentation instrumentation;

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
        invoker.addCommand(new AgentCommand("thread", new ThreadReceiver()));
        invoker.addCommand(new AgentCommand("memory", new MemoryReceiver()));
        invoker.addCommand(new AgentCommand("os", new OsReceiver()));
        invoker.addCommand(new AgentCommand("jvm", new JvmReceiver()));
        invoker.addCommand(new AgentCommand("gc", new GcReceiver()));
        TransportServer server = new NettyTransportServer();
        server.start(9966, invoker);
    }
}
