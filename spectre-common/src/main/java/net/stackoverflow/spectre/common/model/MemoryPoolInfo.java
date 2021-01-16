package net.stackoverflow.spectre.common.model;

/**
 * 内存池信息
 *
 * @author wormhole
 */
public class MemoryPoolInfo {

    private String name;

    private String type;

    private Long init;

    private Long used;

    private Long committed;

    private Long max;

    public MemoryPoolInfo() {

    }

    public MemoryPoolInfo(String name, String type, Long init, Long used, Long committed, Long max) {
        this.name = name;
        this.type = type;
        this.init = init;
        this.used = used;
        this.committed = committed;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getInit() {
        return init;
    }

    public void setInit(Long init) {
        this.init = init;
    }

    public Long getUsed() {
        return used;
    }

    public void setUsed(Long used) {
        this.used = used;
    }

    public Long getCommitted() {
        return committed;
    }

    public void setCommitted(Long committed) {
        this.committed = committed;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }
}
