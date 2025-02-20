package com.ddiv.juejin_fk.service.impl;

import com.ddiv.juejin_fk.mapper.ArticleMapper;
import com.ddiv.juejin_fk.mapper.UserMapper;
import com.ddiv.juejin_fk.pojo.JuejinUser;
import com.ddiv.juejin_fk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void register(JuejinUser request) {
        userMapper.insertUser(new JuejinUser(request.getUserName(), request.getUserPassword()));
    }

    @Override
    public JuejinUser findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public void updateImage(Integer userId, String image) {
        userMapper.updateUserImage(userId, image);
    }

    @Override
    public void updatePassword(Integer userId, String password) {
        userMapper.updateUserPassword(userId, password);
    }

    @Override
    public JuejinUser findByUserId(Integer userId) {
        return userMapper.findByUserId(userId);
    }

    @Override
    public JuejinUser getSimpleUser(Integer userId) {
        return userMapper.getSimpleUserInfo(userId);
    }

    @Override
    public void deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
    }

    @Override
    public Map<String, Object> getSelfInfo(Integer userId) {
        JuejinUser user = userMapper.getSimpleUserInfo(userId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", user.getUserId());
        map.put("user_name", user.getUserName());
        map.put("user_resume", user.getUserResume());
        map.put("user_image", user.getUserImage());
        map.put("my_articles", articleMapper.getArticleFromUser(userId));
        map.put("like_articles", articleMapper.getMyLikeArticle(userId));
        return map;
    }

    @Override
    public Map<String, Object> getUserInfo(Integer userId) {
        JuejinUser user = userMapper.getSimpleUserInfo(userId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_id", user.getUserId());
        map.put("user_name", user.getUserName());
        map.put("user_resume", user.getUserResume());
        map.put("user_image", user.getUserImage());
        map.put("my_articles", articleMapper.getArticleFromUser(userId));
        return map;
    }

    @Override
    public void updateUserName(JuejinUser user) {
        userMapper.updateUserName(user);
    }
}
