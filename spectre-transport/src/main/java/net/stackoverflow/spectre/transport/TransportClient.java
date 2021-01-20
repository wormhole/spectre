package net.stackoverflow.spectre.transport;

import net.stackoverflow.spectre.transport.proto.BusinessRequest;

/**
 * 客户端接口
 *
 * @author wormhole
 */
public interface TransportClient {

    void connect(String ip, int port);

    void sendTo(BusinessRequest request);

    void close();
}
