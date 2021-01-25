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
    public Object action(Object... args) {
        String[] arguments = (String[]) args[1];
        Thread thread = ThreadUtils.findThread(Long.valueOf(arguments[1]));
        StackTraceElement[] stackTraceElements = thread.getStackTrace();
        return stackTraceElements;
    }
}
