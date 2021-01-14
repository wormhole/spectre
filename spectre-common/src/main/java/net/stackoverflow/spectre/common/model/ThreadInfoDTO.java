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

    private Double cpuRate;

    private String group;

    private Integer priority;

    private Boolean active;

    private Boolean daemon;

    private Boolean interrupted;

    private Boolean suspended;

    private Long blockedCount;

    private Long waitedCount;

    private Long lockOwnerId;

    private String lockName;

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

    public Double getCpuRate() {
        return cpuRate;
    }

    public void setCpuRate(Double cpuRate) {
        this.cpuRate = cpuRate;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDaemon() {
        return daemon;
    }

    public void setDaemon(Boolean daemon) {
        this.daemon = daemon;
    }

    public Boolean getInterrupted() {
        return interrupted;
    }

    public void setInterrupted(Boolean interrupted) {
        this.interrupted = interrupted;
    }

    public Boolean getSuspended() {
        return suspended;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Long getBlockedCount() {
        return blockedCount;
    }

    public void setBlockedCount(Long blockedCount) {
        this.blockedCount = blockedCount;
    }

    public Long getWaitedCount() {
        return waitedCount;
    }

    public void setWaitedCount(Long waitedCount) {
        this.waitedCount = waitedCount;
    }

    public Long getLockOwnerId() {
        return lockOwnerId;
    }

    public void setLockOwnerId(Long lockOwnerId) {
        this.lockOwnerId = lockOwnerId;
    }

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }
}
