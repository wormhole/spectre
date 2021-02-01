package net.stackoverflow.spectre.transport;

/**
 * 服务端接口
 *
 * @author wormhole
 */
public interface TransportServer {

    void bind(int port);

    void close();
}
