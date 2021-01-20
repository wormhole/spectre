package net.stackoverflow.spectre.transport.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 响应上下文
 *
 * @author wormhole
 */
public class ResponseContext {

    private static final Logger log = LoggerFactory.getLogger(ResponseContext.class);

    private final Map<String, BlockingQueue<Object>> context;

    private static ResponseContext instance;

    private ResponseContext() {
        this.context = new ConcurrentHashMap<>();
    }

    public static ResponseContext getInstance() {
        if (instance == null) {
            synchronized (ResponseContext.class) {
                if (instance == null) {
                    instance = new ResponseContext();
                }
            }
        }
        return instance;
    }

    public synchronized boolean watch(String requestId) {
        BlockingQueue<Object> queue = context.get(requestId);
        if (queue != null) {
            return false;
        } else {
            queue = new LinkedBlockingQueue<>();
            context.put(requestId, queue);
            return true;
        }
    }

    public void unwatch(String requestId) {
        context.remove(requestId);
    }

    public boolean setResponse(String requestId, Object response) {
        BlockingQueue<Object> queue = context.get(requestId);
        if (queue == null) {
            return false;
        } else {
            //队列满时直接丢弃
            return queue.offer(response);
        }
    }

    public Object getResponse(String requestId) {
        BlockingQueue<Object> queue = context.get(requestId);
        if (queue == null) {
            return null;
        } else {
            Object response = null;
            try {
                response = queue.take();
            } catch (InterruptedException e) {
                log.error("ResponseContext fail to get response", e);
            }
            return response;
        }
    }
}
