package net.stackoverflow.spectre.transport;

import net.stackoverflow.spectre.transport.command.Invoker;

/**
 * 服务端接口
 *
 * @author wormhole
 */
public interface TransportServer {

    void start(int port, Invoker invoker);

    void close();
}
