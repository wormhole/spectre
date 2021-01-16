package net.stackoverflow.spectre.common.model;

import java.util.List;

/**
 * gc信息
 *
 * @author wormhole
 */
public class GcInfo {

    private String name;

    private Long count;

    private Long time;

    private List<String> poolNames;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public List<String> getPoolNames() {
        return poolNames;
    }

    public void setPoolNames(List<String> poolNames) {
        this.poolNames = poolNames;
    }
}
