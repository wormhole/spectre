package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.MemoryInfo;
import net.stackoverflow.spectre.common.model.MemoryPoolInfo;
import net.stackoverflow.spectre.common.util.FormatUtils;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.future.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.fusesource.jansi.Ansi;

import java.util.UUID;

/**
 * 内存命令接收者
 *
 * @author wormhole
 */
public class MemoryReceiver implements Receiver {

    private final TransportClient client;

    private final SerializeManager serializeManager;

    public MemoryReceiver(TransportClient client) {
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
        MemoryInfo result = serializeManager.deserialize(response, MemoryInfo.class);
        renderMemory(result);
        return null;
    }

    private void renderMemory(MemoryInfo result) {
        System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
        System.out.printf("%-25s  %-20s  %-15s  %-15s  %-15s  %-15s  %-30s",
                "name", "type", "init(MB)", "used(MB)", "committed(MB)", "max(MB)", "memory.manager");
        System.out.println(Ansi.ansi().reset());
        System.out.printf("%-25s  %-20s  %-15s  %-15s  %-15s  %-15s  %-30s%n",
                "Heap total", "Heap memory", FormatUtils.bytesToMB(result.getHeap().getInit()), FormatUtils.bytesToMB(result.getHeap().getUsed()),
                FormatUtils.bytesToMB(result.getHeap().getCommitted()), FormatUtils.bytesToMB(result.getHeap().getMax()), "-");
        System.out.printf("%-25s  %-20s  %-15s  %-15s  %-15s  %-15s  %-30s%n",
                "Non-Heap total", "Non-heap memory", FormatUtils.bytesToMB(result.getNoHeap().getInit()), FormatUtils.bytesToMB(result.getNoHeap().getUsed()),
                FormatUtils.bytesToMB(result.getNoHeap().getCommitted()), FormatUtils.bytesToMB(result.getNoHeap().getMax()), "-");
        for (MemoryPoolInfo pool : result.getPools()) {
            System.out.printf("%-25s  %-20s  %-15s  %-15s  %-15s  %-15s  %-30s%n",
                    pool.getName(), pool.getType(), FormatUtils.bytesToMB(pool.getInit()), FormatUtils.bytesToMB(pool.getUsed()),
                    FormatUtils.bytesToMB(pool.getCommitted()), FormatUtils.bytesToMB(pool.getMax()), pool.getManager());
        }
    }
}
