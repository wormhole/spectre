package net.stackoverflow.spectre.agent;

import io.netty.channel.Channel;
import net.stackoverflow.spectre.common.model.WatchInfo;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.proto.Message;
import net.stackoverflow.spectre.transport.proto.MessageTypeConstant;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字节码插桩
 *
 * @author wormhole
 */
public class SpectreHack {

    private static final Logger log = LoggerFactory.getLogger(SpectreHack.class);

    public static Map<String, Channel> watches = new ConcurrentHashMap<>();

    private static final SerializeManager serializeManager = new JsonSerializeManager();

    public static void watch(String key, Object ret, List<Object> args) {
        try {
            Channel channel = watches.get(key);
            if (channel != null) {
                BusinessResponse response = new BusinessResponse(key, serializeManager.serialize(new WatchInfo(args, ret)));
                channel.writeAndFlush(new Message(MessageTypeConstant.BUSINESS_RESPONSE, response));
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
