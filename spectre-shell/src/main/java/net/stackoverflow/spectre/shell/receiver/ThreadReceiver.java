package net.stackoverflow.spectre.shell.receiver;

import com.fasterxml.jackson.core.type.TypeReference;
import net.stackoverflow.spectre.common.model.ThreadInfo;
import net.stackoverflow.spectre.common.util.ColorUtils;
import net.stackoverflow.spectre.common.util.FormatUtils;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.future.ResponseFutureContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;

import java.util.List;
import java.util.UUID;

/**
 * thread命令接收者
 *
 * @author wormhole
 */
public class ThreadReceiver implements Receiver {

    private final TransportClient client;

    private final SerializeManager serializeManager;

    public ThreadReceiver(TransportClient client) {
        this.client = client;
        this.serializeManager = new JsonSerializeManager();
    }

    @Override
    public Object action(Object... args) {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(args[0]));
        ResponseFuture future = client.sendTo(request);
        BusinessResponse response = future.getResponse(-1);
        ResponseFutureContext.getInstance().removeFuture(request.getId());
        List<ThreadInfo> result = serializeManager.deserialize(response.getResponse(), new TypeReference<List<ThreadInfo>>() {
        });
        renderThreads(result);
        return null;
    }

    private void renderThreads(List<ThreadInfo> infos) {
        ColorUtils.color(ColorUtils.F_BLACK, ColorUtils.B_GREY, ColorUtils.BOLD);
        System.out.printf("%-5s  %-24.24s  %-13s  %-13s  %-13s  %-13s  %-10s  %-8s  %-6s  %-6s  %-11s  %-9s  %-13s  %-12s  %-13s",
                "id", "name", "state", "cpu.rate(%)", "cpu.time", "user.time", "group", "priority", "active", "daemon", "interrupted",
                "suspended", "blocked.count", "waited.count", "lock.owner.id");
        ColorUtils.color(ColorUtils.ORIGINAL);
        System.out.println();
        for (ThreadInfo info : infos) {
            System.out.printf("%-5s  %-24.24s  %-13s  %-13s  %-13s  %-13s  %-10s  %-8s  %-6s  %-6s  %-11s  %-9s  %-13s  %-12s  %-13s%n",
                    info.getThreadId(), info.getThreadName(), info.getThreadState(), String.format("%.2f", info.getCpuRate()), FormatUtils.formatNanoSecond(info.getCpuTime()),
                    FormatUtils.formatNanoSecond(info.getUserTime()), info.getGroup(), info.getPriority(), info.getActive(), info.getDaemon(), info.getInterrupted(), info.getSuspended(),
                    info.getBlockedCount(), info.getWaitedCount(), info.getLockOwnerId());
        }
    }
}
