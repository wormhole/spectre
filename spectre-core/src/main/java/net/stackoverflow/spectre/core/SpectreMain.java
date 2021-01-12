package net.stackoverflow.spectre.core;

import com.sun.tools.attach.*;
import net.stackoverflow.spectre.transport.NettyTransportClient;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

/**
 * 主类
 *
 * @author wormhole
 */
public class SpectreMain {

    private static final Logger log = LoggerFactory.getLogger(SpectreMain.class);

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
        SerializeManager serializeManager = new JsonSerializeManager();

        ResponseFuture future = client.sendTo(new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize("hello world")));
        BusinessResponse response = future.getResponse(-1);
        System.out.println(serializeManager.deserialize(response.getResponse(), String.class));

        client.sendTo(new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize("exit")));
        client.close();
        vm.detach();
    }
}
