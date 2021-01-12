package net.stackoverflow.spectre.transport.proto;

/**
 * 业务请求
 *
 * @author wormhole
 */
public class BusinessRequest {

    private String id;

    private byte[] request;

    public BusinessRequest(String id, byte[] request) {
        this.id = id;
        this.request = request;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getRequest() {
        return request;
    }

    public void setRequest(byte[] request) {
        this.request = request;
    }
}
