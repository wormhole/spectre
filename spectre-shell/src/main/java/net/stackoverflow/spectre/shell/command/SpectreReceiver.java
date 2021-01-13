package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.shell.PrintUtils;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.command.Receiver;
import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.future.ResponseFutureContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

/**
 * 命令接收者实现
 *
 * @author wormhole
 */
public class SpectreReceiver implements Receiver {

    private static final Logger log = LoggerFactory.getLogger(SpectreReceiver.class);

    private final TransportClient client;

    private final SerializeManager serializeManager;

    public SpectreReceiver(TransportClient client) {
        this.client = client;
        this.serializeManager = new JsonSerializeManager();
    }

    public void exit() {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize("exit"));
        client.sendTo(request);
    }

    public Map<Long, String> threads() {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize("threads"));
        ResponseFuture future = client.sendTo(request);
        BusinessResponse response = future.getResponse(-1);
        if (response != null) {
            ResponseFutureContext.getInstance().removeFuture(response.getId());
        }
        Map<Long, String> result = serializeManager.deserialize(response.getResponse(), Map.class);
        PrintUtils.printThreads(result);
        return result;
    }

    public void help() {
        PrintUtils.printHelp();
    }
}
