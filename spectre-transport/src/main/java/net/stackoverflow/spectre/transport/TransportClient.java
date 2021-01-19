package net.stackoverflow.spectre.transport;

import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;

/**
 * 客户端接口
 *
 * @author wormhole
 */
public interface TransportClient {

    void connect(String ip, int port);

    ResponseFuture<BusinessResponse> sendTo(BusinessRequest request);

    void close();
}
