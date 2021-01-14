package net.stackoverflow.spectre.shell.util;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import net.stackoverflow.spectre.common.model.ThreadInfoDTO;
import net.stackoverflow.spectre.common.util.ColorUtils;

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
        String line = null;
        try {
            ColorUtils.color(ColorUtils.ORIGINAL, ColorUtils.BOLD);
            System.out.println("==========================================================");
            while ((line = reader.readLine()) != null) {
                ColorUtils.color(ColorUtils.F_RED, ColorUtils.BOLD);
                System.out.print(line.substring(0, 8));
                ColorUtils.color(ColorUtils.F_RED, ColorUtils.F_YELLOW, ColorUtils.BOLD);
                System.out.print(line.substring(8, 16));
                ColorUtils.color(ColorUtils.F_YELLOW, ColorUtils.BOLD);
                System.out.print(line.substring(16, 24));
                ColorUtils.color(ColorUtils.F_GREEN, ColorUtils.BOLD);
                System.out.print(line.substring(24, 32));
                ColorUtils.color(ColorUtils.F_CYAN, ColorUtils.BOLD);
                System.out.print(line.substring(32, 41));
                ColorUtils.color(ColorUtils.F_BLUE, ColorUtils.BOLD);
                System.out.print(line.substring(41, 49));
                ColorUtils.color(ColorUtils.F_PURPLE, ColorUtils.BOLD);
                System.out.println(line.substring(49));
            }
            ColorUtils.color(ColorUtils.ORIGINAL);
            System.out.println();
            System.out.println("github : https://github.com/wormhole/spectre");
            System.out.println("author : wormhole");
            System.out.println("version: 1.0.0");
            System.out.println("pid    : " + pid);
            ColorUtils.color(ColorUtils.ORIGINAL, ColorUtils.BOLD);
            System.out.println("==========================================================");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ColorUtils.color(ColorUtils.ORIGINAL);
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
        ColorUtils.color(ColorUtils.F_CYAN);
        for (VirtualMachineDescriptor vmd : list) {
            String id = vmd.id();
            String name = vmd.displayName().split(" ")[0];
            System.out.printf("%-6s %s %n", id, name);
        }
        ColorUtils.color(ColorUtils.ORIGINAL);
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
        ColorUtils.color(ColorUtils.F_BLACK, ColorUtils.B_WHITE, ColorUtils.BOLD);
        System.out.printf("%-8s %s", "option", "description");
        ColorUtils.color(ColorUtils.ORIGINAL);
        System.out.println();
        System.out.printf("%-8s %s %n", "help", "print help");
        System.out.printf("%-8s %s %n", "threads", "print thread information");
        System.out.printf("%-8s %s %n", "exit", "close session and exit spectre");
    }

    /**
     * 打印线程信息
     *
     * @param infos
     */
    public static void printThreads(List<ThreadInfoDTO> infos) {
        ColorUtils.color(ColorUtils.F_BLACK, ColorUtils.B_WHITE, ColorUtils.BOLD);
        System.out.printf("%-5s  %-25.25s  %-15s  %-10s  %-13s  %-12s  %-12s  %-11s  %-9s  %-6s  %-13s  %-50.50s",
                "id", "name", "state", "cpu", "blocked.count", "blocked.time",
                "waited.count", "waited.time", "suspended", "native", "lock.owner.id", "lock");
        ColorUtils.color(ColorUtils.ORIGINAL);
        System.out.println();
        for (ThreadInfoDTO info : infos) {
            String lockName = info.getLockName();
            if (lockName != null) {
                String[] names = lockName.split("\\.");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < names.length - 1; i++) {
                    sb.append(names[i].charAt(0)).append(".");
                }
                sb.append(names[names.length - 1]);
                lockName = sb.toString();
            }
            System.out.printf("%-5s  %-25.25s  %-15s  %-10s  %-13s  %-12s  %-12s  %-11s  %-9s  %-6s  %-13s  %-50.50s%n",
                    info.getThreadId(), info.getThreadName(), info.getThreadState(), info.getCpuRate() + "%", info.getBlockedCount(), info.getBlockedTime(),
                    info.getWaitedCount(), info.getWaitedTime(), info.getSuspended(), info.getInNative(), info.getLockOwnerId(), lockName);
        }
    }
}
