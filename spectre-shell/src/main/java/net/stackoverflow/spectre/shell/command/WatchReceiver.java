package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.WatchInfo;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.future.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.fusesource.jansi.Ansi;

import java.util.UUID;

/**
 * watch命令
 *
 * @author wormhole
 */
public class WatchReceiver implements Receiver {

    private final TransportClient client;

    private final SerializeManager serializeManager;

    public WatchReceiver(TransportClient client) {
        this.client = client;
        this.serializeManager = new JsonSerializeManager();
    }

    @Override
    public Object action(Object... args) {
        String[] arguments = (String[]) args;
        BusinessRequest request = new BusinessRequest(arguments[1] + "." + arguments[2], serializeManager.serialize(args));
        client.sendTo(request);
        ResponseContext context = ResponseContext.getInstance();
        renderWatchTitle();
        for (int i = 0; i < 2; i++) {
            byte[] response = (byte[]) context.getResponse(request.getId());
            WatchInfo result = serializeManager.deserialize(response, WatchInfo.class);
            System.out.printf("%-5s  %-50s  %-50s%n", i, result.getArguments(), result.getRet());
        }
        context.unwatch(request.getId());
        request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(new String[]{"unwatch", arguments[1], arguments[2]}));
        client.sendTo(request);
        context.unwatch(request.getId());
        return null;
    }

    private void renderWatchTitle() {
        System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
        System.out.printf("%-5s  %-50s  %-50s", "num", "arguments", "return");
        System.out.println(Ansi.ansi().reset());
    }
}
