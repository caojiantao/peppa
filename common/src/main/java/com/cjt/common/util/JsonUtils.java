package com.cjt.common.util;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author caojiantao
 */
public class JsonUtils {

    /**
     * 组装分页结果JSON
     *
     * @param data  当前页数据
     * @param total 总条数
     * @param <T>   数据类型
     * @return JSON结果
     */
    public static <T> JSONObject toPageData(List<T> data, int total) {
        JSONObject result = new JSONObject();
        result.put("data", data);
        result.put("total", total);
        return result;
    }
}
