package com.cjt.ssm.controller;

import com.cjt.ssm.dto.ResultMsg;

import java.util.List;

/**
 * @author caojiantao
 * @create 2017-07-22 18:41:02
 * @desc controller的基类
 */
public class BaseController {

    public <T> ResultMsg initSuccessMsg(List<T> data){
        return new ResultMsg<T>(true, data);
    }

    public ResultMsg initFailedMsg(String msg){
        return new ResultMsg(false, msg);
    }
}
