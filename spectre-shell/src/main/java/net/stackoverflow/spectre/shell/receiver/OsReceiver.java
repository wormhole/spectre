package net.stackoverflow.spectre.shell.receiver;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.OsInfo;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.context.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
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

    public OsReceiver(TransportClient client, SerializeManager serializeManager) {
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
            OsInfo dto = serializeManager.deserialize(response, OsInfo.class);
            renderOsInfo(dto);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
