package net.stackoverflow.spectre.agent;

import io.netty.channel.Channel;
import net.stackoverflow.spectre.common.model.WatchInfo;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.proto.Message;
import net.stackoverflow.spectre.transport.proto.MessageType;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字节码插桩
 *
 * @author wormhole
 */
public class SpectreHack {

    private static final Logger log = LoggerFactory.getLogger(SpectreHack.class);

    private static Map<String, Set<Channel>> listener = new ConcurrentHashMap<>();

    private static final SerializeManager serializeManager = new JsonSerializeManager();

    public static synchronized void listen(String key, Channel channel) {
        Set<Channel> channels = listener.get(key);
        if (channels == null) {
            channels = new HashSet<>();
            listener.put(key, channels);
        }
        channels.add(channel);
    }

    /**
     * @param key
     * @param channel
     * @return key所指向的监听列表是否为空
     */
    public static synchronized boolean unListen(String key, Channel channel) {
        Set<Channel> channels = listener.get(key);
        boolean flag = false;
        if (channels != null) {
            channels.remove(channel);
            if (channels.size() == 0) {
                listener.remove(key);
                flag = true;
            }
        } else {
            flag = true;
        }
        return flag;
    }

    public static void watch(String key, String ret, List<String> args) {
        try {
            Set<Channel> channels = listener.get(key);
            if (channels != null) {
                for (Channel channel : channels) {
                    BusinessResponse response = new BusinessResponse(key, serializeManager.serialize(new WatchInfo(args, ret)));
                    channel.writeAndFlush(Message.from(MessageType.BUSINESS_RESPONSE).body(response));
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
