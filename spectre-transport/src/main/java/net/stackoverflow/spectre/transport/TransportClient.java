package net.stackoverflow.spectre.transport;

/**
 * 客户端接口
 *
 * @author wormhole
 */
public interface TransportClient {

    void connect(String ip, int port);
}
