package net.stackoverflow.spectre.shell.receiver;

import com.fasterxml.jackson.core.type.TypeReference;
import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.GcInfo;
import net.stackoverflow.spectre.common.util.FormatUtils;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.context.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.fusesource.jansi.Ansi;

import java.util.List;
import java.util.UUID;

/**
 * gc命令接收者
 *
 * @author wormhole;
 */
public class GcReceiver implements Receiver {

    private final TransportClient client;

    private final SerializeManager serializeManager;

    public GcReceiver(TransportClient client, SerializeManager serializeManager) {
        this.client = client;
        this.serializeManager = serializeManager;
    }

    @Override
    public Object action(String... args) {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(args));
        client.sendTo(request);
        ResponseContext context = ResponseContext.getInstance();
        try {
            byte[] response = (byte[]) context.getResponse(request.getId());
            context.unwatch(request.getId());
            List<GcInfo> result = serializeManager.deserialize(response, new TypeReference<List<GcInfo>>() {
            });
            renderMemory(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void renderMemory(List<GcInfo> infos) {
        System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
        System.out.printf("%-25s  %-6s  %-15s  %-50s", "name", "count", "time", "memory.pool.names");
        System.out.println(Ansi.ansi().reset());
        for (GcInfo info : infos) {
            System.out.printf("%-25s  %-6s  %-15s  %-50s%n", info.getName(), info.getCount(), FormatUtils.formatMilliSecond(info.getTime()), info.getPoolNames());
        }
    }
}
