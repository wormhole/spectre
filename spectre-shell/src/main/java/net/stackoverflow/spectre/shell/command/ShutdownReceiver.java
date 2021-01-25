package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.context.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;

import java.util.UUID;

/**
 * shutdown命令接收者
 *
 * @author wormhole
 */
public class ShutdownReceiver implements Receiver {
    private final TransportClient client;

    private final SerializeManager serializeManager;

    public ShutdownReceiver(TransportClient client, SerializeManager serializeManager) {
        this.client = client;
        this.serializeManager = serializeManager;
    }

    @Override
    public Object action(Object... args) {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(args));
        client.sendTo(request);
        ResponseContext.getInstance().unwatch(request.getId());
        return null;
    }
}
