package com.cjt.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author caojiantao
 * @since 2017-08-11 00:37:25
 */
public class ExceptionUtils {

    /**
     * 异常转换为详情字符串
     */
    public static String toDetailStr(Exception ex) {
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw, true)) {
            ex.printStackTrace(pw);
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
