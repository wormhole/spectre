package net.stackoverflow.spectre.transport;

import java.util.concurrent.CountDownLatch;

/**
 * 服务端接口
 *
 * @author wormhole
 */
public interface TransportServer {

    void bind(String ip, int port, CountDownLatch countDownLatch);
}
