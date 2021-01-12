package net.stackoverflow.spectre.transport;

import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;

import java.util.concurrent.CountDownLatch;

/**
 * 客户端接口
 *
 * @author wormhole
 */
public interface TransportClient {

    void connect(String ip, int port, CountDownLatch countDownLatch);

    ResponseFuture sendTo(String ip, int port, BusinessRequest request);
}
