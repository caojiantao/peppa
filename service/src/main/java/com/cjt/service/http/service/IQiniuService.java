package com.cjt.service.http.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author caojiantao
 */
public interface IQiniuService {

    String uploadFile(byte[] byptes, String bucket, String key);

    JSONObject listFiles(String bucket, int page, int pagesize);

    boolean removeFile(String bucket, String key);
}
