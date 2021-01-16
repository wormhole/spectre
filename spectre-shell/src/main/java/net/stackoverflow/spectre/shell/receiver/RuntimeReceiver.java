package net.stackoverflow.spectre.shell.receiver;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.RuntimeInfo;
import net.stackoverflow.spectre.common.util.ColorUtils;
import net.stackoverflow.spectre.common.util.FormatUtils;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.future.ResponseFutureContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;

import java.util.List;
import java.util.UUID;

/**
 * runtime命令接收者
 *
 * @author wormhole
 */
public class RuntimeReceiver implements Receiver {

    private final TransportClient client;

    private SerializeManager serializeManager;

    public RuntimeReceiver(TransportClient client) {
        this.client = client;
        this.serializeManager = new JsonSerializeManager();
    }

    @Override
    public Object action(Object... args) {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(args[0]));
        ResponseFuture future = client.sendTo(request);
        BusinessResponse response = future.getResponse(-1);
        ResponseFutureContext.getInstance().removeFuture(request.getId());
        RuntimeInfo result = serializeManager.deserialize(response.getResponse(), RuntimeInfo.class);
        renderRuntime(result);
        return null;
    }

    private void renderRuntime(RuntimeInfo info) {
        ColorUtils.color(ColorUtils.F_BLACK, ColorUtils.B_GREY, ColorUtils.BOLD);
        System.out.printf("%-20s  %-100s", "option", "value");
        ColorUtils.color(ColorUtils.ORIGINAL);
        System.out.println();
        System.out.printf("%-20s  %-100s%n", "jvm.name", info.getJvmName());
        System.out.printf("%-20s  %-100s%n", "jvm.version", info.getJvmVersion());
        System.out.printf("%-20s  %-100s%n", "jvm.vendor", info.getJvmVendor());
        System.out.printf("%-20s  %-100s%n", "spec.name", info.getSpecName());
        System.out.printf("%-20s  %-100s%n", "spec.version", info.getSpecVersion());
        System.out.printf("%-20s  %-100s%n", "spec.vendor", info.getSpecVendor());
        System.out.printf("%-20s  %-100s%n", "spec.name", info.getSpecName());
        System.out.printf("%-20s  %-100s%n", "start.time", FormatUtils.parseDate(info.getStartTime()));
        System.out.printf("%-20s  %-100s%n", "up.time", FormatUtils.formatMilliSecond(info.getUpTime()));
        System.out.printf("%-20s  %-100s%n", "arguments", arguments(info.getInputArguments()));
        System.out.printf("%-20s  %-100s%n", "classpath", info.getClassPath());
        System.out.printf("%-20s  %-100s%n", "bootclasspath", info.getBootClassPath());
    }

    private String arguments(List<String> args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        return sb.toString();
    }
}
