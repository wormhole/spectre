package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.agent.command.AgentInvoker;
import net.stackoverflow.spectre.agent.command.AgentReceiver;
import net.stackoverflow.spectre.agent.command.LsThreadsCommand;
import net.stackoverflow.spectre.transport.NettyTransportServer;
import net.stackoverflow.spectre.transport.TransportServer;

import java.lang.instrument.Instrumentation;

/**
 * 代理类
 *
 * @author wormhole
 */
public class SpectreAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        main(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        main(agentArgs, inst);
    }

    private static void main(String agentArgs, Instrumentation inst) {
        AgentInvoker invoker = new AgentInvoker();
        AgentReceiver receiver = new AgentReceiver();
        invoker.addCommand(new LsThreadsCommand("ls threads", receiver));
        TransportServer server = new NettyTransportServer();
        server.start(9966, invoker);
    }
}
