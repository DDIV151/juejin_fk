package com.ddiv.juejin_fk.service;

import com.ddiv.juejin_fk.pojo.JuejinUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {

    JuejinUser register(JuejinUser request) ;

    JuejinUser findByUserName(String userName);

    void updateImage(Integer userId, String image);

    void updatePassword(Integer userId,String newPassword);

    JuejinUser findByUserId(Integer userId);

    JuejinUser getSimpleUser(Integer userId);

    void deleteUser(JuejinUser user);

    Map<String, Object> getSelfInfo(Integer userId);

    Map<String, Object> getUserInfo(Integer userId);

    void updateUserName(JuejinUser user);

    JuejinUser login(JuejinUser request);
}
