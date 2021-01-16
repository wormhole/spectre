package net.stackoverflow.spectre.common.model;

/**
 * 操作系统信息
 *
 * @author wormhole
 */
public class OsInfoDTO {

    private String name;

    private String version;

    private String arch;

    private Integer cores;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }
}
