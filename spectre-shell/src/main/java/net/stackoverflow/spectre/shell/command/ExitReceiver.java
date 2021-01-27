package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.transport.TransportClient;

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
    public Object action(String... args) {
        client.close();
        return null;
    }
}
