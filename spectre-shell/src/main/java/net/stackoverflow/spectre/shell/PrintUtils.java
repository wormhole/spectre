package net.stackoverflow.spectre.shell;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * 打印工具类
 *
 * @author wormhole
 */
public class PrintUtils {

    public static void printBanner(String pid) {
        InputStream is = ClassLoader.getSystemResourceAsStream("banner.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        System.out.print("\033[35m");
        String line = null;
        try {
            System.out.println("==========================================================");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.print("\033[0m");
            System.out.println("github : https://github.com/wormhole/spectre");
            System.out.println("author : 凉衫薄");
            System.out.println("version: 1.0.0");
            System.out.println("pid    : " + pid);
            System.out.print("\033[35m");
            System.out.println("==========================================================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.print("\033[0m");
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printVirtualMachines() {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        System.out.print("\033[36m");
        for (VirtualMachineDescriptor vmd : list) {
            String id = vmd.id();
            String name = vmd.displayName().split(" ")[0];
            System.out.println(id + " " + name);
        }
        System.out.print("\033[0m");
        System.out.print("input pid: ");
    }

    public static void printSession(String pid) {
        System.out.print("[spectre@" + pid + "]# ");
    }

    public static void printThreads(Map<Long, String> map) {
        System.out.printf("%-10s %s %n", "threadId", "name");
        for (Map.Entry<Long, String> entry : map.entrySet()) {
            System.out.printf("%-10s %s %n", entry.getKey(), entry.getValue());
        }
    }
}
