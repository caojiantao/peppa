package com.cjt.dao.http;

import com.cjt.entity.model.http.SrcIpPort;

import java.util.List;

/**
 * @author caojiantao
 */
public interface ISrcIpPortDao {

    /**
     * 添加 资源-IP-端口 对应关系
     *
     * @param srcIpPort 对应关系实体类
     */
    void saveSrcIpPort(SrcIpPort srcIpPort);

    /**
     * 删除该资源的对应关系
     *
     * @param srcUrl 资源地址
     * @return 影响行数
     */
    int removeSrcIpPort(String srcUrl);

    /**
     * 获取目前所有对应关系
     *
     * @return 所有对应关系
     */
    List<SrcIpPort> listSrcIpPort();
}
