package com.ddiv.juejin_fk.utils;

import com.ddiv.juejin_fk.pojo.JuejinUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Data
public class TokenUtils {

    private static long time = 3600 * 24;
    private final static String secret = "ee044392420d89b41081a267f3672935";
    public static final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

    /**
     * 生成token
     * 传入非正的数据则使用默认值
     *
     * @param user 用于确认身份
     * @param time 有限时长 单位：秒 默认一天
     * @return token字符串
     */
    public static String generateToken(JuejinUser user, long time) {
        if (time > 0)
            TokenUtils.time = time;
        Date exprireDate = Date.from(Instant.now().plusSeconds(TokenUtils.time));

        return Jwts.builder()
                // 设置头部信息header
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                // 设置自定义负载信息userName userId
                .claim("user_name", user.getUserName())
                .claim("user_id", user.getUserId())
                // 过期日期
                .expiration(exprireDate)
                // 签发时间
                .issuedAt(new Date())
                // 签名
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public static Jws<Claims> parseToken(String token) throws JwtException, IllegalArgumentException {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }
}
