package net.stackoverflow.spectre.transport.exception;

/**
 * 连接不活跃异常
 *
 * @author wormhole
 */
public class InActiveException extends RuntimeException {

    public InActiveException() {

    }

    public InActiveException(String msg) {
        super(msg);
    }
}
