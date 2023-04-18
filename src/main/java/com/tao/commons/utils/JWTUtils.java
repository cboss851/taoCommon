package com.tao.commons.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @Author：cboss
 * @Date：2023/3/30
 */
public class JWTUtils {
    public static String SECRET = "55eb17c1e6e24a528c54c6bc922cfccc";
    public static Integer EXPIRES = 60 * 60 * 24;//失效时间，单位秒

    /**
     * 根据userID生成token
     */
    public static String getTokenByUserId(String userId) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, EXPIRES);

        String token = JWT.create()
                .withExpiresAt(instance.getTime())
                .withClaim("userId", userId)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 根据token获取userID
     *
     * @param token
     * @return
     */
    public static String getUserIdByToken(String token) {
        return JWTUtils.verify(token).getClaim("userId").asString();
    }

    /**
     * 生成token  header.payload.sign
     */
    public static String getTokenByMap(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, EXPIRES);

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });

        String token = builder.withExpiresAt(instance.getTime())//指定令牌过期时间
                .sign(Algorithm.HMAC256(SECRET));//sign
        return token;
    }

    /**
     * 验证token 合法性
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }


}
