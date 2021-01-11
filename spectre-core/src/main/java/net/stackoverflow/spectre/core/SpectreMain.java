package net.stackoverflow.spectre.core;

import com.sun.tools.attach.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 主类
 *
 * @author wormhole
 */
public class SpectreMain {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vmd : list) {
            System.out.printf("%s %s %n", vmd.id(), vmd.displayName());
        }
        System.out.print("input pid:");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(isr);
        String pid = reader.readLine();
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(args[0]);
        vm.detach();
    }
}
