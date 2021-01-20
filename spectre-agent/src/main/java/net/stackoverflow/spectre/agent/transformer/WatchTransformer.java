package net.stackoverflow.spectre.agent.transformer;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

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

    private Map<String, Set<String>> map = new ConcurrentHashMap<>();

    public synchronized void watch(String className, String methodName) {
        Set<String> methods = map.get(className);
        if (methods == null) {
            methods = new HashSet<>();
            map.put(className, methods);
        }
        methods.add(methodName);
    }

    public synchronized void unwatch(String className, String methodName) {
        Set<String> methods = map.get(className);
        if (methods != null) {
            methods.remove(methodName);
            if (methods.size() == 0) {
                map.remove(className);
            }
        }
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
                    CtMethod[] methods = cl.getDeclaredMethods();
                    for (int i = 0; i < methods.length; i++) {
                        CtMethod method = methods[i];
                        if (ms.contains(method.getName())) {
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
