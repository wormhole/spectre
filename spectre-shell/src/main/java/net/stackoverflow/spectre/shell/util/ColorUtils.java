package net.stackoverflow.spectre.shell.util;

/**
 * 控制台颜色工具类
 *
 * @author wormhole
 */
public class ColorUtils {

    /**
     * esc转义
     */
    public static final String ESC = "\033";

    /**
     * 原始状态
     */
    public static final String ORIGINAL = "0";

    /**
     * 粗体
     */
    public static final String BOLD = "1";

    /**
     * 模糊
     */
    public static final String FUZZY = "2";

    /**
     * 斜体
     */
    public static final String ITALIC = "3";

    /**
     * 下划线
     */
    public static final String UNDER = "4";

    /**
     * 闪烁-慢
     */
    public static final String FLASH_SLOW = "5";

    /**
     * 闪烁-快
     */
    public static final String FLASH_QUICK = "6";

    /**
     * 交换背景与前景色
     */
    public static final String SWAP = "7";

    /**
     * 隐藏
     */
    public static final String HIDDEN = "8";

    /**
     * 前景-黑色
     */
    public static final String F_BLACK = "30";

    /**
     * 前景-红色
     */
    public static final String F_RED = "31";

    /**
     * 前景-绿色
     */
    public static final String F_GREEN = "32";

    /**
     * 前景-黄色
     */
    public static final String F_YELLOW = "33";

    /**
     * 前景-蓝色
     */
    public static final String F_BLUE = "34";

    /**
     * 前景-紫色
     */
    public static final String F_PURPLE = "35";

    /**
     * 前景-青色
     */
    public static final String F_CYAN = "36";

    /**
     * 前景-白色
     */
    public static final String F_WHITE = "37";

    /**
     * 背景-黑色
     */
    public static final String B_BLACK = "40";

    /**
     * 背景-红色
     */
    public static final String B_RED = "41";

    /**
     * 背景-绿色
     */
    public static final String B_GREEN = "42";

    /**
     * 背景-黄色
     */
    public static final String B_YELLOW = "43";

    /**
     * 背景-蓝色
     */
    public static final String B_BLUE = "44";

    /**
     * 背景-紫色
     */
    public static final String B_PURPLE = "45";

    /**
     * 背景-青色
     */
    public static final String B_CYAN = "46";

    /**
     * 背景-白色
     */
    public static final String B_WHITE = "47";

    public static final void color(String... flags) {
        StringBuilder sb = new StringBuilder(ESC);
        sb.append("[");
        for (int i = 0; i < flags.length - 1; i++) {
            sb.append(flags[i]).append(";");
        }
        sb.append(flags[flags.length - 1]).append("m");
        System.out.print(sb.toString());
    }
}
