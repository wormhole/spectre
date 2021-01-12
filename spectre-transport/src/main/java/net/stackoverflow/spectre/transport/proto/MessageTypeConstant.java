package net.stackoverflow.spectre.transport.proto;

/**
 * 消息类型
 *
 * @author wormhole
 */
public class MessageTypeConstant {

    /**
     * 认证请求
     */
    public static final byte AUTH_REQUEST = 0;

    /**
     * 认证响应
     */
    public static final byte AUTH_RESPONSE = 1;

    /**
     * 心跳检测请求
     */
    public static final byte HEARTBEAT_PING = 2;

    /**
     * 心跳检测响应
     */
    public static final byte HEARTBEAT_PONG = 3;

    /**
     * 业务请求
     */
    public static final byte BUSINESS_REQUEST = 4;

    /**
     * 业务响应
     */
    public static final byte BUSINESS_RESPONSE = 5;
}
