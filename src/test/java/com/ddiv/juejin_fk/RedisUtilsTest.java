package com.ddiv.juejin_fk;

import com.ddiv.juejin_fk.config.RedisConfig;
import com.ddiv.juejin_fk.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashMap;
import java.util.Map;

@SpringJUnitConfig(classes = {RedisConfig.class,RedisUtils.class})
public class RedisUtilsTest {


    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void test() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "value");
        map.put("student:1", "1223");
        map.put("student:2", "212");
        System.out.println(map);

        redisUtils.set("key", map);
        System.out.println((Object) redisUtils.get("key"));
    }
}
