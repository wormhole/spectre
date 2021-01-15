package net.stackoverflow.spectre.shell;

import com.sun.tools.attach.*;
import net.stackoverflow.spectre.common.command.Invoker;
import net.stackoverflow.spectre.common.util.ColorUtils;
import net.stackoverflow.spectre.shell.command.ExitCommand;
import net.stackoverflow.spectre.shell.command.HelpCommand;
import net.stackoverflow.spectre.shell.command.ThreadCommand;
import net.stackoverflow.spectre.shell.receiver.ExitReceiver;
import net.stackoverflow.spectre.shell.receiver.HelpReceiver;
import net.stackoverflow.spectre.shell.receiver.ThreadReceiver;
import net.stackoverflow.spectre.transport.NettyTransportClient;
import net.stackoverflow.spectre.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * 启动类
 *
 * @author wormhole
 */
public class ShellBootstrap {

    private static final Logger log = LoggerFactory.getLogger(ShellBootstrap.class);

    private final BufferedReader reader;

    private VirtualMachine vm;

    public ShellBootstrap() {
        InputStreamReader isr = new InputStreamReader(System.in);
        this.reader = new BufferedReader(isr);
    }

    public static void main(String[] args) {
        ShellBootstrap shellBootstrap = new ShellBootstrap();
        try {
            shellBootstrap.loop(args[0], "127.0.0.1", 9966);
        } catch (Exception e) {
            e.printStackTrace();
            shellBootstrap.exit(-1);
        }
        shellBootstrap.exit(0);
    }

    protected static String bytesToMB(long bytes) {
        NumberFormat fmtI = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ENGLISH));
        return fmtI.format((long)(bytes / 1024 / 1024)) + " MB";
    }

    private VirtualMachine attach(String agentJar) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        ColorUtils.color(ColorUtils.F_CYAN);
        for (VirtualMachineDescriptor vmd : list) {
            String id = vmd.id();
            String name = vmd.displayName().split(" ")[0];
            System.out.printf("%-6s %s%n", id, name);
        }
        ColorUtils.color(ColorUtils.ORIGINAL);
        System.out.print("input pid: ");
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
        invoker.addCommand(new HelpCommand("help", "Print help information", new HelpReceiver(invoker.getCommands())));
        invoker.addCommand(new ThreadCommand("thread", "Print thread information", new ThreadReceiver(client)));
        invoker.addCommand(new ExitCommand("exit", "Close session and exit spectre", new ExitReceiver(client)));
        return invoker;
    }

    public void loop(String agentJar, String ip, int port) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        this.vm = attach(agentJar);
        TransportClient client = connect(ip, port);
        Invoker invoker = initCommand(client);

        new Banner().render();
        String cmd = null;
        do {
            System.out.print("[spectre@" + vm.id() + "]# ");
            cmd = reader.readLine();
            invoker.call(cmd.trim());
        } while (!"exit".equals(cmd));
    }

    private void exit(int status) {
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
