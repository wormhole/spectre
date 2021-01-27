package net.stackoverflow.spectre.agent.transport;

import io.netty.channel.Channel;

/**
 * 线程内channel持有者
 *
 * @author wormhole
 */
public class ChannelHolder {

    private static final ThreadLocal<Channel> threadLocal = new ThreadLocal<>();

    public static Channel get() {
        return threadLocal.get();
    }

    public static void set(Channel channel) {
        threadLocal.set(channel);
    }
}
