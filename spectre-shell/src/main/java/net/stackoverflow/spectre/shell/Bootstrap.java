package net.stackoverflow.spectre.shell;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import net.stackoverflow.spectre.shell.command.*;
import net.stackoverflow.spectre.shell.util.PrintUtils;
import net.stackoverflow.spectre.transport.NettyTransportClient;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.command.Invoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 启动类
 *
 * @author wormhole
 */
public class Bootstrap {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    private final BufferedReader reader;

    private VirtualMachine vm;

    private TransportClient client;

    public Bootstrap() {
        InputStreamReader isr = new InputStreamReader(System.in);
        this.reader = new BufferedReader(isr);
    }

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.loop(args[0], "127.0.0.1", 9966);
        } catch (Exception e) {
            e.printStackTrace();
            bootstrap.exit(-1);
        }
        bootstrap.exit(0);
    }

    private VirtualMachine attach(String agentJar) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        PrintUtils.printVirtualMachines();
        String pid = reader.readLine();
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(agentJar);
        return vm;
    }

    private TransportClient connect(String ip, int port) {
        TransportClient client = new NettyTransportClient();
        client.connect(ip, port);
        return client;
    }

    private Invoker initCommand(TransportClient client) {
        Invoker invoker = new SpectreInvoker();
        SpectreReceiver receiver = new SpectreReceiver(client);
        invoker.addCommand(new HelpCommand("help", receiver));
        invoker.addCommand(new ThreadCommand("thread", receiver));
        invoker.addCommand(new ExitCommand("exit", receiver));
        return invoker;
    }

    public void loop(String agentJar, String ip, int port) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        this.vm = attach(agentJar);
        this.client = connect(ip, port);
        Invoker invoker = initCommand(this.client);

        PrintUtils.printBanner(vm.id());
        String cmd = null;
        do {
            PrintUtils.printSession(vm.id());
            cmd = reader.readLine();
            invoker.call(cmd.trim());
        } while (!"exit".equals(cmd));
    }

    public void exit(int status) {
        if (this.vm != null) {
            try {
                this.vm.detach();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        System.exit(status);
    }
}
