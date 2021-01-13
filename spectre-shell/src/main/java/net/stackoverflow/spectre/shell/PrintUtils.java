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

    /**
     * 打印banner
     *
     * @param pid
     */
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
            clColor();
            System.out.println("github : https://github.com/wormhole/spectre");
            System.out.println("author : 凉衫薄");
            System.out.println("version: 1.0.0");
            System.out.println("pid    : " + pid);
            System.out.print("\033[35m");
            System.out.println("==========================================================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            clColor();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 打印虚拟机进程列表
     */
    public static void printVirtualMachines() {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        System.out.print("\033[36m");
        for (VirtualMachineDescriptor vmd : list) {
            String id = vmd.id();
            String name = vmd.displayName().split(" ")[0];
            System.out.printf("%-6s %s %n", id, name);
        }
        clColor();
        System.out.print("input pid: ");
    }

    /**
     * 打印会话提示
     *
     * @param pid
     */
    public static void printSession(String pid) {
        System.out.print("[spectre@" + pid + "]# ");
    }

    /**
     * 打印帮助信息
     */
    public static void printHelp() {
        titleColor();
        System.out.printf("%-8s %s %n", "option", "description");
        clColor();
        System.out.printf("%-8s %s %n", "help", "print help");
        System.out.printf("%-8s %s %n", "threads", "print thread information");
        System.out.printf("%-8s %s %n", "exit", "close session and exit spectre");
    }

    /**
     * 打印线程信息
     *
     * @param map
     */
    public static void printThreads(Map<Long, String> map) {
        titleColor();
        System.out.printf("%-5s %s %n", "id", "name");
        clColor();
        for (Map.Entry<Long, String> entry : map.entrySet()) {
            System.out.printf("%-5s %s %n", entry.getKey(), entry.getValue());
        }
    }

    public static void titleColor() {
        System.out.print("\033[30;47;1m");
    }

    public static void clColor() {
        System.out.print("\033[0m");
    }
}
