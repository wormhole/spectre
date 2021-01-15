package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.model.ThreadInfoDTO;
import net.stackoverflow.spectre.common.util.ThreadUtils;
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

        if (threadMXBean.isThreadCpuTimeSupported()) {
            if (!threadMXBean.isThreadCpuTimeEnabled()) {
                threadMXBean.setThreadCpuTimeEnabled(true);
            }
        }
        long[] threadIds = threadMXBean.getAllThreadIds();
        for (Long threadId : threadIds) {
            ThreadInfo info = threadMXBean.getThreadInfo(threadId);
            ThreadInfoDTO dto = new ThreadInfoDTO();
            dto.setThreadId(info.getThreadId());
            dto.setThreadName(info.getThreadName());
            dto.setThreadState(info.getThreadState().name());
            dto.setBlockedCount(info.getBlockedCount());
            dto.setWaitedCount(info.getWaitedCount());
            dto.setSuspended(info.isSuspended());
            dto.setLockName(info.getLockName());
            dto.setLockOwnerId(info.getLockOwnerId());

            Thread thread = ThreadUtils.findThread(threadId);
            if (thread != null) {
                dto.setGroup(thread.getThreadGroup().getName());
                dto.setPriority(thread.getPriority());
                dto.setActive(thread.isAlive());
                dto.setDaemon(thread.isDaemon());
                dto.setInterrupted(thread.isInterrupted());
            }
            result.add(dto);
        }
        for (Long threadId : threadIds) {
            times.put(threadId, threadMXBean.getThreadCpuTime(threadId));
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
            dto.setCpuRate(((cpuEnd - times.get(dto.getThreadId())) / 1000000.0) / (end - start) * 100);
            dto.setUserTime(threadMXBean.getThreadUserTime(dto.getThreadId()));
            dto.setCpuTime(cpuEnd);
        }
        Collections.sort(result, Comparator.comparingDouble(ThreadInfoDTO::getCpuRate));
        Collections.reverse(result);
        return result;
    }
}
