package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.model.ThreadInfoDTO;
import net.stackoverflow.spectre.transport.command.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.*;

/**
 * 命令接收者实现
 *
 * @author wormhole
 */
public class AgentReceiver implements Receiver {

    private static final Logger log = LoggerFactory.getLogger(AgentReceiver.class);

    public List<ThreadInfoDTO> threads() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        List<ThreadInfoDTO> result = new ArrayList<>();
        Map<Long, Long> times = new HashMap<>();
        for (Long threadId : threadMXBean.getAllThreadIds()) {
            ThreadInfo info = threadMXBean.getThreadInfo(threadId);
            ThreadInfoDTO dto = new ThreadInfoDTO();
            dto.setThreadId(info.getThreadId());
            dto.setThreadName(info.getThreadName());
            dto.setThreadState(info.getThreadState().name());
            dto.setBlockedCount(info.getBlockedCount());
            dto.setBlockedTime(info.getBlockedTime());
            dto.setWaitedCount(info.getWaitedCount());
            dto.setWaitedTime(info.getWaitedTime());
            dto.setSuspended(info.isSuspended());
            dto.setInNative(info.isInNative());
            dto.setLockName(info.getLockName());
            dto.setLockOwnerId(info.getLockOwnerId());

            if (threadMXBean.isThreadCpuTimeSupported()) {
                if (!threadMXBean.isThreadCpuTimeEnabled()) {
                    threadMXBean.setThreadCpuTimeEnabled(true);
                }
                times.put(threadId, threadMXBean.getThreadCpuTime(threadId));
            }
            result.add(dto);
        }
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        for (ThreadInfoDTO dto : result) {
            long cpuEnd = threadMXBean.getThreadCpuTime(dto.getThreadId());
            dto.setCpuRate((double) ((cpuEnd - times.get(dto.getThreadId())) / ((end - start) * 1000)));
        }
        Collections.sort(result, Comparator.comparingDouble(ThreadInfoDTO::getCpuRate));
        Collections.reverse(result);
        return result;
    }
}
