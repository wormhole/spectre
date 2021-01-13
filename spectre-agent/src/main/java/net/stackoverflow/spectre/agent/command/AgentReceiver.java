package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.transport.command.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;

/**
 * 命令接收者实现
 *
 * @author wormhole
 */
public class AgentReceiver implements Receiver {

    private static final Logger log = LoggerFactory.getLogger(AgentReceiver.class);

    public Map<Long, String> lsThreads() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        Map<Long, String> result = new HashMap<>();
        for (Long threadId : threadMXBean.getAllThreadIds()) {
            result.put(threadId, threadMXBean.getThreadInfo(threadId).getThreadName());
        }
        return result;
    }
}
