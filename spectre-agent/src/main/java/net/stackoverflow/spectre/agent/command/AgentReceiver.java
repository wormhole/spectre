package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.model.ThreadInfoDTO;
import net.stackoverflow.spectre.transport.command.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 命令接收者实现
 *
 * @author wormhole
 */
public class AgentReceiver implements Receiver {

    private static final Logger log = LoggerFactory.getLogger(AgentReceiver.class);

    private final DecimalFormat df = new DecimalFormat("#.00");

    public List<ThreadInfoDTO> threads() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        List<ThreadInfoDTO> result = new ArrayList<>();
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
                long start = System.currentTimeMillis();
                long cpuStart = threadMXBean.getThreadCpuTime(threadId);
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long end = System.currentTimeMillis();
                long cpuEnd = threadMXBean.getThreadCpuTime(threadId);
                dto.setCpuRate(df.format((cpuEnd - cpuStart) / 1000.0 / (end - start)));
            }
            result.add(dto);
        }
        return result;
    }
}
