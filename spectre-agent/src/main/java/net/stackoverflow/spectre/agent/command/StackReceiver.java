package net.stackoverflow.spectre.agent.command;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.util.ThreadUtils;

/**
 * 打印线程堆栈命令接收者
 *
 * @author wormhole
 */
public class StackReceiver implements Receiver {


    @Override
    public Object action(String... args) {
        Thread thread = ThreadUtils.findThread(Long.parseLong(args[0]));
        if (thread != null) {
            return thread.getStackTrace();
        } else {
            return null;
        }
    }
}
