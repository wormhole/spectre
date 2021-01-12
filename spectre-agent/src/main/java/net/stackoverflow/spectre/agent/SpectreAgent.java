package net.stackoverflow.spectre.agent;

import net.stackoverflow.spectre.transport.NettyTransportServer;
import net.stackoverflow.spectre.transport.TransportClient;
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
        TransportServer server = new NettyTransportServer();
        server.bind("127.0.0.1", 9966, null);
    }
}
