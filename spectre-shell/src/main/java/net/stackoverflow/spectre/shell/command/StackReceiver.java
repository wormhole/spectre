package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.context.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;

import java.util.UUID;

/**
 * 打印线程堆栈
 *
 * @author wormhole
 */
public class StackReceiver implements Receiver {

    private final TransportClient client;

    private final SerializeManager serializeManager;

    public StackReceiver(TransportClient client, SerializeManager serializeManager) {
        this.client = client;
        this.serializeManager = serializeManager;
    }

    @Override
    public Object action(String... args) {
        BusinessRequest request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(args));
        client.sendTo(request);
        ResponseContext context = ResponseContext.getInstance();
        try {
            byte[] response = (byte[]) context.getResponse(request.getId());
            context.unwatch(request.getId());
            StackTraceElement[] stackTraceElements = serializeManager.deserialize(response, StackTraceElement[].class);
            renderStackTrace(stackTraceElements);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void renderStackTrace(StackTraceElement[] stackTraceElements) {
        if (stackTraceElements.length > 0) {
            System.out.println(stackTraceElements[0]);
        }
        for (int i = 1; i < stackTraceElements.length; i++) {
            System.out.println("\tat " + stackTraceElements[i]);
        }
    }
}
