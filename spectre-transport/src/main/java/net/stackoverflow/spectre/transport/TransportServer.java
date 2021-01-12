package net.stackoverflow.spectre.transport;

/**
 * 服务端接口
 *
 * @author wormhole
 */
public interface TransportServer {

    void start(int port);

    void close();
}
