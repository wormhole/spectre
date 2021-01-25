package net.stackoverflow.spectre.shell;

import com.sun.tools.attach.*;
import net.stackoverflow.spectre.common.command.Invoker;
import net.stackoverflow.spectre.shell.command.*;
import net.stackoverflow.spectre.transport.NettyTransportClient;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.exception.InActiveException;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 启动类
 *
 * @author wormhole
 */
public class ShellBootstrap {

    private static final Logger log = LoggerFactory.getLogger(ShellBootstrap.class);

    private final BufferedReader reader;

    private final SerializeManager serializeManager;

    private VirtualMachine vm;

    public ShellBootstrap() {
        InputStreamReader isr = new InputStreamReader(System.in);
        this.reader = new BufferedReader(isr);
        this.serializeManager = new JsonSerializeManager();
    }

    public static void main(String[] args) {
        ShellBootstrap shellBootstrap = new ShellBootstrap();
        try {
            AnsiConsole.systemInstall();
            shellBootstrap.loop(args[0], "127.0.0.1", 9966);
            AnsiConsole.systemUninstall();
        } catch (Exception e) {
            e.printStackTrace();
            shellBootstrap.exit(-1);
        }
        shellBootstrap.exit(0);
    }

    private VirtualMachine attach(String agentJar) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        System.out.print(Ansi.ansi().fgCyan());
        for (VirtualMachineDescriptor vmd : list) {
            String id = vmd.id();
            String name = vmd.displayName().split(" ")[0];
            System.out.printf("%-6s %s%n", id, name);
        }
        System.out.print(Ansi.ansi().reset().a("input pid: "));
        String pid = reader.readLine();
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent(agentJar);
        log.info("attach jvm pid:{}", pid);
        return vm;
    }

    private TransportClient connect(String ip, int port) {
        TransportClient client = new NettyTransportClient();
        client.connect(ip, port);
        return client;
    }

    private Invoker initCommand(TransportClient client) {
        ShellInvoker invoker = new ShellInvoker();
        invoker.addCommand(new ShellCommand("help", "Print help information", new HelpReceiver(invoker.getCommands())));
        invoker.addCommand(new ShellCommand("thread", "Print thread information, options [-b, -w]", new ThreadReceiver(client, serializeManager)));
        invoker.addCommand(new ShellCommand("memory", "Print memory information", new MemoryReceiver(client, serializeManager)));
        invoker.addCommand(new ShellCommand("os", "Print operating system information", new OsReceiver(client, serializeManager)));
        invoker.addCommand(new ShellCommand("jvm", "Print jvm information", new JvmReceiver(client, serializeManager)));
        invoker.addCommand(new ShellCommand("gc", "Print gc information", new GcReceiver(client, serializeManager)));
        invoker.addCommand(new ShellCommand("watch", "Watch variable", new WatchReceiver(client, serializeManager)));
        invoker.addCommand(new ShellCommand("shutdown", "Close spectre agent", new ShutdownReceiver(client, serializeManager)));
        invoker.addCommand(new ShellCommand("exit", "Close session and exit spectre", new ExitReceiver(client)));
        log.info("shell init commands");
        return invoker;
    }

    public void loop(String agentJar, String ip, int port) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        this.vm = attach(agentJar);
        TransportClient client = connect(ip, port);
        Invoker invoker = initCommand(client);

        new Banner().render();
        String cmd = null;
        try {
            do {
                System.out.print("[spectre@" + vm.id() + "]# ");
                cmd = reader.readLine();
                log.info("shell call command {}", cmd);
                invoker.call(cmd.trim().split("\\s+"));
            } while (!"exit".equals(cmd));
        } catch (InActiveException e) {
            System.out.println(Ansi.ansi().fgRed().a("connection is closed..."));
        }
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
