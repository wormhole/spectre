package net.stackoverflow.spectre.agent.receiver;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.RuntimeInfo;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * jvm命令接收者
 *
 * @author wormhole
 */
public class JvmReceiver implements Receiver {
    @Override
    public Object action(String... args) {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        RuntimeInfo info = new RuntimeInfo();
        info.setJvmName(runtime.getVmName());
        info.setJvmVendor(runtime.getVmVendor());
        info.setJvmVersion(runtime.getVmVersion());
        info.setSpecName(runtime.getSpecName());
        info.setSpecVendor(runtime.getSpecVendor());
        info.setSpecVersion(runtime.getSpecVersion());
        info.setStartTime(runtime.getStartTime());
        info.setUpTime(runtime.getUptime());
        info.setInputArguments(runtime.getInputArguments());
        info.setClassPath(runtime.getClassPath());
        info.setBootClassPath(runtime.getBootClassPath());
        info.setLibraryPath(runtime.getLibraryPath());
        return info;
    }
}
