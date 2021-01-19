package net.stackoverflow.spectre.common.model;

import java.util.List;

/**
 * watch信息
 *
 * @author wormhole
 */
public class WatchInfo {

    private List<Object> arguments;

    private Object ret;

    public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }
}
