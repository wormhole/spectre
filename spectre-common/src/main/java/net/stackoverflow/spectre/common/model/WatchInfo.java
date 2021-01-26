package net.stackoverflow.spectre.common.model;

import java.util.List;

/**
 * watch信息
 *
 * @author wormhole
 */
public class WatchInfo {

    private List<String> arguments;

    private String ret;

    public WatchInfo() {

    }

    public WatchInfo(List<String> arguments, String ret) {
        this.arguments = arguments;
        this.ret = ret;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }
}
