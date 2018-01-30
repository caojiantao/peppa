package com.cjt.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author caojiantao
 * @create 2017-08-11 00:37:25
 * @desc 异常工具类
 */
public class ExceptionUtil {

    /**
     * 异常转换为详情字符串
     */
    public static String toDetailStr(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }
}
