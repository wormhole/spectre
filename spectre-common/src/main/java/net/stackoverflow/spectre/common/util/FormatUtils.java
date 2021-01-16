package net.stackoverflow.spectre.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化工具类
 *
 * @author wormhole
 */
public class FormatUtils {

    public static final Long MS_PER_SECOND = 1000L;

    public static final Long MS_PER_MINUTES = 60 * MS_PER_SECOND;

    public static final Long MS_PER_HOUR = 60 * MS_PER_MINUTES;

    public static final Long MS_PER_DAY = 24 * MS_PER_HOUR;

    public static final Long NS_PER_MS = 1000000L;

    public static final Long NS_PER_SECOND = 1000 * NS_PER_MS;

    public static final Long NS_PER_MINUTES = 60 * NS_PER_SECOND;

    public static final Long NS_PER_HOUR = 60 * NS_PER_MINUTES;

    public static final Long NS_PER_DAY = 24 * NS_PER_HOUR;

    public static final Long MB = 1024 * 1024L;

    public static String parseDate(Long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(timestamp));
    }

    public static Long bytesToMB(long bytes) {
        return bytes / MB;
    }

    public static String formatMilliSecond(Long ms) {
        StringBuilder sb = new StringBuilder();
        if (ms > NS_PER_DAY) {
            sb.append(String.format("%02d", ms / MS_PER_DAY)).append(":");
            ms = ms % MS_PER_DAY;
        } else {
            sb.append("00:");
        }
        if (ms > MS_PER_HOUR) {
            sb.append(String.format("%02d", ms / MS_PER_HOUR)).append(":");
            ms = ms % MS_PER_HOUR;
        } else {
            sb.append("00:");
        }
        if (ms > MS_PER_MINUTES) {
            sb.append(String.format("%02d", ms / MS_PER_MINUTES)).append(":");
            ms = ms % MS_PER_MINUTES;
        } else {
            sb.append("00:");
        }
        if (ms > MS_PER_SECOND) {
            sb.append(String.format("%02d", ms / MS_PER_SECOND));
        } else {
            sb.append("00");
        }
        return sb.toString();
    }

    public static String formatNanoSecond(Long ns) {
        return formatMilliSecond(ns / NS_PER_MS);
    }
}
