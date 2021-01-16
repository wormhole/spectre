package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.agent.command.MemoryCommand;
import net.stackoverflow.spectre.agent.command.OsCommand;
import net.stackoverflow.spectre.agent.command.ThreadCommand;
import net.stackoverflow.spectre.agent.receiver.MemoryReceiver;
import net.stackoverflow.spectre.agent.receiver.OsReceiver;
import net.stackoverflow.spectre.agent.receiver.ThreadReceiver;
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
        invoker.addCommand(new ThreadCommand("thread", "Fetch thread information", new ThreadReceiver()));
        invoker.addCommand(new MemoryCommand("memory", "Fetch memory information", new MemoryReceiver()));
        invoker.addCommand(new OsCommand("os", "Fetch operating system information", new OsReceiver()));
        TransportServer server = new NettyTransportServer();
        server.start(9966, invoker);
    }
}
