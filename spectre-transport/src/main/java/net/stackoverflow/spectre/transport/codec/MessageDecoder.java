package net.stackoverflow.spectre.transport.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import net.stackoverflow.spectre.transport.proto.*;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 报文解码
 *
 * @author wormhole
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    public static final Logger log = LoggerFactory.getLogger(MessageDecoder.class);

    private final SerializeManager serializeManager;

    public MessageDecoder() {
        super(1024 * 1024, 10, 4, 0, 0);
        this.serializeManager = new JsonSerializeManager();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        Message message = new Message();
        Header header = new Header();
        frame.readBytes(header.getMagic(), 0, 8);
        header.setVersion(frame.readShort());
        header.setLength(frame.readInt());
        header.setType(frame.readByte());

        int size = frame.readInt();
        Map<String, String> attachment = new HashMap<>();

        for (int i = 0; i < size; i++) {
            int keySize = frame.readInt();
            byte[] keyBytes = new byte[keySize];
            frame.readBytes(keyBytes, 0, keySize);
            String key = new String(keyBytes, "UTF-8");

            int valueSize = frame.readInt();
            byte[] valueBytes = new byte[valueSize];
            frame.readBytes(valueBytes, 0, valueSize);
            String value = new String(keyBytes, "UTF-8");
            attachment.put(key, value);
        }
        header.setAttachment(attachment);
        message.setHeader(header);

        int bodySize = frame.readInt();
        if (bodySize > 0) {
            byte[] bodyBytes = new byte[bodySize];
            frame.readBytes(bodyBytes, 0, bodySize);
            switch (MessageType.valueOf(header.getType())) {
                case BUSINESS_REQUEST:
                    BusinessRequest request = serializeManager.deserialize(bodyBytes, BusinessRequest.class);
                    message.setBody(request);
                    break;
                case BUSINESS_RESPONSE:
                    BusinessResponse response = serializeManager.deserialize(bodyBytes, BusinessResponse.class);
                    message.setBody(response);
                    break;
                default:
                    break;
            }
        }
        log.trace("receive: {}", message);
        frame.release();
        return message;
    }
}
