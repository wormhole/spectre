package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.MemoryInfo;
import net.stackoverflow.spectre.common.model.MemoryPoolInfo;
import net.stackoverflow.spectre.common.model.MemoryUsageInfo;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 内存命令接收
 *
 * @author wormhole
 */
public class MemoryReceiver implements Receiver {

    @Override
    public Object action(Object... args) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();

        MemoryUsage heapUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage noHeapUsage = memoryMXBean.getNonHeapMemoryUsage();

        MemoryInfo dto = new MemoryInfo();
        MemoryUsageInfo heapUsageDTO = new MemoryUsageInfo(heapUsage.getInit(), heapUsage.getUsed(), heapUsage.getCommitted(), heapUsage.getMax());
        MemoryUsageInfo noHeapUsageDTO = new MemoryUsageInfo(noHeapUsage.getInit(), noHeapUsage.getUsed(), noHeapUsage.getCommitted(), noHeapUsage.getMax());
        dto.setHeap(heapUsageDTO);
        dto.setNoHeap(noHeapUsageDTO);

        List<MemoryPoolInfo> poolInfoDTOs = new ArrayList<>();
        for (MemoryPoolMXBean pool : pools) {
            pool.getMemoryManagerNames();
            MemoryUsage usage = pool.getUsage();
            poolInfoDTOs.add(new MemoryPoolInfo(pool.getName(), pool.getType().toString(), usage.getInit(), usage.getUsed(), usage.getCommitted(), usage.getMax(), Arrays.asList(pool.getMemoryManagerNames())));
        }
        dto.setPools(poolInfoDTOs);
        return dto;
    }
}
