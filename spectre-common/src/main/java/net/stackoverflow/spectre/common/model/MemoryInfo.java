package net.stackoverflow.spectre.common.model;

import java.util.List;

/**
 * 内存信息
 *
 * @author wormhole
 */
public class MemoryInfo {

    private MemoryUsageInfo heap;

    private MemoryUsageInfo noHeap;

    private List<MemoryPoolInfo> pools;

    public MemoryUsageInfo getHeap() {
        return heap;
    }

    public void setHeap(MemoryUsageInfo heap) {
        this.heap = heap;
    }

    public MemoryUsageInfo getNoHeap() {
        return noHeap;
    }

    public void setNoHeap(MemoryUsageInfo noHeap) {
        this.noHeap = noHeap;
    }

    public List<MemoryPoolInfo> getPools() {
        return pools;
    }

    public void setPools(List<MemoryPoolInfo> pools) {
        this.pools = pools;
    }
}
