package net.stackoverflow.spectre.agent.receiver;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.ThreadInfo;
import net.stackoverflow.spectre.common.util.ThreadUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;

/**
 * 线程命令接收者
 *
 * @author wormhole
 */
public class ThreadReceiver implements Receiver {

    @Override
    public Object action(String... args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        List<ThreadInfo> result = new ArrayList<>();
        Map<Long, Long> times = new HashMap<>();

        if (threadMXBean.isThreadCpuTimeSupported()) {
            if (!threadMXBean.isThreadCpuTimeEnabled()) {
                threadMXBean.setThreadCpuTimeEnabled(true);
            }
        }
        long[] threadIds = threadMXBean.getAllThreadIds();
        for (Long threadId : threadIds) {
            java.lang.management.ThreadInfo info = threadMXBean.getThreadInfo(threadId);
            if (!filter(info, args)) {
                continue;
            }
            ThreadInfo dto = new ThreadInfo();
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
        for (ThreadInfo dto : result) {
            long cpuEnd = threadMXBean.getThreadCpuTime(dto.getThreadId());
            dto.setCpuRate(((cpuEnd - times.get(dto.getThreadId())) / 1000000.0) / (end - start) * 100);
            dto.setUserTime(threadMXBean.getThreadUserTime(dto.getThreadId()));
            dto.setCpuTime(cpuEnd);
        }
        Collections.sort(result, Comparator.comparingDouble(ThreadInfo::getCpuRate));
        Collections.reverse(result);
        return result;
    }

    private boolean filter(java.lang.management.ThreadInfo info, String... options) {
        List<String> ops = Arrays.asList(options);
        Thread.State state = info.getThreadState();
        if (options.length == 1) {
            return true;
        } else if (ops.contains("-b") && state.equals(Thread.State.BLOCKED)) {
            return true;
        } else if (ops.contains("-w") && (state.equals(Thread.State.WAITING) || state.equals(Thread.State.TIMED_WAITING))) {
            return true;
        } else {
            return false;
        }
    }
}
