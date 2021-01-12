package net.stackoverflow.spectre.transport.proto;

/**
 * 业务响应
 *
 * @author wormhole
 */
public class BusinessResponse {

    private String id;

    private byte[] response;

    public BusinessResponse(String id, byte[] response) {
        this.id = id;
        this.response = response;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getResponse() {
        return response;
    }

    public void setResponse(byte[] response) {
        this.response = response;
    }
}
