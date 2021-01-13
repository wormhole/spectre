package net.stackoverflow.spectre.shell;

import com.sun.tools.attach.*;
import net.stackoverflow.spectre.shell.command.ExitCommand;
import net.stackoverflow.spectre.shell.command.LsThreadsCommand;
import net.stackoverflow.spectre.shell.command.SpectreInvoker;
import net.stackoverflow.spectre.shell.command.SpectreReceiver;
import net.stackoverflow.spectre.transport.NettyTransportClient;
import net.stackoverflow.spectre.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 主类
 *
 * @author wormhole
 */
public class Bootstrap {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

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

        TransportClient client = new NettyTransportClient();
        client.connect("127.0.0.1", 9966);

        SpectreReceiver receiver = new SpectreReceiver(client);
        SpectreInvoker invoker = new SpectreInvoker();
        invoker.addCommand(new LsThreadsCommand("ls threads", receiver));
        invoker.addCommand(new ExitCommand("exit", receiver));

        String cmd = null;
        do {
            cmd = reader.readLine();
            Object result = invoker.call(cmd.trim());
            System.out.println(result);
        } while (!"exit".equals(cmd));

        client.close();
        vm.detach();
    }
}
