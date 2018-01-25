package com.cjt.common.encrypt;

import com.cjt.common.util.ExceptionUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;

/**
 * 简单的base64加密解密(主要用于properties中密码的加密)
 *
 * @author caojiantao
 */
public class EncryptUtil {

    private final static Logger logger = LogManager.getLogger(EncryptUtil.class);

    public static String encrypt(String value) {
        try {
            value = DatatypeConverter.printBase64Binary(value.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(ExceptionUtil.toDetailStr(e));
        }
        return value;
    }

    public static String decrypt(String value) {
        byte[] bytes = DatatypeConverter.parseBase64Binary(value);
        try {
            value = new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(ExceptionUtil.toDetailStr(e));
        }
        return value;
    }

    public static void main(String[] args) {
        System.out.println(decrypt("cm9vdDEyMw=="));
    }
}