package net.stackoverflow.spectre.agent;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;

/**
 * 代理类
 *
 * @author wormhole
 */
public class AgentBootstrap {

    private static volatile SpectreClassLoader classLoader;

    public static void premain(String agentArgs, Instrumentation inst) {
        main(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        main(agentArgs, inst);
    }

    private static void main(String agentArgs, Instrumentation inst) {
        if (classLoader == null) {
            classLoader = new SpectreClassLoader();
        }
        try {
            Class<?> clazz = classLoader.loadClass("net.stackoverflow.spectre.agent.SpectreAgent");
            Constructor constructor = clazz.getConstructor(String.class, Instrumentation.class);
            Object object = constructor.newInstance(agentArgs, inst);
            clazz.getMethod("start").invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
