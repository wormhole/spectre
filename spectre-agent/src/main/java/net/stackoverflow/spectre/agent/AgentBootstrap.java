package net.stackoverflow.spectre.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * 代理类
 *
 * @author wormhole
 */
public class AgentBootstrap {

    public static void premain(String agentArgs, Instrumentation inst) {
        main(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        main(agentArgs, inst);
    }

    private static void main(String agentArgs, Instrumentation inst) {
        SpectreClassLoader loader = new SpectreClassLoader();
        try {
            Class<?> clazz = loader.loadClass("net.stackoverflow.spectre.agent.SpectreAgent");
            Object object = clazz.newInstance();
            clazz.getMethod("start").invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
