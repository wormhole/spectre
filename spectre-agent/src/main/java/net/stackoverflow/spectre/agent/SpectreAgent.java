package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.agent.receiver.*;
import net.stackoverflow.spectre.transport.NettyTransportServer;
import net.stackoverflow.spectre.transport.TransportServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SpectreAgent代理类
 *
 * @author wormhole
 */
public class SpectreAgent {

    private static final Logger log = LoggerFactory.getLogger(SpectreAgent.class);

    public void start() {
        if (NettyTransportServer.isBind) {
            log.info("spectre server already bind");
            return;
        }
        AgentInvoker invoker = new AgentInvoker();
        invoker.addCommand(new AgentCommand("thread", new ThreadReceiver()));
        invoker.addCommand(new AgentCommand("memory", new MemoryReceiver()));
        invoker.addCommand(new AgentCommand("os", new OsReceiver()));
        invoker.addCommand(new AgentCommand("runtime", new RuntimeReceiver()));
        invoker.addCommand(new AgentCommand("gc", new GcReceiver()));
        TransportServer server = new NettyTransportServer();
        server.start(9966, invoker);
    }
}
