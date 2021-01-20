package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.RuntimeInfo;
import net.stackoverflow.spectre.common.util.FormatUtils;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.future.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.fusesource.jansi.Ansi;

import java.util.UUID;

/**
 * jvm命令接收者
 *
 * @author wormhole
 */
public class JvmReceiver implements Receiver {

    private final TransportClient client;

    private SerializeManager serializeManager;

    public JvmReceiver(TransportClient client) {
        this.client = client;
        this.serializeManager = new JsonSerializeManager();
    }

    @Override
    public Object action(Object... args) {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(args));
        client.sendTo(request);
        ResponseContext context = ResponseContext.getInstance();
        byte[] response = (byte[]) context.getResponse(request.getId());
        ResponseContext.getInstance().unwatch(request.getId());
        RuntimeInfo result = serializeManager.deserialize(response, RuntimeInfo.class);
        renderRuntime(result);
        return null;
    }

    private void renderRuntime(RuntimeInfo info) {
        System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
        System.out.printf("%-20s  %-100s", "option", "value");
        System.out.println(Ansi.ansi().reset());
        System.out.printf("%-20s  %-100s%n", "jvm.name", info.getJvmName());
        System.out.printf("%-20s  %-100s%n", "jvm.version", info.getJvmVersion());
        System.out.printf("%-20s  %-100s%n", "jvm.vendor", info.getJvmVendor());
        System.out.printf("%-20s  %-100s%n", "spec.name", info.getSpecName());
        System.out.printf("%-20s  %-100s%n", "spec.version", info.getSpecVersion());
        System.out.printf("%-20s  %-100s%n", "spec.vendor", info.getSpecVendor());
        System.out.printf("%-20s  %-100s%n", "spec.name", info.getSpecName());
        System.out.printf("%-20s  %-100s%n", "start.time", FormatUtils.parseDate(info.getStartTime()));
        System.out.printf("%-20s  %-100s%n", "up.time", FormatUtils.formatMilliSecond(info.getUpTime()));
        System.out.printf("%-20s  %-100s%n", "jvm.option", info.getInputArguments());
        System.out.printf("%-20s  %-100s%n", "classpath", info.getClassPath());
        System.out.printf("%-20s  %-100s%n", "bootclasspath", info.getBootClassPath());
    }
}
