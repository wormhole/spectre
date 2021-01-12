package net.stackoverflow.spectre.transport.proto;

/**
 * 消息类型
 *
 * @author wormhole
 */
public enum MessageTypeEnum {

    /**
     * 认证请求
     */
    AUTH_REQUEST((byte) 0),

    /**
     * 认证响应
     */
    AUTH_RESPONSE((byte) 1),

    /**
     * 心跳检测请求
     */
    HEARTBEAT_PING((byte) 2),

    /**
     * 心跳检测响应
     */
    HEARTBEAT_PONG((byte) 3),

    /**
     * 业务请求
     */
    BUSINESS_REQUEST((byte) 4),

    /**
     * 业务响应
     */
    BUSINESS_RESPONSE((byte) 5);

    private byte type;

    MessageTypeEnum(byte type) {
        this.type = type;
    }

    public byte type() {
        return type;
    }

    public static MessageTypeEnum valueOf(byte type) {
        MessageTypeEnum messageTypeEnum = null;
        for (MessageTypeEnum value : values()) {
            if (value.type() == type) {
                messageTypeEnum = value;
            }
        }
        return messageTypeEnum;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MessageTypeEnum{");
        sb.append("type=").append(type);
        sb.append(", name=").append(name());
        sb.append('}');
        return sb.toString();
    }
}
