package net.stackoverflow.spectre.common.util;

/**
 * 格式化工具类
 *
 * @author wormhole
 */
public class FormatUtils {

    public static final Long NS_OF_MS = 1000000L;

    public static final Long NS_OF_SECOND = 1000 * NS_OF_MS;

    public static final Long NS_OF_MINUTES = 60 * NS_OF_SECOND;

    public static final Long NS_OF_HOUR = 60 * NS_OF_MINUTES;

    public static final Long NS_OF_DAY = 24 * NS_OF_HOUR;

    public static String formatNanoSecond(Long ns) {
        StringBuilder sb = new StringBuilder();
        if (ns > NS_OF_DAY) {
            sb.append(ns / NS_OF_DAY).append(":");
            ns = ns % NS_OF_DAY;
        } else {
            sb.append("00:");
        }
        if (ns > NS_OF_HOUR) {
            sb.append(ns / NS_OF_HOUR).append(":");
            ns = ns % NS_OF_HOUR;
        } else {
            sb.append("00:");
        }
        if (ns > NS_OF_MINUTES) {
            sb.append(ns / NS_OF_MINUTES).append(":");
            ns = ns % NS_OF_MINUTES;
        } else {
            sb.append("00:");
        }
        if (ns > NS_OF_SECOND) {
            sb.append(ns / NS_OF_SECOND);
        } else {
            sb.append("00");
        }
        return sb.toString();
    }
}
