package com.ddiv.juejin_fk;


import com.ddiv.juejin_fk.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@SpringJUnitConfig(RedisConfig.class)
public class RedisUtilsTest {


    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void test() throws Exception {

        redisTemplate.opsForValue().set("key", "value");
        redisTemplate.opsForValue().set("student:1", "zpli"); // <2>

        System.out.println(redisTemplate.opsForValue().get("student:1"));

        Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
        map.put("student:1", "1223");
        map.put("student:2", "212");
        redisTemplate.opsForValue().set("key1", map, Duration.ofSeconds(10));
        System.out.println(redisTemplate.opsForValue().get("key1"));
        map = (Map<String, Object>) redisTemplate.opsForValue().get("key1");
        System.out.println(map);
    }
}
