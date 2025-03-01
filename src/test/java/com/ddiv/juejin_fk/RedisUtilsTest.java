package com.ddiv.juejin_fk;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisUtilsTest {


    @Autowired
    private RedisTemplate redisTemplate;// <1>

    @Test
    public void test() throws Exception {

        redisTemplate.opsForValue().set("key", "value");
        redisTemplate.opsForValue().set("student:1", "zpli"); // <2>

        System.out.println(redisTemplate.opsForValue().get("student:1"));
    }
}
