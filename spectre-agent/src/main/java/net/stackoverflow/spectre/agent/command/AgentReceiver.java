package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.model.ThreadInfoDTO;
import net.stackoverflow.spectre.transport.command.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

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
            dto.setUserTime(threadMXBean.getThreadUserTime(threadId));
            if (threadMXBean.isThreadCpuTimeSupported()) {
                if (threadMXBean.isThreadCpuTimeEnabled()) {
                    dto.setCpuTime(threadMXBean.getThreadCpuTime(threadId));
                } else {
                    threadMXBean.setThreadCpuTimeEnabled(true);
                    dto.setCpuTime(threadMXBean.getThreadCpuTime(threadId));
                }
            } else {
                dto.setCpuTime(-1L);
            }

            result.add(dto);
        }
        return result;
    }
}
