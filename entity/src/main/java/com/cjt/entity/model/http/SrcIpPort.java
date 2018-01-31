package com.cjt.entity.model.http;

/**
 * @author caojiantao
 */
public class SrcIpPort {

    private Integer id;

    private String srcUrl;

    private String ip;

    private Integer port;

    public SrcIpPort() {
    }

    public SrcIpPort(String srcUrl, String ip, Integer port) {
        this.srcUrl = srcUrl;
        this.ip = ip;
        this.port = port;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
