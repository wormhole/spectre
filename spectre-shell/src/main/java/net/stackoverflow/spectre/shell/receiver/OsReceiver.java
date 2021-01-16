package net.stackoverflow.spectre.shell.receiver;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.OsInfo;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.future.ResponseFutureContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.fusesource.jansi.Ansi;

import java.util.UUID;

/**
 * 查询os命令接收者
 *
 * @author wormhole
 */
public class OsReceiver implements Receiver {

    private final TransportClient client;

    private final SerializeManager serializeManager;

    public OsReceiver(TransportClient client) {
        this.client = client;
        this.serializeManager = new JsonSerializeManager();
    }

    @Override
    public Object action(Object... args) {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(args[0]));
        ResponseFuture future = client.sendTo(request);
        BusinessResponse response = future.getResponse(-1);
        ResponseFutureContext.getInstance().removeFuture(request.getId());
        OsInfo dto = serializeManager.deserialize(response.getResponse(), OsInfo.class);
        renderOsInfo(dto);
        return null;
    }

    private void renderOsInfo(OsInfo dto) {
        System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
        System.out.printf("%-10s  %-10s", "option", "value");
        System.out.println(Ansi.ansi().reset());
        System.out.printf("%-10s  %-10s%n", "name", dto.getName());
        System.out.printf("%-10s  %-10s%n", "version", dto.getVersion());
        System.out.printf("%-10s  %-10s%n", "arch", dto.getArch());
        System.out.printf("%-10s  %-10s%n", "cores", dto.getCores());
    }
}
