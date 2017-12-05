package com.cjt.common.util;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * token操作工具类
 */
@Component
public class TokenUtil {

  private static final String EXP = "exp";
  private static final String PAYLOAD = "payload";

  private static String secret;

  /**
   * 采用方法注解，防止value作用在static补起作用
   */
  @Value("${token_secret}")
  private void setSecret(String tokenSecret) {
    secret = tokenSecret;
  }

  /**
   * 生成token
   */
  public static <T> String getToken(T t, long maxAge) {
    try {
      JWTSigner signer = new JWTSigner(secret);
      Map<String, Object> claims = new HashMap<>();
      ObjectMapper mapper = new ObjectMapper();
      claims.put(PAYLOAD, mapper.writeValueAsString(t));
      claims.put(EXP, System.currentTimeMillis() + maxAge);
      return signer.sign(claims);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 解析token
   */
  public static <T> T parseToken(String token, Class<T> clazz) {
    try {
      JWTVerifier verifier = new JWTVerifier(secret);
      Map<String, Object> claims = verifier.verify(token);
      if (claims.containsKey(PAYLOAD) && claims.containsKey(EXP)) {
        long exp = (long) claims.get(EXP);
        if (exp > System.currentTimeMillis()) {
          String json = (String) claims.get(PAYLOAD);
          return new ObjectMapper().readValue(json, clazz);
        }
      }
    } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException | IOException | JWTVerifyException e) {
      e.printStackTrace();
    }
    return null;
  }
}
