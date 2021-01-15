package net.stackoverflow.spectre.common.model;

import java.util.List;

/**
 * 内存信息
 *
 * @author wormhole
 */
public class MemoryInfoDTO {

    private MemoryUsageInfoDTO heap;

    private MemoryUsageInfoDTO noHeap;

    private List<MemoryPoolInfoDTO> pools;

    public MemoryUsageInfoDTO getHeap() {
        return heap;
    }

    public void setHeap(MemoryUsageInfoDTO heap) {
        this.heap = heap;
    }

    public MemoryUsageInfoDTO getNoHeap() {
        return noHeap;
    }

    public void setNoHeap(MemoryUsageInfoDTO noHeap) {
        this.noHeap = noHeap;
    }

    public List<MemoryPoolInfoDTO> getPools() {
        return pools;
    }

    public void setPools(List<MemoryPoolInfoDTO> pools) {
        this.pools = pools;
    }
}
