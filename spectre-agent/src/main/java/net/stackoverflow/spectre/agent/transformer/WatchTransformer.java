package net.stackoverflow.spectre.agent.transformer;

import io.netty.channel.Channel;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import net.stackoverflow.spectre.agent.SpectreHack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * watch命令字节码转换
 *
 * @author wormhole
 */
public class WatchTransformer implements ClassFileTransformer {

    private static final Logger log = LoggerFactory.getLogger(WatchTransformer.class);

    private Map<String, Set<String>> map = new ConcurrentHashMap<>();

    public synchronized void watch(String className, String methodName, Channel channel) {
        SpectreHack.listen(className + "." + methodName, channel);
        Set<String> methods = map.get(className);
        if (methods == null) {
            methods = new HashSet<>();
            map.put(className, methods);
        }
        methods.add(methodName);
        log.info("WatchTransformer watch {}, {}", className, methodName);
    }

    public synchronized boolean unwatch(String className, String methodName, Channel channel) {
        boolean result = SpectreHack.unListen(className + "." + methodName, channel);
        if (result) {
            Set<String> methods = map.get(className);
            if (methods != null) {
                methods.remove(methodName);
                if (methods.size() == 0) {
                    map.remove(className);
                }
                log.info("WatchTransformer unwatch {}, {}", className, methodName);
            }
        }
        return result;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        Set<String> ms = map.get(className);
        if (ms != null && ms.size() > 0) {
            byte[] transformed = null;
            ClassPool pool = ClassPool.getDefault();
            CtClass cl = null;
            try {
                cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                if (!cl.isInterface()) {
                    CtMethod[] methods = cl.getMethods();
                    for (int i = 0; i < methods.length; i++) {
                        CtMethod method = methods[i];
                        if (ms.contains(method.getName())) {
                            log.info("WatcherTransformer transform {}, {}, {}", className, method.getName(), method.getSignature());
                            String key = className + "." + method.getName();
                            StringBuilder sb = new StringBuilder("{");
                            sb.append("ClassLoader classLoader = net.stackoverflow.spectre.agent.AgentBootstrap.classLoader;");
                            sb.append("Class clazz = classLoader.loadClass(\"net.stackoverflow.spectre.agent.SpectreHack\");");
                            sb.append("java.lang.reflect.Method method = clazz.getMethod(\"watch\", new Class[]{String.class, Object.class, java.util.List.class});");
                            sb.append("method.invoke(null, new Object[]{\"").append(key).append("\", $_, java.util.Arrays.asList($args)});");
                            sb.append("}");
                            method.insertAfter(sb.toString());
                        }
                    }
                    transformed = cl.toBytecode();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cl != null) {
                    cl.detach();
                }
            }
            return transformed;
        } else {
            return classfileBuffer;
        }
    }
}
