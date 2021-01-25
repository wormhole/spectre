package net.stackoverflow.spectre.transport.proto;

/**
 * 报文类型枚举
 *
 * @author wormhole
 */
public enum MessageType {

    AUTH_REQUEST((byte) 0),
    AUTH_RESPONSE((byte) 1),
    HEARTBEAT_PING((byte) 2),
    HEARTBEAT_PONG((byte) 3),
    BUSINESS_REQUEST((byte) 4),
    BUSINESS_RESPONSE((byte) 5);

    private final byte value;

    MessageType(byte value) {
        this.value = value;
    }

    public final byte value() {
        return this.value;
    }

    public static MessageType from(byte value) {
        for (MessageType type : MessageType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }
}
