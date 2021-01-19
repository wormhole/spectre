package net.stackoverflow.spectre.agent;

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

public class SpectreTransformer implements ClassFileTransformer {

    private Map<String, Set<String>> map = new ConcurrentHashMap<>();

    public void watch(String className, String methodName) {
        Set<String> methods = map.get(className);
        if (methods == null) {
            methods = new HashSet<>();
            map.put(className, methods);
        }
        methods.add(methodName);
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        Set<String> ms = map.get(className.replace("/", "."));
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
                            method.insertBefore("System.out.println(java.util.Arrays.asList($args));");
                            method.insertAfter("System.out.println($_);");
                        }
                    }
                    System.out.println(classfileBuffer.length);
                    transformed = cl.toBytecode();
                    System.out.println(transformed.length);
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
