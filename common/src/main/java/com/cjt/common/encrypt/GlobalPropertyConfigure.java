package com.cjt.common.encrypt;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class GlobalPropertyConfigure extends
        PropertyPlaceholderConfigurer {

  @Override
  protected String convertProperty(String propertyName, String propertyValue) {
    return isEncryptProp(propertyName) ? EncryptUtil.decrypt(propertyValue) : propertyValue;
  }

  /**
   * 判断该属性值是否需要加密处理
   */
  private boolean isEncryptProp(String propertyName) {
    return propertyName.endsWith("password");
  }
}
