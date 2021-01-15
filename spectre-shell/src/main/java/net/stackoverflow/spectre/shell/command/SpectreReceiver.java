package net.stackoverflow.spectre.shell.command;

import com.fasterxml.jackson.core.type.TypeReference;
import net.stackoverflow.spectre.common.model.ThreadInfoDTO;
import net.stackoverflow.spectre.shell.util.RenderUtils;
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

import java.util.List;
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
        client.close();
    }

    public List<ThreadInfoDTO> threads() {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize("thread"));
        ResponseFuture future = client.sendTo(request);
        BusinessResponse response = future.getResponse(-1);
        ResponseFutureContext.getInstance().removeFuture(request.getId());
        List<ThreadInfoDTO> result = serializeManager.deserialize(response.getResponse(), new TypeReference<List<ThreadInfoDTO>>() {
        });
        RenderUtils.renderThreads(result);
        return result;
    }

    public void help() {
        RenderUtils.renderHelp();
    }
}
