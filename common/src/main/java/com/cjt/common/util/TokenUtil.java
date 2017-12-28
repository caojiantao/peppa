package com.cjt.common.util;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * token操作工具类
 *
 * @author caojiantao
 */
@Component
public class TokenUtil {

    private static final Logger logger = LogManager.getLogger(TokenUtil.class);

    /**
     * 默认密钥
     */
    @Value("${token_secret}")
    private String secret;

    /**
     * 生成token（注意注意：key为exp必须为非负数！！！）
     */
    public String getToken(Map<String, Object> claims) {
        String token = "";
        try {
            JWTSigner signer = new JWTSigner(secret);
            token = signer.sign(claims);
        } catch (Exception e) {
            logger.error(ExceptionUtil.toDetailStr(e));
        }
        return token;
    }

    /**
     * 解析token
     */
    public Object parseToken(String token, String key) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Object object = null;
        try {
            JWTVerifier verifier = new JWTVerifier(secret);
            Map<String, Object> claims = verifier.verify(token);
            object = claims.get(key);
        } catch (Exception e) {
            logger.error(ExceptionUtil.toDetailStr(e));
        }
        return object;
    }
}
