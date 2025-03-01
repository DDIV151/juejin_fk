package com.ddiv.juejin_fk.service.impl;

import com.ddiv.juejin_fk.exception.LoginException;
import com.ddiv.juejin_fk.exception.UnauthorizedException;
import com.ddiv.juejin_fk.exception.UserNotFoundException;
import com.ddiv.juejin_fk.mapper.ArticleMapper;
import com.ddiv.juejin_fk.mapper.UserMapper;
import com.ddiv.juejin_fk.pojo.JuejinUser;
import com.ddiv.juejin_fk.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final ArticleMapper articleMapper;

    public UserServiceImpl(UserMapper userMapper, ArticleMapper articleMapper) {
        this.userMapper = userMapper;
        this.articleMapper = articleMapper;
    }

    @Override
    public JuejinUser register(JuejinUser request) {
        try {
            userMapper.insertUser(request);
        } catch (Exception e) {
            return null;
        }
        return userMapper.findByUserName(request.getUserName());
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
        JuejinUser user = userMapper.getSimpleUserInfo(userId);
        if (user == null) {
            throw new UserNotFoundException("用户不存在");
        }
        return user;
    }

    @Override
    public void deleteUser(JuejinUser user) {
        JuejinUser user0 = userMapper.findByUserId(user.getUserId());
        if (user0 == null) {
            throw new UserNotFoundException(401, "用户不存在");
        } else if (!user0.getUserPassword().equals(user.getUserPassword())) {
            throw new UnauthorizedException(402, "密码错误");
        }
        userMapper.deleteUser(user.getUserId());
    }

    @Override
    public Map<String, Object> getSelfInfo(Integer userId) {
        Map<String, Object> map = getSimpleUserInfo(userId);
        map.put("like_articles", articleMapper.getMyLikeArticle(userId));
        return map;
    }

    @Override
    public Map<String, Object> getUserInfo(Integer userId) {
        return getSimpleUserInfo(userId);
    }

    /**
     * @param userId 定位用户
     * @return userId, userName, userResume, userImage 封入Map
     */
    private Map<String, Object> getSimpleUserInfo(Integer userId) {
        JuejinUser user = userMapper.getSimpleUserInfo(userId);
        if (user == null) {
            throw new UserNotFoundException("用户不存在");
        }
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
        JuejinUser user0 = userMapper.findByUserName(user.getUserName());
        if (user0 == null)
            throw new UserNotFoundException("用户名不存在");
        try {
            userMapper.updateUserName(user);
        } catch (Exception e) {
            throw new UserNotFoundException(409, "用户名重复");
        }

    }

    @Override
    public JuejinUser login(JuejinUser request) {
        JuejinUser user = userMapper.findByUserName(request.getUserName());
        if (user == null || !user.getUserPassword().equals(request.getUserPassword())) {
            throw new LoginException("用户名或密码错误");
        }
        return user;
    }
}
