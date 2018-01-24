package com.cjt.common.util;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author caojiantao
 */
public class JsonUtils {

    public static <T> JSONObject toPageData(List<T> data, int total) {
        JSONObject result = new JSONObject();
        result.put("data", data);
        result.put("total", total);
        return result;
    }
}
