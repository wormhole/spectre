package net.stackoverflow.spectre.shell.util;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import net.stackoverflow.spectre.common.util.ColorUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 打印工具类
 *
 * @author wormhole
 */
public class RenderUtils {

    /**
     * 打印banner
     *
     * @param pid
     */
    public static void renderBanner(String pid) {
        InputStream is = ClassLoader.getSystemResourceAsStream("banner.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            ColorUtils.color(ColorUtils.ORIGINAL, ColorUtils.BOLD);
            System.out.println("==========================================================");
            while ((line = reader.readLine()) != null) {
                ColorUtils.color(ColorUtils.F_RED, ColorUtils.BOLD);
                System.out.print(line.substring(0, 8));
                ColorUtils.color(ColorUtils.F_L_RED, ColorUtils.BOLD);
                System.out.print(line.substring(8, 16));
                ColorUtils.color(ColorUtils.F_L_YELLOW, ColorUtils.BOLD);
                System.out.print(line.substring(16, 24));
                ColorUtils.color(ColorUtils.F_L_GREEN, ColorUtils.BOLD);
                System.out.print(line.substring(24, 32));
                ColorUtils.color(ColorUtils.F_L_CYAN, ColorUtils.BOLD);
                System.out.print(line.substring(32, 41));
                ColorUtils.color(ColorUtils.F_L_BLUE, ColorUtils.BOLD);
                System.out.print(line.substring(41, 49));
                ColorUtils.color(ColorUtils.F_L_PURPLE, ColorUtils.BOLD);
                System.out.println(line.substring(49));
            }
            ColorUtils.color(ColorUtils.ORIGINAL);
            System.out.println();
            System.out.println("github  : https://github.com/wormhole/spectre");
            System.out.println("author  : wormhole");
            System.out.println("version : 1.0.0");
            System.out.println("attached: " + pid);
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
    public static void renderVirtualMachines() {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        ColorUtils.color(ColorUtils.F_CYAN);
        for (VirtualMachineDescriptor vmd : list) {
            String id = vmd.id();
            String name = vmd.displayName().split(" ")[0];
            System.out.printf("%-6s %s%n", id, name);
        }
        ColorUtils.color(ColorUtils.ORIGINAL);
        System.out.print("input pid: ");
    }

    /**
     * 打印会话提示
     *
     * @param pid
     */
    public static void renderSession(String pid) {
        System.out.print("[spectre@" + pid + "]# ");
    }
}
