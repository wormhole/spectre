package net.stackoverflow.spectre.shell.receiver;

import com.sun.tools.attach.VirtualMachine;
import net.stackoverflow.spectre.common.command.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.tools.attach.HotSpotVirtualMachine;

import java.io.File;
import java.io.IOException;

/**
 * dump命令接收者
 *
 * @author wormhole
 */
public class DumpReceiver implements Receiver {

    private static final Logger log = LoggerFactory.getLogger(DumpReceiver.class);

    private VirtualMachine vm;

    public DumpReceiver(VirtualMachine vm) {
        this.vm = vm;
    }

    @Override
    public Object action(String... args) {
        if (vm instanceof HotSpotVirtualMachine) {
            HotSpotVirtualMachine hsvm = (HotSpotVirtualMachine) vm;
            try {
                String currentDir = System.getProperty("user.dir");
                File file = new File(currentDir, "spectre-output/" + args[2]);
                hsvm.dumpHeap(file.getAbsolutePath());
                System.out.println("filepath: " + file.getAbsolutePath());
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return null;
    }
}
