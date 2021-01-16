package net.stackoverflow.spectre.agent.receiver;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.MemoryInfoDTO;
import net.stackoverflow.spectre.common.model.MemoryPoolInfoDTO;
import net.stackoverflow.spectre.common.model.MemoryUsageInfoDTO;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
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

        MemoryInfoDTO dto = new MemoryInfoDTO();
        MemoryUsageInfoDTO heapUsageDTO = new MemoryUsageInfoDTO(heapUsage.getInit(), heapUsage.getUsed(), heapUsage.getCommitted(), heapUsage.getMax());
        MemoryUsageInfoDTO noHeapUsageDTO = new MemoryUsageInfoDTO(noHeapUsage.getInit(), noHeapUsage.getUsed(), noHeapUsage.getCommitted(), noHeapUsage.getMax());
        dto.setHeap(heapUsageDTO);
        dto.setNoHeap(noHeapUsageDTO);

        List<MemoryPoolInfoDTO> poolInfoDTOs = new ArrayList<>();
        for (MemoryPoolMXBean pool : pools) {
            MemoryUsage usage = pool.getUsage();
            poolInfoDTOs.add(new MemoryPoolInfoDTO(pool.getName(), pool.getType().toString(), usage.getInit(), usage.getUsed(), usage.getCommitted(), usage.getMax()));
        }
        dto.setPools(poolInfoDTOs);
        return dto;
    }
}
