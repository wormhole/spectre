package net.stackoverflow.spectre.transport.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ResponseFuture上下文
 *
 * @author wormhole
 */
public class ResponseFutureContext {

    private static final Logger log = LoggerFactory.getLogger(ResponseFutureContext.class);

    private final Map<String, ResponseFuture> futurePool;

    private static ResponseFutureContext instance;

    public static ResponseFutureContext getInstance() {
        if (instance == null) {
            synchronized (ResponseFutureContext.class) {
                if (instance == null) {
                    instance = new ResponseFutureContext();
                }
            }
        }
        return instance;
    }

    private ResponseFutureContext() {
        this.futurePool = new ConcurrentHashMap<>();
    }

    /**
     * 生成ResponseFuture
     *
     * @param requestId 请求唯一标识
     * @return ResponseFuture对象
     */
    public ResponseFuture createFuture(String requestId) {
        ResponseFuture future = new ResponseFuture(requestId);
        futurePool.put(requestId, future);
        return future;
    }

    /**
     * 设置响应结果
     *
     * @param response 响应对象
     * @parma requestId 请求id
     */
    public void setResponse(String requestId, Object response) {
        ResponseFuture future = futurePool.get(requestId);
        if (future != null) {
            future.setResponse(response);
        }
    }

    /**
     * 移除ResponseFuture
     *
     * @param requestId 请求唯一标识
     */
    public void removeFuture(String requestId) {
        futurePool.remove(requestId);
    }
}
