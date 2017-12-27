package com.cjt.common.util;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * token操作工具类
 *
 * @author caojiantao
 */
@Component
public class TokenUtil {

    private static final Logger logger = LogManager.getLogger(TokenUtil.class);

    private static final String EXP = "exp";
    private static final String PAYLOAD = "payload";

    /**
     * 默认密钥
     */
    @Value("${token_secret}")
    private String secret;

    public String getToken(Map<String, Object> map) {
        return getToken(map, -1);
    }

    /**
     * 生成token（mmp，key为exp必须为非负数！！！）
     */
    public String getToken(Map<String, Object> claims, long maxAge) {
        String token = "";
        try {
            JWTSigner signer = new JWTSigner(secret);
            claims.put("maxAge", maxAge);
            initClaims(claims);
            token = signer.sign(claims);
        } catch (Exception e) {
            logger.error(ExceptionUtil.toDetailStr(e));
        }
        return token;
    }

    private void initClaims(Map<String, Object> map) throws IOException {
        if (map != null && !map.isEmpty()) {
            Set<Map.Entry<String, Object>> entries = map.entrySet();
            for (Map.Entry entry : entries) {
                ObjectMapper mapper = new ObjectMapper();
                map.put((String) entry.getKey(), mapper.writeValueAsString(entry.getValue()));
            }
        }
    }

    /**
     * 解析token
     */
    public <T> T parseToken(String token, String key, Class<T> clazz) {
        T t = null;
        try {
            JWTVerifier verifier = new JWTVerifier(secret);
            Map<String, Object> claims = verifier.verify(token);
            t = (T) claims.get(key);
        } catch (Exception e) {
            logger.info("解析客户端token失败，token是" + token);
        }
        return t;
    }
}
