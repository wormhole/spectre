package net.stackoverflow.spectre.shell.receiver;

import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.common.command.Receiver;

/**
 * 退出命令接收者
 *
 * @author wormhole
 */
public class ExitReceiver implements Receiver {

    private final TransportClient client;

    public ExitReceiver(TransportClient client) {
        this.client = client;
    }

    @Override
    public Object action(Object... args) {
        client.close();
        return null;
    }
}
