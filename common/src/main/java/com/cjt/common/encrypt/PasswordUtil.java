package com.cjt.common.encrypt;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 作为加密组件，可能根据环境配置加密参数，可以注入到Spring中
 *
 * @author caojiantao
 */
@Component
public class PasswordUtil {

    @Value("${shiro_salt}")
    private String salt;

    @Value("${shiro_algorithm}")
    private String algorithmName;

    @Value("${shiro_iterations}")
    private int iterations;

    public String encryptPassword(String password) {
        return new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(salt),
                iterations
        ).toHex();
    }
}
