package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.agent.command.AgentInvoker;
import net.stackoverflow.spectre.agent.command.AgentReceiver;
import net.stackoverflow.spectre.agent.command.ThreadCommand;
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
public class SpectreAgent {

    private static final Logger log = LoggerFactory.getLogger(SpectreAgent.class);

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
        AgentReceiver receiver = new AgentReceiver();
        invoker.addCommand(new ThreadCommand("thread", receiver));
        TransportServer server = new NettyTransportServer();
        server.start(9966, invoker);
    }
}
