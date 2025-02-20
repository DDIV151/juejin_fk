package com.ddiv.juejin_fk;

import com.ddiv.juejin_fk.mapper.UserMapper;
import com.ddiv.juejin_fk.pojo.JuejinUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JuejinFkApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findUserByName() {
        JuejinUser user = userMapper.findByUserName("test");
        System.out.println(user);
    }

    @Test
    void insertUser() {
        userMapper.insertUser(new JuejinUser("test" + System.currentTimeMillis(), "123456"));
    }

}