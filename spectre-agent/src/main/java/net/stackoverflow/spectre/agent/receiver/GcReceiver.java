package net.stackoverflow.spectre.agent.receiver;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.GcInfo;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * gc命令receiver
 *
 * @author wormhole
 */
public class GcReceiver implements Receiver {
    @Override
    public Object action(Object... args) {
        List<GarbageCollectorMXBean> gcMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        List<GcInfo> gcInfos = new ArrayList<>();
        for (GarbageCollectorMXBean gcMXBean : gcMXBeans) {
            GcInfo info = new GcInfo();
            info.setName(gcMXBean.getName());
            info.setCount(gcMXBean.getCollectionCount());
            info.setTime(gcMXBean.getCollectionTime());
            info.setPoolNames(Arrays.asList(gcMXBean.getMemoryPoolNames()));
            gcInfos.add(info);
        }
        return gcInfos;
    }
}
