package com.cjt.ssm.encrypt;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;

/**
 * 简单的base64加密解密
 *
 * @author caojiantao
 */
public class EncryptUtil {

  /**
   * 加密
   *
   * @param value
   * @return
   */
  public static String encrypt(String value) {
    try {
      value = DatatypeConverter.printBase64Binary(value.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return value;
  }

  /**
   * 解密
   *
   * @param value
   * @return
   */
  public static String decrypt(String value) {
    byte[] bytes = DatatypeConverter.parseBase64Binary(value);
    try {
      value = new String(bytes, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return value;
  }

  public static void main(String[] args) {
    String str = "Cjt00382114.";
    System.err.println(encrypt(str));
  }
}