package net.stackoverflow.spectre.agent;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 自定义类加载器
 *
 * @author wormhole
 */
public class SpectreClassLoader extends ClassLoader {

    public SpectreClassLoader() {
        super(ClassLoader.getSystemClassLoader());
    }

    protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null)
            return loadedClass;
        if (name != null && !name.startsWith("net.stackoverflow.spectre"))
            return super.loadClass(name, resolve);
        try {
            Class<?> aClass = findClass(name);
            if (resolve)
                resolveClass(aClass);
            return aClass;
        } catch (Exception exception) {
            return super.loadClass(name, resolve);
        }
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = getClassFilePath(name);
        try {
            byte[] bytes = getClassBytes(path);
            Class<?> c = this.defineClass(name, bytes, 0, bytes.length);
            return c;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    private String getClassFilePath(String name) {
        String[] arrays = name.split("\\.");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrays.length - 1; i++) {
            sb.append(arrays[i]).append("/");
        }
        sb.append(arrays[arrays.length - 1]).append(".class");
        return sb.toString();
    }

    private byte[] getClassBytes(String name) throws Exception {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        byte[] data = baos.toByteArray();
        is.close();
        baos.close();
        return data;
    }
}
