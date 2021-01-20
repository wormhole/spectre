package net.stackoverflow.spectre.shell.command;

import com.fasterxml.jackson.core.type.TypeReference;
import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.ThreadInfo;
import net.stackoverflow.spectre.common.util.FormatUtils;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.context.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

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
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(args));
        client.sendTo(request);
        ResponseContext context = ResponseContext.getInstance();
        try {
            byte[] response = (byte[]) context.getResponse(request.getId());
            context.unwatch(request.getId());
            List<ThreadInfo> result = serializeManager.deserialize(response, new TypeReference<List<ThreadInfo>>() {
            });
            renderThreads(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void renderThreads(List<ThreadInfo> infos) {
        System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
        System.out.printf("%-5s  %-24.24s  %-13s  %-13s  %-13s  %-15.15s  %-8s  %-6s  %-6s  %-11s  %-9s  %-13s  %-12s  %-13s  %-13s",
                "id", "name", "cpu.rate(%)", "cpu.time", "user.time", "group", "priority", "active", "daemon", "interrupted",
                "suspended", "blocked.count", "waited.count", "lock.owner.id", "state");
        System.out.println(Ansi.ansi().reset());
        for (ThreadInfo info : infos) {
            AnsiConsole.out().printf("%-5s  %-24.24s  %-13s  %-13s  %-13s  %-15.15s  %-8s  %-6s  %-6s  %-11s  %-9s  %-13s  %-12s  %-13s  %-13s%n",
                    info.getThreadId(), info.getThreadName(), String.format("%.2f", info.getCpuRate()), FormatUtils.formatNanoSecond(info.getCpuTime()),
                    FormatUtils.formatNanoSecond(info.getUserTime()), info.getGroup(), info.getPriority(), info.getActive(), info.getDaemon(), info.getInterrupted(), info.getSuspended(),
                    info.getBlockedCount(), info.getWaitedCount(), info.getLockOwnerId(), threadState(info.getThreadState()));
        }
    }

    private Ansi threadState(String state) {
        Ansi ansi = Ansi.ansi().a(state).reset();
        switch (state) {
            case "NEW":
                ansi = Ansi.ansi().a(state).reset();
                break;
            case "RUNNABLE":
                ansi = Ansi.ansi().fgBrightGreen().a(state).reset();
                break;
            case "BLOCKED":
                ansi = Ansi.ansi().fgBrightRed().a(state).reset();
                break;
            case "WAITING":
                ansi = Ansi.ansi().fgBrightBlue().a(state).reset();
                break;
            case "TIMED_WAITING":
                ansi = Ansi.ansi().fgBrightCyan().a(state).reset();
                break;
            case "TERMINATED":
                ansi = Ansi.ansi().fgBrightMagenta().a(state).reset();
                break;
            default:
                break;
        }
        return ansi;
    }
}
