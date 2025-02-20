package com.ddiv.juejin_fk;

import com.ddiv.juejin_fk.pojo.JuejinUser;
import com.ddiv.juejin_fk.utils.TokenUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Test;


public class TokenUtilsTest {
    @Test
    public void generateTokenTest() throws Exception{
        JuejinUser user = new JuejinUser("ddiv151","123123");
        String token = TokenUtils.generateToken(user, 10);
        token = TokenUtils.generateToken(user, -1);
        System.out.println("token: " + token);
        Jws<Claims> clams = TokenUtils.parseToken(token);
        System.out.println("clams: " + clams);
        try {
            clams = TokenUtils.parseToken(token.replace("a", "b"));
            System.out.println(clams.getPayload().get("username"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
