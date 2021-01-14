package net.stackoverflow.spectre.common.model;

/**
 * 线程信息
 *
 * @author wormhole
 */
public class ThreadInfoDTO {

    private Long threadId;

    private String threadName;

    private String threadState;

    private Long blockedCount;

    private Long blockedTime;

    private Long waitedCount;

    private Long waitedTime;

    private Boolean suspended;

    private Boolean inNative;

    private String lockName;

    private Long lockOwnerId;

    private Double cpuRate;

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getThreadState() {
        return threadState;
    }

    public void setThreadState(String threadState) {
        this.threadState = threadState;
    }

    public Long getBlockedCount() {
        return blockedCount;
    }

    public void setBlockedCount(Long blockedCount) {
        this.blockedCount = blockedCount;
    }

    public Long getBlockedTime() {
        return blockedTime;
    }

    public void setBlockedTime(Long blockedTime) {
        this.blockedTime = blockedTime;
    }

    public Long getWaitedCount() {
        return waitedCount;
    }

    public void setWaitedCount(Long waitedCount) {
        this.waitedCount = waitedCount;
    }

    public Long getWaitedTime() {
        return waitedTime;
    }

    public void setWaitedTime(Long waitedTime) {
        this.waitedTime = waitedTime;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Boolean getInNative() {
        return inNative;
    }

    public void setInNative(Boolean inNative) {
        this.inNative = inNative;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public Long getLockOwnerId() {
        return lockOwnerId;
    }

    public void setLockOwnerId(Long lockOwnerId) {
        this.lockOwnerId = lockOwnerId;
    }

    public Double getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(Double cpuRate) {
        this.cpuRate = cpuRate;
    }
}
