package net.stackoverflow.spectre.agent;

import java.lang.instrument.Instrumentation;

/**
 * 代理类
 *
 * @author wormhole
 */
public class SpectreAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain");
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agent main");
    }
}
