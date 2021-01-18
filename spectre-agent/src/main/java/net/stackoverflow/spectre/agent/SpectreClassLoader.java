package net.stackoverflow.spectre.agent;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * 自定义类加载器
 *
 * @author wormhole
 */
public class SpectreClassLoader extends ClassLoader {

    public SpectreClassLoader() {
        super(ClassLoader.getSystemClassLoader().getParent());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String path = getClassFilePath(name);
        Class<?> c = null;
        try {
            byte[] bytes = getClassBytes(path);
            c = this.defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
        }
        return c;
    }

    @Override
    protected URL findResource(String name) {
        return getSystemClassLoader().getResource(name);
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
