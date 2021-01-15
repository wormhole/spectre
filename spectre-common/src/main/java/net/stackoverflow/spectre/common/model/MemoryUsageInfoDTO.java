package net.stackoverflow.spectre.common.model;

/**
 * 内存使用情况
 *
 * @author wormhole
 */
public class MemoryUsageInfoDTO {

    private Long init;

    private Long used;

    private Long committed;

    private Long max;

    public MemoryUsageInfoDTO(){

    }

    public MemoryUsageInfoDTO(Long init, Long used, Long committed, Long max) {
        this.init = init;
        this.used = used;
        this.committed = committed;
        this.max = max;
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
