package net.stackoverflow.spectre.agent.transformer;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import net.stackoverflow.spectre.agent.SpectreHack;
import net.stackoverflow.spectre.agent.transport.ChannelHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
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

    private final Set<Class<?>> classSet = new HashSet<>();

    private final Map<String, Set<String>> methodMap = new ConcurrentHashMap<>();

    private final Instrumentation instrumentation;

    public WatchTransformer(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    public synchronized void watch(Class<?> clazz, String className, String methodName) {
        classSet.add(clazz);
        SpectreHack.listen(className + "." + methodName, ChannelHolder.get());
        Set<String> methods = methodMap.get(className);
        if (methods == null) {
            methods = new HashSet<>();
            methodMap.put(className, methods);
        }
        methods.add(methodName);
        log.info("WatchTransformer watch {}, {}", className, methodName);
        try {
            instrumentation.retransformClasses(clazz);
            log.info("Instrumentation transform class {}", clazz);
        } catch (UnmodifiableClassException e) {
            log.error("", e);
        }
    }

    public synchronized boolean unwatch(Class<?> clazz, String className, String methodName) {
        boolean result = SpectreHack.unListen(className + "." + methodName, ChannelHolder.get());
        if (result) {
            Set<String> methods = methodMap.get(className);
            if (methods != null) {
                methods.remove(methodName);
                if (methods.size() == 0) {
                    methodMap.remove(className);
                }
                log.info("WatchTransformer unwatch {}, {}", className, methodName);
            }
            try {
                instrumentation.retransformClasses(clazz);
                log.info("Instrumentation transform class {}", clazz);
            } catch (UnmodifiableClassException e) {
                log.error("", e);
            }
        }
        return result;
    }

    public synchronized void unwatchAll() {
        SpectreHack.unListenAll();
        methodMap.clear();
        try {
            if (classSet.size() > 0) {
                instrumentation.retransformClasses(classSet.toArray(new Class<?>[0]));
            }
        } catch (UnmodifiableClassException e) {
            log.error("", e);
        }
        classSet.clear();
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        className = className.replace("/", ".");
        Set<String> ms = methodMap.get(className);
        if (ms != null && ms.size() > 0) {
            byte[] transformed = null;
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new ClassClassPath(classBeingRedefined));
            CtClass cl = null;
            try {
                cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                if (!cl.isInterface()) {
                    CtMethod[] methods = cl.getMethods();
                    for (int i = 0; i < methods.length; i++) {
                        CtMethod method = methods[i];
                        if (ms.contains(method.getName())) {
                            log.info("ClassLoader {}", loader);
                            log.info("WatchTransformer transform {}, {}, {}", className, method.getName(), method.getSignature());
                            String key = className + "." + method.getName();
                            StringBuilder sb = new StringBuilder("{");
                            sb.append("ClassLoader classLoader = net.stackoverflow.spectre.agent.AgentBootstrap.classLoader;");
                            sb.append("Class clazz = classLoader.loadClass(\"net.stackoverflow.spectre.agent.SpectreHack\");");
                            sb.append("java.lang.reflect.Method method = clazz.getMethod(\"watch\", new Class[]{String.class, Object.class, java.util.List.class});");
                            /*sb.append("java.util.List arguments = new java.util.ArrayList();");
                            sb.append("String ret = $_ == null ? null : $_.toString();");
                            sb.append("for(int i=0;i<$args.length;i++){");
                            sb.append("if($args[i]!=null){");
                            sb.append("arguments.add($args[i].toString());");
                            sb.append("}else{");
                            sb.append("arguments.add(null);}}");*/
                            sb.append("method.invoke(null, new Object[]{\"").append(key).append("\", $_, java.util.Arrays.asList($args)});");
                            sb.append("}");
                            method.insertAfter(sb.toString());
                        }
                    }
                    transformed = cl.toBytecode();
                }
            } catch (Exception e) {
                log.error("", e);
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
