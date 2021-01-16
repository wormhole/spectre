package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.agent.receiver.*;
import net.stackoverflow.spectre.transport.NettyTransportServer;
import net.stackoverflow.spectre.transport.TransportServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * 代理类
 *
 * @author wormhole
 */
public class AgentBootstrap {

    private static final Logger log = LoggerFactory.getLogger(AgentBootstrap.class);

    public static void premain(String agentArgs, Instrumentation inst) {
        main(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        main(agentArgs, inst);
    }

    private static void main(String agentArgs, Instrumentation inst) {
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
