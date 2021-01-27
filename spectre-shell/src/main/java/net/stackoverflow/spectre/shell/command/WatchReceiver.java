package net.stackoverflow.spectre.shell.command;

import net.stackoverflow.spectre.common.command.Receiver;
import net.stackoverflow.spectre.common.model.WatchInfo;
import net.stackoverflow.spectre.transport.TransportClient;
import net.stackoverflow.spectre.transport.context.ResponseContext;
import net.stackoverflow.spectre.transport.proto.BusinessRequest;
import net.stackoverflow.spectre.transport.serialize.SerializeManager;
import org.fusesource.jansi.Ansi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

/**
 * watch命令
 *
 * @author wormhole
 */
public class WatchReceiver implements Receiver {

    private final TransportClient client;

    private final SerializeManager serializeManager;

    public WatchReceiver(TransportClient client, SerializeManager serializeManager) {
        this.client = client;
        this.serializeManager = serializeManager;
    }

    @Override
    public Object action(String... args) {
        BusinessRequest request = new BusinessRequest(args[1] + "." + args[2], serializeManager.serialize(args));
        client.sendTo(request);
        renderWatchTitle();
        Thread thread = new WatchThread(request.getId(), serializeManager);
        thread.setName("watch-render-thread");
        thread.start();
        String command = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            do {
                command = reader.readLine().trim();
            } while (!"q".equals(command));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            thread.interrupt();
        }
        request = new BusinessRequest(UUID.randomUUID().toString(), serializeManager.serialize(new String[]{"unwatch", args[1], args[2]}));
        client.sendTo(request);
        ResponseContext context = ResponseContext.getInstance();
        context.unwatch(request.getId());
        return null;
    }

    private void renderWatchTitle() {
        System.out.print(Ansi.ansi().fgYellow());
        System.out.println("输入q按下回车退出watch");
        System.out.print(Ansi.ansi().reset());
    }

    static class WatchThread extends Thread {

        private String requestId;

        private SerializeManager serializeManager;

        public WatchThread(String requestId, SerializeManager serializeManager) {
            this.requestId = requestId;
            this.serializeManager = serializeManager;
        }

        @Override
        public void run() {
            ResponseContext context = ResponseContext.getInstance();
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    byte[] response = (byte[]) context.getResponse(requestId);
                    if (response != null) {
                        WatchInfo result = serializeManager.deserialize(response, WatchInfo.class);
                        for (int i = 0; i < result.getArguments().size(); i++) {
                            System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
                            System.out.print("argument[" + i + "]");
                            System.out.print(Ansi.ansi().reset());
                            System.out.print("   ");
                            System.out.println(result.getArguments().get(i));
                        }
                        System.out.print(Ansi.ansi().fgBlack().bg(Ansi.Color.WHITE).bold());
                        System.out.print("     return");
                        System.out.print(Ansi.ansi().reset());
                        System.out.print("   ");
                        System.out.println(result.getRet());
                        System.out.println();
                    }
                }
            } catch (InterruptedException e) {

            } finally {
                context.unwatch(requestId);
            }
        }
    }
}
