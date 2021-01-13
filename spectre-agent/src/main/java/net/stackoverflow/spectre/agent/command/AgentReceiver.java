package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.transport.command.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 命令接收者实现
 *
 * @author wormhole
 */
public class AgentReceiver implements Receiver {

    private static final Logger log = LoggerFactory.getLogger(AgentReceiver.class);

    public Map<Long, ThreadInfo> threads() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Map<Long, ThreadInfo> result = new LinkedHashMap<>();
        for (Long threadId : threadMXBean.getAllThreadIds()) {
            ThreadInfo info = threadMXBean.getThreadInfo(threadId);
            result.put(threadId, info);
        }
        return result;
    }
}
