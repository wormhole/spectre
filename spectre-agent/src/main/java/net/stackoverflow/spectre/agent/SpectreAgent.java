package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.agent.receiver.*;
import net.stackoverflow.spectre.agent.transformer.WatchTransformer;
import net.stackoverflow.spectre.agent.transport.AgentBusinessHandler;
import net.stackoverflow.spectre.common.command.ShellCommand;
import net.stackoverflow.spectre.common.command.ShellInvoker;
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
        WatchTransformer transformer = new WatchTransformer();
        invoker.addCommand(new ShellCommand("thread", null, new ThreadReceiver()));
        invoker.addCommand(new ShellCommand("memory", null, new MemoryReceiver()));
        invoker.addCommand(new ShellCommand("os", null, new OsReceiver()));
        invoker.addCommand(new ShellCommand("jvm", null, new JvmReceiver()));
        invoker.addCommand(new ShellCommand("gc", null, new GcReceiver()));
        invoker.addCommand(new ShellCommand("watch", null, new WatchReceiver(transformer, instrumentation)));
        invoker.addCommand(new ShellCommand("unwatch", null, new UnwatchReceiver(transformer, instrumentation)));
        invoker.addCommand(new ShellCommand("stack", null, new StackReceiver()));
        invoker.addCommand(new ShellCommand("shutdown", null, new ShutdownReceiver()));
        log.info("agent init command");

        BusinessHandler handler = new AgentBusinessHandler(invoker);
        TransportServer server = new NettyTransportServer(9966, handler);
        server.start();
    }
}
