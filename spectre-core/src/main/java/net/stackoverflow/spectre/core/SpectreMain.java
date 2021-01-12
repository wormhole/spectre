package net.stackoverflow.spectre.core;

import com.sun.tools.attach.*;
import net.stackoverflow.spectre.transport.NettyTransportClient;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.future.ResponseFuture;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.proto.BusinessResponse;
import net.stackoverflow.spectre.transport.serialize.JsonSerializeManager;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * 主类
 *
 * @author wormhole
 */
public class SpectreMain {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException, InterruptedException {
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

        CountDownLatch countDownLatch = new CountDownLatch(1);
        TransportClient client = new NettyTransportClient();
        client.connect("127.0.0.1", 9966, countDownLatch);
        countDownLatch.await();
        SerializeManager serializeManager = new JsonSerializeManager();
        ResponseFuture future = client.sendTo(new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize("hello world")));
        BusinessResponse response = future.getResponse(-1);
        System.out.println(serializeManager.deserialize(response.getResponse(), String.class));
        vm.detach();
    }
}
