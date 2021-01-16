package net.stackoverflow.spectre.shell.receiver;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.MemoryInfoDTO;
import net.stackoverflow.spectre.common.model.MemoryPoolInfoDTO;
import net.stackoverflow.spectre.common.util.ColorUtils;
import net.stackoverflow.spectre.common.util.FormatUtils;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.future.ResponseFutureContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;

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
    public Object action(String... args) {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(args[0]));
        ResponseFuture future = client.sendTo(request);
        BusinessResponse response = future.getResponse(-1);
        ResponseFutureContext.getInstance().removeFuture(request.getId());
        MemoryInfoDTO result = serializeManager.deserialize(response.getResponse(), MemoryInfoDTO.class);
        renderMemory(result);
        return null;
    }

    public void renderMemory(MemoryInfoDTO result) {
        ColorUtils.color(ColorUtils.F_BLACK, ColorUtils.B_GREY, ColorUtils.BOLD);
        System.out.printf("%-25s  %-20s  %-15s  %-15s  %-15s  %-15s",
                "name", "type", "init(MB)", "used(MB)", "committed(MB)", "max(MB)");
        ColorUtils.color(ColorUtils.ORIGINAL);
        System.out.println();
        System.out.printf("%-25s  %-20s  %-15s  %-15s  %-15s  %-15s%n",
                "Heap total", "Heap memory", FormatUtils.bytesToMB(result.getHeap().getInit()), FormatUtils.bytesToMB(result.getHeap().getUsed()),
                FormatUtils.bytesToMB(result.getHeap().getCommitted()), FormatUtils.bytesToMB(result.getHeap().getMax()));
        System.out.printf("%-25s  %-20s  %-15s  %-15s  %-15s  %-15s%n",
                "Non-Heap total", "Non-heap memory", FormatUtils.bytesToMB(result.getNoHeap().getInit()), FormatUtils.bytesToMB(result.getNoHeap().getUsed()),
                FormatUtils.bytesToMB(result.getNoHeap().getCommitted()), FormatUtils.bytesToMB(result.getNoHeap().getMax()));
        for (MemoryPoolInfoDTO pool : result.getPools()) {
            System.out.printf("%-25s  %-20s  %-15s  %-15s  %-15s  %-15s%n",
                    pool.getName(), pool.getType(), FormatUtils.bytesToMB(pool.getInit()), FormatUtils.bytesToMB(pool.getUsed()),
                    FormatUtils.bytesToMB(pool.getCommitted()), FormatUtils.bytesToMB(pool.getMax()));
        }
    }
}