package net.stackoverflow.spectre.agent.receiver;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.OsInfoDTO;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

/**
 * os命令接收者
 *
 * @author wormhole
 */
public class OsReceiver implements Receiver {

    @Override
    public Object action(Object... args) {
        OperatingSystemMXBean osMXBean = ManagementFactory.getOperatingSystemMXBean();
        OsInfoDTO dto = new OsInfoDTO();
        dto.setName(osMXBean.getName());
        dto.setVersion(osMXBean.getVersion());
        dto.setArch(osMXBean.getArch());
        dto.setCores(osMXBean.getAvailableProcessors());
        return dto;
    }
}
